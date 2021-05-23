package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.util.WriterUtil;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class LlvmGeneratorCallUtil {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();

    public static LlvmSymbol generateCallToProphecyMethod(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, MethodSymbol methodSymbol, List<LlvmSymbol> arguments) {
        var parameterLlvmTypes = methodSymbol.getParameters().stream()
            .map(Symbol::getType)
            .map(LlvmGeneratorUtil::getLlvmType)
            .collect(Collectors.toList());
        var llvmArguments = arguments.stream()
            .map(argumentSymbol -> argumentSymbol.getType() + " " + argumentSymbol.getName())
            .collect(Collectors.joining(", "));

        var llvmCode = "call $returnType ($parameterTypes) $functionName($arguments) ; prophecy call\n"
            .replace("$returnType", LlvmGeneratorUtil.getLlvmType(methodSymbol.getType()))
            .replace("$parameterTypes", String.join(", ", parameterLlvmTypes))
            .replace("$functionName", LlvmGeneratorUtil.getLlvmFunctionName(methodSymbol))
            .replace("$arguments", llvmArguments);

        LlvmSymbol returnLlvmSymbol;

        if (Type.sameType(methodSymbol.getType(), bootstrapTypeSymbols.getVoidClass()))
            returnLlvmSymbol = null;
        else {
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();

            llvmCode = returnValueName + " = " + llvmCode;
            returnLlvmSymbol = new LlvmSymbol(LlvmGeneratorUtil.getLlvmType(methodSymbol.getType()), returnValueName);
        }

        WriterUtil.writeRuntimeException(writer, llvmCode);

        return returnLlvmSymbol;
    }

}
