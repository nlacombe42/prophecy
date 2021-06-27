package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
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

public class SymbolLlvmGenerator {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
    private static final ProphecySpecialTypeSymbols prophecySpecialTypeSymbols = ProphecySpecialTypeSymbols.getInstance();

    public void generate(Writer writer, GlobalScope globalScope) {
        WriterUtil.writeRuntimeException(writer, "declare i32 @printf(i8*, ...)\n\n");

        generate(writer, globalScope.getSymbols());
    }

    public void generate(Writer writer, List<Symbol> symbols) {
        symbols.forEach(symbol -> {
            if (symbol instanceof MethodSymbol) {
                generate(writer, (MethodSymbol) symbol);
            } else if (symbol instanceof ClassSymbol)
                generate(writer, ((ClassSymbol) symbol).getMembers());
        });
    }

    public void generate(Writer writer, MethodSymbol methodSymbol) {
        var methodSignature = methodSymbol.getSignature();

        if (getSpecialInlineMethodSignatures().contains(methodSignature))
            return;

        var customLlvmCodeByMethodSignature = getCustomLlvmCodeByMethodSignature();
        var customLlvmCode = customLlvmCodeByMethodSignature.get(methodSignature);
        var methodLlvmCode = customLlvmCode != null ? customLlvmCode : getLlvmCodeForNonCustomNonInlineMethod(methodSymbol);

        generate(writer, methodSymbol, methodLlvmCode);
        WriterUtil.writeRuntimeException(writer, "\n");
    }

    private String getLlvmCodeForNonCustomNonInlineMethod(MethodSymbol methodSymbol) {
        var methodAstNode = methodSymbol.getDefinitionAstNode();
        var fileAstNode = (ProphecyFileAstNode) methodAstNode;
        var llvmContext = new LlvmContext(LlvmGeneratorUtil.getLlvmNameBySymbol(fileAstNode.getStatements()));

        try (var stringWriter = new StringWriter()) {
            stringWriter.write("entry:\n");

            fileAstNode.getStatements().forEach(statementAstNode -> {
                stringWriter.write("; " + statementAstNode.toString().replaceAll("\n", "; ") + "\n");
                stringWriter.write("; start of statement\n");
                AstLlvmGenerator.generate(stringWriter, llvmContext, statementAstNode);
                stringWriter.write("; end of statement\n\n");
            });

            if (bootstrapTypeSymbols.getVoidClass().equals(methodSymbol.getType()))
                stringWriter.write("ret void\n");

            return stringWriter.toString();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
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

    private List<MethodSignature> getSpecialInlineMethodSignatures() {
        return List.of(
            prophecySpecialTypeSymbols.getUInt8ArrayGetMethodSignature(),
            prophecySpecialTypeSymbols.getGenericArraySetMethodSignature(),
            prophecySpecialTypeSymbols.getUInt8ArraySizeMethodSignature(),
            prophecySpecialTypeSymbols.getArrayRangeMethodSignature()
        );
    }

    private Map<MethodSignature, String> getCustomLlvmCodeByMethodSignature() {
        var customLlvmCodeByMethodSignature = new HashMap<MethodSignature, String>();

        customLlvmCodeByMethodSignature.put(prophecySpecialTypeSymbols.getSystemPrintlnUInt8MethodSignature(), """
            %1 = alloca [4 x i8]
            store [4 x i8] c"%d\\0A\\00", [4 x i8]* %1
            %2 = bitcast [4 x i8]* %1 to i8*
            call i32 (i8*, ...) @printf(i8* %2, i8 %i)
            ret void
            """);

        customLlvmCodeByMethodSignature.put(prophecySpecialTypeSymbols.getSystemPrintlnStringMethodSignature(), """
            %1 = alloca [4 x i8]
            store [4 x i8] c"%s\\0A\\00", [4 x i8]* %1
            %2 = bitcast [4 x i8]* %1 to i8*
            call i32 (i8*, ...) @printf(i8* %2, i8* %s)
            ret void
            """);

        customLlvmCodeByMethodSignature.put(prophecySpecialTypeSymbols.getInternalArrayRangeMethodSignature(), """
            entry:
                %0 = sub i8 %end, %start
                %size = add i8 %0, 1
                %index = alloca i8
                store i8 1, i8* %index
                br label %for.cond

            for.cond:
                %1 = load i8, i8* %index
                %cond = icmp ult i8 %1, %size
                br i1 %cond, label %for.body, label %for.end

            for.body:
                %indexValue = load i8, i8* %index
                %2 = sub i8 %indexValue, 1
                %value = add i8 %2, %start

                %arrayValuePointer = getelementptr i8, i8* %array, i8 %indexValue
                store i8 %value, i8* %arrayValuePointer

                %newIndexValue = add i8 %indexValue, 1
                store i8 %newIndexValue, i8* %index
                br label %for.cond

            for.end:
                ret void
            """);

        return customLlvmCodeByMethodSignature;
    }
}
