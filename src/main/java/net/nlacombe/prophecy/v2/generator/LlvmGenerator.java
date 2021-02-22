package net.nlacombe.prophecy.v2.generator;

import net.nlacombe.prophecy.shared.symboltable.domain.MethodSignature;
import net.nlacombe.prophecy.shared.symboltable.domain.Type;
import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2CallAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2ExpressionAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2FileAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2IntegerLiteralAstNode;
import net.nlacombe.prophecy.v2.constants.ConstantsV2;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import org.apache.commons.collections4.ListUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LlvmGenerator {

    static class LlvmSymbol {
        private final String type;
        private final String name;

        public LlvmSymbol(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    private int lastUsedTemporaryLlvmName;

    public LlvmGenerator() {
        lastUsedTemporaryLlvmName = 0;
    }

    public void generate(Writer writer, GlobalScope globalScope) {
        try {
            writer.write("declare i32 @printf(i8*, ...)\n\n");

            generate(writer, globalScope.getSymbols());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generate(Writer writer, List<Symbol> symbols) {
        symbols.forEach(symbol -> {
            if (symbol instanceof MethodSymbol) {
                generate(writer, (MethodSymbol) symbol);
            }

            try {
                writer.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void generate(Writer writer, MethodSymbol methodSymbol) {
        try {
            String methodLlvmCode;

            if (methodSymbol.getSignature().equals(ConstantsV2.PRINTLN_SYSTEM_METHOD_SIGNATURE)) {
                writer.write("@System$println$int$printf = internal constant [4 x i8] c\"%d\\0A\\00\"\n");
                methodLlvmCode = """
                    %_t1 = getelementptr [4 x i8], [4 x i8]* @System$println$int$printf, i64 0, i64 0
                    call i32 (i8*, ...) @printf(i8* %_t1, i32 %i)
                    ret void
                    """;
            } else {
                var methodAstNode = methodSymbol.getDefinitionAstNode();
                var fileAstNode = (ProphecyV2FileAstNode) methodAstNode;

                try (var stringWriter = new StringWriter()) {
                    fileAstNode.getStatements().forEach(statementAstNode -> {
                        stringWriter.write("; start of statement\n");
                        generate(stringWriter, statementAstNode);
                        stringWriter.write("; end of statement\n\n");
                    });

                    if (BuiltInTypeSymbol.tVoid.equals(methodSymbol.getType()))
                        stringWriter.write("ret void\n");

                    methodLlvmCode = stringWriter.toString();
                }
            }

            generate(writer, methodSymbol, methodLlvmCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generate(Writer writer, MethodSymbol methodSymbol, String methodLlvmCode) {
        var functionDeclarationLlvm = "define ";
        functionDeclarationLlvm += getLlvmType(methodSymbol.getType()) + " ";
        functionDeclarationLlvm += getLlvmFunctionName(methodSymbol);

        var parameterDeclarationLlvm = methodSymbol.getParameters().stream()
            .map(parameter -> getLlvmType(parameter.getType()) + " %" + parameter.getName())
            .collect(Collectors.joining(", "));

        functionDeclarationLlvm += "(" + parameterDeclarationLlvm + ")";
        functionDeclarationLlvm += " {\n";

        try {
            writer.write(functionDeclarationLlvm);
            writer.write(methodLlvmCode.indent(4));
            writer.write("}\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generate(Writer writer, ProphecyV2AstNode astNode) {
        var astNodeClass = astNode.getClass();

        if (ProphecyV2ExpressionAstNode.class.isAssignableFrom(astNodeClass)) {
            generate(writer, (ProphecyV2ExpressionAstNode) astNode);
            return;
        }

        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyV2AstNode>, Consumer<ProphecyV2AstNode>>();

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        generator.accept(astNode);
    }

    private LlvmSymbol generate(Writer writer, ProphecyV2ExpressionAstNode astNode) {
        var astNodeClass = astNode.getClass();
        var generatorsByAstNodeType = new HashMap<Class<? extends ProphecyV2AstNode>, Function<ProphecyV2ExpressionAstNode, LlvmSymbol>>();
        generatorsByAstNodeType.put(ProphecyV2CallAstNode.class, node -> this.generate(writer, (ProphecyV2CallAstNode)node));
        generatorsByAstNodeType.put(ProphecyV2IntegerLiteralAstNode.class, node -> this.generate(writer, (ProphecyV2IntegerLiteralAstNode)node));

        var generator = generatorsByAstNodeType.get(astNodeClass);

        if (generator == null)
            throw new ProphecyCompilerException("Unimplemented llvm generator for ast node type: " + astNodeClass.getSimpleName());

        return generator.apply(astNode);
    }

    private LlvmSymbol generate(Writer writer, ProphecyV2IntegerLiteralAstNode astNode) {
        try {
            var llvmType = getLlvmType(astNode.getEvaluatedType());
            var returnValueName = getNewTemporaryLlvmName();
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

    private LlvmSymbol generate(Writer writer, ProphecyV2CallAstNode astNode) {
        try {
            var arguments = astNode.getArguments().stream()
                .map(argument -> generate(writer, argument))
                .map(argumentSymbol -> argumentSymbol.getType() + " " + argumentSymbol.getName())
                .collect(Collectors.joining(", "));

            var methodSymbol = astNode.getMethodSymbol();

            var parameterTypes = methodSymbol.getParameters().stream()
                .map(Symbol::getType)
                .map(this::getLlvmType)
                .collect(Collectors.joining(", "));

            var llvmCode = "call $returnType ($parameterTypes) $functionName($arguments) ; call ast node\n"
                .replace("$returnType", getLlvmType(methodSymbol.getType()))
                .replace("$parameterTypes", parameterTypes)
                .replace("$functionName", getLlvmFunctionName(methodSymbol))
                .replace("$arguments", arguments);

            LlvmSymbol returnLlvmSymbol = null;

            if (!BuiltInTypeSymbol.tVoid.equals(methodSymbol.getType())) {
                var returnValueName = getNewTemporaryLlvmName();

                llvmCode = returnValueName + " = " + llvmCode;
                returnLlvmSymbol = new LlvmSymbol(getLlvmType(methodSymbol.getType()), returnValueName);
            }

            writer.write(llvmCode);

            return returnLlvmSymbol;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getLlvmFunctionName(MethodSymbol methodSymbol) {
        return "@" + getLlvmNameFromNameParts(getFunctionNameParts(methodSymbol));
    }

    private List<String> getFunctionNameParts(MethodSymbol methodSymbol) {
        var functionNamePartsLlvm = new ArrayList<String>();

        if (methodSymbol.getParentClass() != null)
            functionNamePartsLlvm.add(methodSymbol.getParentClass().getName());

        functionNamePartsLlvm.addAll(getNameParts(methodSymbol.getSignature()));

        return functionNamePartsLlvm;
    }

    private List<String> getNameParts(MethodSignature signature) {
        var parameterNameParts = signature.getParameterTypes().stream()
            .map(this::getLlvmType)
            .collect(Collectors.toList());

        return ListUtils.union(List.of(signature.getName()), parameterNameParts);
    }

    private String getLlvmType(Type type) {
        if (BuiltInTypeSymbol.tVoid.equals(type))
            return "void";
        if (BuiltInTypeSymbol.tInt.equals(type))
            return "i32";
        else
            throw new ProphecyCompilerException("Unimplemented llvm type for prophecy type: " + type);
    }

    private String getLlvmNameFromNameParts(List<String> nameParts) {
        return String.join(getLlvmNamePartSeparator(), nameParts);
    }

    private String getLlvmNamePartSeparator() {
        return "$";
    }

    private String getNewTemporaryLlvmName() {
        lastUsedTemporaryLlvmName++;

        return "%" + lastUsedTemporaryLlvmName;
    }

}
