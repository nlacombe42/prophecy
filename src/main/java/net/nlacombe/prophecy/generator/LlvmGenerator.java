package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.util.WriterUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LlvmGenerator {

    public void generate(Writer writer, GlobalScope globalScope) {
        WriterUtil.writeRuntimeException(writer, "declare i32 @printf(i8*, ...)\n\n");

        generate(writer, globalScope.getSymbols());
    }

    public void generate(Writer writer, List<Symbol> symbols) {
        symbols.forEach(symbol -> {
            if (symbol instanceof MethodSymbol) {
                generate(writer, (MethodSymbol) symbol);
            }

            WriterUtil.writeRuntimeException(writer, "\n");
        });
    }

    public void generate(Writer writer, MethodSymbol methodSymbol) {
        try {
            var customLlvmCodeByMethodSignature = getCustomLlvmCodeByMethodSignature();
            var customLlvmCode = customLlvmCodeByMethodSignature.get(methodSymbol.getSignature());
            String methodLlvmCode;

            if (customLlvmCode != null) {
                methodLlvmCode = customLlvmCode;
            } else {
                var methodAstNode = methodSymbol.getDefinitionAstNode();
                var fileAstNode = (ProphecyFileAstNode) methodAstNode;
                var llvmTemporaryNameGenerator = new LlvmTemporaryNameGenerator();

                try (var stringWriter = new StringWriter()) {
                    fileAstNode.getStatements().forEach(statementAstNode -> {
                        stringWriter.write("; " + statementAstNode.toString() + "\n");
                        stringWriter.write("; start of statement\n");
                        AstLlvmGenerator.generate(stringWriter, llvmTemporaryNameGenerator, statementAstNode);
                        stringWriter.write("; end of statement\n\n");
                    });

                    if (BootstrapTypeSymbols.getInstance().getVoidClass().equals(methodSymbol.getType()))
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
        var functionType = LlvmGeneratorUtil.getLlvmType(methodSymbol.getType());
        var functionName = LlvmGeneratorUtil.getLlvmFunctionName(methodSymbol);

        var parameters = methodSymbol.getParameters().stream()
            .map(parameter -> LlvmGeneratorUtil.getLlvmType(parameter.getType()) + " %" + parameter.getName())
            .collect(Collectors.joining(", "));

        var functionDeclarationLlvm = "define $functionType $functionName($parameters) {\n"
            .replace("$functionType", functionType)
            .replace("$functionName", functionName)
            .replace("$parameters", parameters);

        try {
            writer.write(functionDeclarationLlvm);
            writer.write(methodLlvmCode.indent(4));
            writer.write("}\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<MethodSignature, String> getCustomLlvmCodeByMethodSignature() {
        var customLlvmCodeByMethodSignature = new HashMap<MethodSignature, String>();

        var printlnIntSystemMethodSymbol = BootstrapTypeSymbols.getInstance().getSystemPrintlnUInt8();
        customLlvmCodeByMethodSignature.put(printlnIntSystemMethodSymbol.getSignature(), """
            %1 = alloca [4 x i8]
            store [4 x i8] c"%d\\0A\\00", [4 x i8]* %1
            %2 = bitcast [4 x i8]* %1 to i8*
            call i32 (i8*, ...) @printf(i8* %2, i8 %$argumentName)
            ret void
            """
            .replace("$argumentName", printlnIntSystemMethodSymbol.getParameter(0).getName()));

        var printlnStringSystemMethodSignature = BootstrapTypeSymbols.getInstance().getSystemPrintlnString();
        customLlvmCodeByMethodSignature.put(printlnStringSystemMethodSignature.getSignature(), """
            %1 = alloca [4 x i8]
            store [4 x i8] c"%s\\0A\\00", [4 x i8]* %1
            %2 = bitcast [4 x i8]* %1 to i8*
            call i32 (i8*, ...) @printf(i8* %2, i8* %$argumentName)
            ret void
            """
            .replace("$argumentName", printlnStringSystemMethodSignature.getParameter(0).getName()));

        return customLlvmCodeByMethodSignature;
    }
}
