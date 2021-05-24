package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.ast.node.ProphecyArrayLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyForeachAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIdentifierExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIntegerLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyStringLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyVariableDeclarationAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.util.WriterUtil;
import org.apache.commons.collections4.ListUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AstLlvmGenerator {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
    private static final ProphecySpecialTypeSymbols specialTypeSymbols = ProphecySpecialTypeSymbols.getInstance();

    public static void generate(Writer writer, LlvmContext llvmContext, ProphecyAstNode astNode) {
        var astNodeClass = astNode.getClass();

        if (ProphecyExpressionAstNode.class.isAssignableFrom(astNodeClass)) {
            AstLlvmGenerator.generate(writer, llvmContext, (ProphecyExpressionAstNode) astNode);
            return;
        }

        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyAstNode>, Consumer<ProphecyAstNode>>();
        generatorsByAstNodeType.put(ProphecyVariableDeclarationAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmContext, (ProphecyVariableDeclarationAstNode) node));
        generatorsByAstNodeType.put(ProphecyForeachAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmContext, (ProphecyForeachAstNode) node));

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        generator.accept(astNode);
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyExpressionAstNode astNode) {
        var astNodeClass = astNode.getClass();
        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyAstNode>, Function<ProphecyExpressionAstNode, LlvmSymbol>>();
        generatorsByAstNodeType.put(ProphecyCallAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmContext, (ProphecyCallAstNode) node));
        generatorsByAstNodeType.put(ProphecyIntegerLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmContext, (ProphecyIntegerLiteralAstNode) node));
        generatorsByAstNodeType.put(ProphecyStringLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmContext, (ProphecyStringLiteralAstNode) node));
        generatorsByAstNodeType.put(ProphecyArrayLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmContext, (ProphecyArrayLiteralAstNode) node));
        generatorsByAstNodeType.put(ProphecyIdentifierExpressionAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmContext, (ProphecyIdentifierExpressionAstNode) node));

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        return generator.apply(astNode);
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyIdentifierExpressionAstNode astNode) {
        var llvmType = LlvmGeneratorUtil.getLlvmReferenceFromType(astNode.getEvaluatedType());

        return new LlvmSymbol(llvmType, LlvmGeneratorUtil.getLlvmVariableName(astNode.getSymbol().getName()));
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyVariableDeclarationAstNode astNode) {
        var initializerLlvmSymbol = AstLlvmGenerator.generate(writer, llvmContext, astNode.getInitializer());
        var pointer = LlvmGeneratorPointerUtil.getPointer(writer, llvmContext, initializerLlvmSymbol);
        var llvmVariableName = LlvmGeneratorUtil.getLlvmVariableName(astNode.getVariableName());

        return LlvmGeneratorPointerUtil.wrap(writer, llvmContext, pointer, llvmVariableName);
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyForeachAstNode astNode) {
        var expressionLlvmSymbol = generate(writer, llvmContext, astNode.getExpression());
        var arrayPointer = LlvmGeneratorPointerUtil.getPointer(writer, llvmContext, expressionLlvmSymbol);
        var arraySizeLlvmSymbol = ArrayLlvmGenerator.generateUInt8ArraySize(writer, llvmContext, arrayPointer);

        var lastIndex = llvmContext.getNewTemporaryLlvmName();
        var indexName = llvmContext.getNewTemporaryLlvmName();
        var indexValue1 = llvmContext.getNewTemporaryLlvmName();
        var conditionBoolean = llvmContext.getNewTemporaryLlvmName();
        var indexValue2 = llvmContext.getNewTemporaryLlvmName();
        var arrayValuePointer = llvmContext.getNewTemporaryLlvmName();
        var foreachName = llvmContext.getNewForeachName();

        var llvmCodePart1 = """
            ; $foreachName start
            $lastIndex = add i8 $arraySize, 1
            $indexName = alloca i8
            store i8 1, i8* $indexName
            br label %$foreachName.cond

            $foreachName.cond:
            $indexValue1 = load i8, i8* $indexName
            $conditionBoolean = icmp ult i8 $indexValue1, $lastIndex
            br i1 $conditionBoolean, label %$foreachName.body, label %$foreachName.end

            $foreachName.body:
            $indexValue2 = load i8, i8* $indexName
            $arrayValuePointer = getelementptr i8, i8* $arrayName, i8 $indexValue2
            """
            .replace("$lastIndex", lastIndex)
            .replace("$indexName", indexName)
            .replace("$arraySize", arraySizeLlvmSymbol.getName())
            .replace("$foreachName", foreachName)
            .replace("$indexValue1", indexValue1)
            .replace("$conditionBoolean", conditionBoolean)
            .replace("$indexValue2", indexValue2)
            .replace("$arrayName", arrayPointer.getName())
            .replace("$arrayValuePointer", arrayValuePointer)
            ;

        WriterUtil.writeRuntimeException(writer, llvmCodePart1);
        LlvmGeneratorPointerUtil.wrap(writer, llvmContext, new LlvmSymbol("i8*", arrayValuePointer), LlvmGeneratorUtil.getLlvmVariableName(astNode.getVariableName()));
        WriterUtil.writeRuntimeException(writer, "; end of $foreachName part 1\n".replace("$foreachName", foreachName));

        astNode.getBlock().forEach(node -> generate(writer, llvmContext, node));

        var llvmCodePart2 = """
            ; start of $foreachName part 2
            $newIndexValue = add i8 $indexValue2, 1
            store i8 $newIndexValue, i8* $indexName
            br label %$foreachName.cond

            $foreachName.end:
            ; $foreachName end
            """
            .replace("$indexName", indexName)
            .replace("$foreachName", foreachName)
            .replace("$indexValue2", indexValue2)
            .replace("$newIndexValue", llvmContext.getNewTemporaryLlvmName())
            ;

        WriterUtil.writeRuntimeException(writer, llvmCodePart2);

        return null;
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyIntegerLiteralAstNode astNode) {
        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(astNode.getEvaluatedType());
            var returnValueName = llvmContext.getNewTemporaryLlvmName();
            var intLitValue = astNode.getLiteralValue();

            var llvmCode = "$returnValueName = add $llvmType 0, $intLitValue ; int lit ast node\n"
                .replace("$returnValueName", returnValueName)
                .replace("$llvmType", llvmType)
                .replace("$intLitValue", "" + intLitValue);

            writer.write(llvmCode);

            return new LlvmSymbol(llvmType, returnValueName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyStringLiteralAstNode astNode) {
        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(astNode.getEvaluatedType());
            var allocTempName = llvmContext.getNewTemporaryLlvmName();
            var returnValueName = llvmContext.getNewTemporaryLlvmName();
            var llvmStringLiteral = LlvmGeneratorUtil.toLlvmStringLiteral(astNode.getStringValue());
            var stringLength = astNode.getStringValue().length() + 1;

            var llvmCode = """
                $allocTempName = alloca [$stringLength x i8] ; string lit ast start
                store [$stringLength x i8] c"$llvmStringLiteral\\00", [$stringLength x i8]* $allocTempName
                $returnValueName = bitcast [$stringLength x i8]* $allocTempName to i8* ; string lit ast end
                """
                .replace("$allocTempName", allocTempName)
                .replace("$returnValueName", returnValueName)
                .replace("$stringLength", "" + stringLength)
                .replace("$llvmStringLiteral", llvmStringLiteral);

            writer.write(llvmCode);

            return new LlvmSymbol(llvmType, returnValueName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyCallAstNode astNode) {
        var expression = astNode.getExpression();
        var expressionType = expression.getEvaluatedType();
        var methodSymbol = astNode.getMethodSymbol();
        var methodSignature = methodSymbol.getSignature();
        var arguments = astNode.getArguments();

        if (Type.sameType(expressionType, specialTypeSymbols.getUInt8Array())) {
            var arrayLlvmSymbol = AstLlvmGenerator.generate(writer, llvmContext, expression);
            var arrayPointerLlvmSymbol = LlvmGeneratorPointerUtil.getPointer(writer, llvmContext, arrayLlvmSymbol);

            if (specialTypeSymbols.getUInt8ArrayGetMethodSignature().equals(methodSignature)) {
                var indexLlvmSymbol = AstLlvmGenerator.generate(writer, llvmContext, arguments.get(0));

                return ArrayLlvmGenerator.generateUInt8ArrayGet(writer, llvmContext, arrayPointerLlvmSymbol, indexLlvmSymbol);
            } else if (specialTypeSymbols.getUInt8ArraySizeMethodSignature().equals(methodSignature))
                return ArrayLlvmGenerator.generateUInt8ArraySize(writer, llvmContext, arrayPointerLlvmSymbol);
            else
                throw new ProphecyCompilerException("llvm generator has no implementation for this Array<UInt8> method: " + methodSignature);
        } else if (Type.sameType(expressionType, bootstrapTypeSymbols.getArrayClass())) {
            if (specialTypeSymbols.getArrayRangeMethodSignature().equals(methodSignature)) {
                var startLlvmSymbol = AstLlvmGenerator.generate(writer, llvmContext, arguments.get(0));
                var endLlvmSymbol = AstLlvmGenerator.generate(writer, llvmContext, arguments.get(1));

                return ArrayLlvmGenerator.generateArrayRange(writer, llvmContext, startLlvmSymbol, endLlvmSymbol);
            } else
                throw new ProphecyCompilerException("llvm generator has no implementation for this Array<T> method: " + methodSignature);
        } else
            return generateCall(writer, llvmContext, methodSymbol, arguments);
    }

    private static LlvmSymbol generateCall(Writer writer, LlvmContext llvmContext, MethodSymbol methodSymbol, List<ProphecyExpressionAstNode> arguments) {
        var parameterLlvmTypes = methodSymbol.getParameters().stream()
            .map(Symbol::getType)
            .map(LlvmGeneratorUtil::getLlvmType)
            .collect(Collectors.toList());
        var llvmArguments = IntStream.range(0, arguments.size())
            .mapToObj(i -> {
                var argument = arguments.get(i);
                var parameterLlvmType = parameterLlvmTypes.get(i);
                var llvmSymbol = AstLlvmGenerator.generate(writer, llvmContext, argument);

                return LlvmGeneratorPointerUtil.convert(writer, llvmContext, llvmSymbol, parameterLlvmType);
            })
            .collect(Collectors.toList());

        return LlvmGeneratorCallUtil.generateCallToProphecyMethod(writer, llvmContext, methodSymbol, llvmArguments);
    }

    private static LlvmSymbol generate(Writer writer, LlvmContext llvmContext, ProphecyArrayLiteralAstNode astNode) {
        var arrayType = astNode.getArrayType();
        var uInt8Class = bootstrapTypeSymbols.getUInt8Class();

        if (!uInt8Class.equals(arrayType.getSubstitutedParameterTypes().get(0)))
            throw new ProphecyCompilerException("only UInt8 array literals are supported by the llvm generator");

        var numberOfElementsInArray = astNode.getElements().size();

        if (numberOfElementsInArray > 255)
            throw new ProphecyCompilerException("array literal with more than 255 elements are not supported by the llvm generator");

        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(specialTypeSymbols.getUInt8Array());
            var allocTempName = llvmContext.getNewTemporaryLlvmName();
            var returnValueName = llvmContext.getNewTemporaryLlvmName();
            var llvmStringLiteral = LlvmGeneratorUtil.toLlvmStringLiteral(ListUtils.union(List.of(numberOfElementsInArray), getArrayUInt8Elements(astNode)));

            var llvmCode = """
                ; $nodeText
                $allocTempName = alloca [$arrayLength x i8] ; uint8 array lit ast start
                store [$arrayLength x i8] c"$llvmStringLiteral", [$arrayLength x i8]* $allocTempName
                $returnValueName = bitcast [$arrayLength x i8]* $allocTempName to i8* ; uint8 array lit ast end
                """
                .replace("$nodeText", astNode.toString())
                .replace("$allocTempName", allocTempName)
                .replace("$returnValueName", returnValueName)
                .replace("$arrayLength", "" + (numberOfElementsInArray + 1))
                .replace("$llvmStringLiteral", llvmStringLiteral);

            writer.write(llvmCode);

            return new LlvmSymbol(llvmType, returnValueName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> getArrayUInt8Elements(ProphecyArrayLiteralAstNode astNode) {
        if (!Type.sameType(astNode.getArrayType(), specialTypeSymbols.getUInt8Array()))
            throw new ProphecyCompilerException("array elements are not of type UInt8");

        return astNode.getElements().stream()
            .map(expression -> ((ProphecyIntegerLiteralAstNode) expression).getLiteralValue())
            .collect(Collectors.toList());
    }

}
