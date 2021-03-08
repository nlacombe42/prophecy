package net.nlacombe.prophecy.v2.generator.llvm;

import net.nlacombe.prophecy.shared.symboltable.domain.MethodSignature;
import net.nlacombe.prophecy.shared.symboltable.domain.Type;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.v2.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LlvmGeneratorUtil {

    public static String getLlvmType(Type type) {
        if (BootstrapTypeSymbols.getInstance().getVoidClass().equals(type))
            return "void";
        if (BootstrapTypeSymbols.getInstance().getUInt8Class().equals(type))
            return "i8";
        if (BootstrapTypeSymbols.getInstance().getStringClass().equals(type))
            return "i8*";
        else
            throw new ProphecyCompilerException("Unimplemented llvm type for prophecy type: " + type);
    }

    public static String getLlvmFunctionName(MethodSymbol methodSymbol) {
        return "@" + getLlvmNameFromNameParts(getFunctionNameParts(methodSymbol));
    }

    public static String toLlvmStringLiteral(String stringValue) {
        return stringValue.chars()
            .mapToObj(codepoint -> {
                if (isPrintableAscii(codepoint) && !(codepoint == '\\' || codepoint == '"'))
                    return "" + (char) codepoint;
                else {
                    var javaHexString = Integer.toHexString(codepoint);

                    if (javaHexString.length() < 2)
                        javaHexString = "0" + javaHexString;

                    return "\\" + javaHexString;
                }
            })
            .collect(Collectors.joining());
    }

    private static boolean isPrintableAscii(int codepoint) {
        return codepoint >= 32 && codepoint <= 127;
    }

    private static String getLlvmNameFromNameParts(List<String> nameParts) {
        return String.join(getLlvmNamePartSeparator(), nameParts);
    }

    private static List<String> getFunctionNameParts(MethodSymbol methodSymbol) {
        var functionNamePartsLlvm = new ArrayList<String>();

        if (methodSymbol.getParentClass() != null)
            functionNamePartsLlvm.add(methodSymbol.getParentClass().getName());

        functionNamePartsLlvm.addAll(getNameParts(methodSymbol.getSignature()));

        return functionNamePartsLlvm;
    }

    private static List<String> getNameParts(MethodSignature signature) {
        var parameterNameParts = signature.getParameterTypes().stream()
            .map(Type::getName)
            .collect(Collectors.toList());

        return ListUtils.union(List.of(signature.getName()), parameterNameParts);
    }

    private static String getLlvmNamePartSeparator() {
        return "$";
    }

}
