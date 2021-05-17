package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.ast.node.ProphecyArrayLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallSelectionExpressionAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIntegerLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyStringLiteralAstNode;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import org.apache.commons.collections4.ListUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        generator.accept(astNode);
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyExpressionAstNode astNode) {
        var astNodeClass = astNode.getClass();
        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyAstNode>, Function<ProphecyExpressionAstNode, LlvmSymbol>>();
        generatorsByAstNodeType.put(ProphecyCallAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyCallAstNode)node));
        generatorsByAstNodeType.put(ProphecyCallSelectionExpressionAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyCallSelectionExpressionAstNode)node));
        generatorsByAstNodeType.put(ProphecyIntegerLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyIntegerLiteralAstNode)node));
        generatorsByAstNodeType.put(ProphecyStringLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyStringLiteralAstNode)node));
        generatorsByAstNodeType.put(ProphecyArrayLiteralAstNode.class, node -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyArrayLiteralAstNode)node));

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        return generator.apply(astNode);
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
            var arguments = astNode.getArguments().stream()
                .map(argument -> AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, argument))
                .map(argumentSymbol -> argumentSymbol.getType() + " " + argumentSymbol.getName())
                .collect(Collectors.joining(", "));

            var methodSymbol = astNode.getMethodSymbol();

            var parameterTypes = methodSymbol.getParameters().stream()
                .map(Symbol::getType)
                .map(LlvmGeneratorUtil::getLlvmType)
                .collect(Collectors.joining(", "));

            var llvmCode = "call $returnType ($parameterTypes) $functionName($arguments) ; call ast node\n"
                .replace("$returnType", LlvmGeneratorUtil.getLlvmType(methodSymbol.getType()))
                .replace("$parameterTypes", parameterTypes)
                .replace("$functionName", LlvmGeneratorUtil.getLlvmFunctionName(methodSymbol))
                .replace("$arguments", arguments);

            LlvmSymbol returnLlvmSymbol = null;

            if (!bootstrapTypeSymbols.getVoidClass().equals(methodSymbol.getType())) {
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
        var uInt8Class = bootstrapTypeSymbols.getUInt8Class();
        var selectionExpression = astNode.getSelectionExpression();

        validateUInt8ArrayGetMethodCall(astNode, uInt8Class, selectionExpression);

        var arrayPointerLlvmSymbol = AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, selectionExpression);
        var indexLlvmSymbol = AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, astNode.getCall().getArguments().get(0));

        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(bootstrapTypeSymbols.getUInt8Class());
            var indexOffsetName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var indexPointerName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();

            var llvmCode = """
                $indexOffsetName = add i8 $indexName, 1 ; get value from UInt8 array start
                $indexPointerName = getelementptr i8, i8* $arrayPointerName, i8 $indexOffsetName
                $returnValueName = load i8, i8* $indexPointerName ; get value from UInt8 array end
                """
                .replace("$indexOffsetName", indexOffsetName)
                .replace("$arrayPointerName", arrayPointerLlvmSymbol.getName())
                .replace("$indexPointerName", indexPointerName)
                .replace("$returnValueName", returnValueName)
                .replace("$indexName", indexLlvmSymbol.getName());

            writer.write(llvmCode);

            return new LlvmSymbol(llvmType, returnValueName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private static void validateUInt8ArrayGetMethodCall(ProphecyCallSelectionExpressionAstNode astNode, ClassSymbol uInt8Class, ProphecyExpressionAstNode selectionExpression) {
        var exception = new ProphecyCompilerException("the llvm generator only support selection expression call for the get method of an UInt8 array");

        if (!(selectionExpression instanceof ProphecyArrayLiteralAstNode))
            throw exception;

        var arrayType = ((ProphecyArrayLiteralAstNode) selectionExpression).getArrayType();

        if (!uInt8Class.equals(arrayType.getSubstitutedParameterTypes().get(0)))
            throw exception;

        if (!specialTypeSymbols.getUInt8ArrayGetMethodSignature().equals(astNode.getCall().getMethodSymbol().getSignature()))
            throw exception;
    }

    private static List<Integer> getArrayUInt8Elements(ProphecyArrayLiteralAstNode astNode) {
        var arrayType = astNode.getArrayType();
        var uInt8Class = bootstrapTypeSymbols.getUInt8Class();

        if (!uInt8Class.equals(arrayType.getSubstitutedParameterTypes().get(0)))
            throw new ProphecyCompilerException("array elements are not of type UInt8");

        return astNode.getElements().stream()
            .map(expression -> ((ProphecyIntegerLiteralAstNode) expression).getLiteralValue())
            .collect(Collectors.toList());
    }

}
