package net.nlacombe.prophecy.v2.generator.llvm;

import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2CallAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2ExpressionAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2IntegerLiteralAstNode;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class V2AstLlvmGenerator {

    public static void generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyV2AstNode astNode) {
        var astNodeClass = astNode.getClass();

        if (ProphecyV2ExpressionAstNode.class.isAssignableFrom(astNodeClass)) {
            V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyV2ExpressionAstNode) astNode);
            return;
        }

        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyV2AstNode>, Consumer<ProphecyV2AstNode>>();

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        generator.accept(astNode);
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyV2ExpressionAstNode astNode) {
        var astNodeClass = astNode.getClass();
        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyV2AstNode>, Function<ProphecyV2ExpressionAstNode, LlvmSymbol>>();
        generatorsByAstNodeType.put(ProphecyV2CallAstNode.class, node -> V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyV2CallAstNode)node));
        generatorsByAstNodeType.put(ProphecyV2IntegerLiteralAstNode.class, node -> V2AstLlvmGenerator.generate(writer, llvmTemporaryNameGenerator, (ProphecyV2IntegerLiteralAstNode)node));

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        return generator.apply(astNode);
    }

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyV2IntegerLiteralAstNode astNode) {
        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(astNode.getEvaluatedType());
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var intLitValue = Integer.parseInt(astNode.getLiteralValue());

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

    private static LlvmSymbol generate(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, ProphecyV2CallAstNode astNode) {
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

            if (!BuiltInTypeSymbol.tVoid.equals(methodSymbol.getType())) {
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
