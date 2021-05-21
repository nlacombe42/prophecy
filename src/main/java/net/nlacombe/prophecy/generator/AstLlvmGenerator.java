package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.ast.node.ProphecyArrayLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallSelectionExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIdentifierExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIntegerLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyStringLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyVariableDeclarationAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
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

    public static void generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyAstNode astNode) {
        var astNodeClass = astNode.getClass();

        if (ProphecyExpressionAstNode.class.isAssignableFrom(astNodeClass)) {
            AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyExpressionAstNode) astNode);
            return;
        }

        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyAstNode>, Consumer<ProphecyAstNode>>();
        generatorsByAstNodeType.put(ProphecyVariableDeclarationAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyVariableDeclarationAstNode) node));

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        generator.accept(astNode);
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyExpressionAstNode astNode) {
        var astNodeClass = astNode.getClass();
        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyAstNode>, Function<ProphecyExpressionAstNode, LlvmSymbol>>();
        generatorsByAstNodeType.put(ProphecyCallAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyCallAstNode) node));
        generatorsByAstNodeType.put(ProphecyCallSelectionExpressionAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyCallSelectionExpressionAstNode) node));
        generatorsByAstNodeType.put(ProphecyIntegerLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyIntegerLiteralAstNode) node));
        generatorsByAstNodeType.put(ProphecyStringLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyStringLiteralAstNode) node));
        generatorsByAstNodeType.put(ProphecyArrayLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyArrayLiteralAstNode) node));
        generatorsByAstNodeType.put(ProphecyIdentifierExpressionAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyIdentifierExpressionAstNode) node));

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        return generator.apply(astNode);
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyIdentifierExpressionAstNode astNode) {
        var llvmType = LlvmGeneratorUtil.getLlvmReferenceFromType(astNode.getEvaluatedType());

        return new LlvmSymbol(llvmType, LlvmGeneratorUtil.getLlvmVariableName(astNode.getSymbol().getName()));
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyVariableDeclarationAstNode astNode) {
        var initializerLlvmSymbol = AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, astNode.getInitializer());
        var pointer = LlvmGeneratorPointerUtil.getPointer(writer, llvmTemporaryNameGenerator, initializerLlvmSymbol);
        var llvmVariableName = LlvmGeneratorUtil.getLlvmVariableName(astNode.getVariableName());

        return LlvmGeneratorPointerUtil.wrap(writer, llvmTemporaryNameGenerator, pointer, llvmVariableName);
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyIntegerLiteralAstNode astNode) {
        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(astNode.getEvaluatedType());
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
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

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyStringLiteralAstNode astNode) {
        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(astNode.getEvaluatedType());
            var allocTempName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
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

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyCallAstNode astNode) {
        try {
            var methodSymbol = astNode.getMethodSymbol();
            var parameterLlvmTypes = methodSymbol.getParameters().stream()
                .map(Symbol::getType)
                .map(LlvmGeneratorUtil::getLlvmType)
                .collect(Collectors.toList());
            var arguments = astNode.getArguments();
            var llvmArguments = IntStream.range(0, astNode.getArguments().size())
                .mapToObj(i -> {
                    var argument = arguments.get(i);
                    var parameterLlvmType = parameterLlvmTypes.get(i);
                    var llvmSymbol = AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, argument);

                    return LlvmGeneratorPointerUtil.convert(writer, llvmTemporaryNameGenerator, llvmSymbol, parameterLlvmType);
                })
                .map(argumentSymbol -> argumentSymbol.getType() + " " + argumentSymbol.getName())
                .collect(Collectors.joining(", "));

            var llvmCode = "call $returnType ($parameterTypes) $functionName($arguments) ; call ast node\n"
                .replace("$returnType", LlvmGeneratorUtil.getLlvmType(methodSymbol.getType()))
                .replace("$parameterTypes", String.join(", ", parameterLlvmTypes))
                .replace("$functionName", LlvmGeneratorUtil.getLlvmFunctionName(methodSymbol))
                .replace("$arguments", llvmArguments);

            LlvmSymbol returnLlvmSymbol = null;

            if (!Type.sameType(methodSymbol.getType(), bootstrapTypeSymbols.getVoidClass())) {
                var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();

                llvmCode = returnValueName + " = " + llvmCode;
                returnLlvmSymbol = new LlvmSymbol(LlvmGeneratorUtil.getLlvmType(methodSymbol.getType()), returnValueName);
            }

            writer.write(llvmCode);

            return returnLlvmSymbol;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyCallSelectionExpressionAstNode astNode) {
        var selectionExpression = astNode.getSelectionExpression();
        var methodSymbol = astNode.getCall().getMethodSymbol();
        var methodSignature = methodSymbol.getSignature();

        validateIsUInt8Array(selectionExpression.getEvaluatedType());

        var arrayLlvmSymbol = AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, selectionExpression);
        var arrayPointerLlvmSymbol = LlvmGeneratorPointerUtil.getPointer(writer, llvmTemporaryNameGenerator, arrayLlvmSymbol);

        if (specialTypeSymbols.getUInt8ArrayGetMethodSignature().equals(methodSignature)) {
            var indexLlvmSymbol = AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, astNode.getCall().getArguments().get(0));

            return UInt8ArrayLlvmGenerator.generateUInt8ArrayGet(writer, llvmTemporaryNameGenerator, arrayPointerLlvmSymbol, indexLlvmSymbol);
        } else if (specialTypeSymbols.getUInt8ArraySizeMethodSignature().equals(methodSignature))
            return UInt8ArrayLlvmGenerator.generateUInt8ArraySize(writer, llvmTemporaryNameGenerator, arrayPointerLlvmSymbol);
        else
            throw new ProphecyCompilerException("llvm generator has no implementation for this Array<UInt8> method: " + methodSignature);
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyArrayLiteralAstNode astNode) {
        var arrayType = astNode.getArrayType();
        var uInt8Class = bootstrapTypeSymbols.getUInt8Class();

        if (!uInt8Class.equals(arrayType.getSubstitutedParameterTypes().get(0)))
            throw new ProphecyCompilerException("only UInt8 array literals are supported by the llvm generator");

        var numberOfElementsInArray = astNode.getElements().size();

        if (numberOfElementsInArray > 255)
            throw new ProphecyCompilerException("array literal with more than 255 elements are not supported by the llvm generator");

        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(specialTypeSymbols.getUInt8Array());
            var allocTempName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
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

    private static void validateIsUInt8Array(Type type) {
        if (!Type.sameType(type, specialTypeSymbols.getUInt8Array()))
            throw new ProphecyCompilerException("only selection expression calls for Array<UInt8> are supported by the llvm generator");
    }

    private static List<Integer> getArrayUInt8Elements(ProphecyArrayLiteralAstNode astNode) {
        if (!Type.sameType(astNode.getArrayType(), specialTypeSymbols.getUInt8Array()))
            throw new ProphecyCompilerException("array elements are not of type UInt8");

        return astNode.getElements().stream()
            .map(expression -> ((ProphecyIntegerLiteralAstNode) expression).getLiteralValue())
            .collect(Collectors.toList());
    }

}
