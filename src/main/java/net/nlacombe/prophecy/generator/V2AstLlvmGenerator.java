package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIntegerLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyStringLiteralAstNode;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class V2AstLlvmGenerator {

    public static void generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyAstNode astNode) {
        var astNodeClass = astNode.getClass();

        if (ProphecyExpressionAstNode.class.isAssignableFrom(astNodeClass)) {
            V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyExpressionAstNode) astNode);
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
        generatorsByAstNodeType.put(ProphecyCallAstNode.class, node -> V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyCallAstNode)node));
        generatorsByAstNodeType.put(ProphecyIntegerLiteralAstNode.class, node -> V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyIntegerLiteralAstNode)node));
        generatorsByAstNodeType.put(ProphecyStringLiteralAstNode.class, node -> V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyStringLiteralAstNode)node));

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
                .map(argument -> V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, argument))
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

            if (!BootstrapTypeSymbols.getInstance().getVoidClass().equals(methodSymbol.getType())) {
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

}
