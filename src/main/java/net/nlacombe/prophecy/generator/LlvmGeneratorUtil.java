package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LlvmGeneratorUtil {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
    private static final ProphecySpecialTypeSymbols specialTypeSymbols = ProphecySpecialTypeSymbols.getInstance();

    public static String getLlvmType(Type type) {
        if (Type.sameType(type, bootstrapTypeSymbols.getVoidClass()))
            return "void";
        else if (Type.sameType(type, bootstrapTypeSymbols.getUInt8Class()))
            return "i8";
        else if (Type.sameType(type, bootstrapTypeSymbols.getStringClass()) || Type.sameType(type, specialTypeSymbols.getUInt8Array()))
            return "i8*";
        else
            throw new ProphecyCompilerException("Unimplemented llvm type for prophecy type: " + type);
    }

    public static String getLlvmFunctionName(MethodSymbol methodSymbol) {
        return "@" + getLlvmNameFromNameParts(getFunctionNameParts(methodSymbol));
    }

    public static String getLlvmVariableName(String variableName) {
        return "%" + variableName;
    }

    public static String toLlvmStringLiteral(String stringValue) {
        return stringValue.chars()
            .mapToObj(codepoint ->
                isPrintableAscii(codepoint) && !(codepoint == '\\' || codepoint == '"') ?
                    "" + (char) codepoint
                    :
                    toLlvmStringLiteralHexSequence(codepoint)
            )
            .collect(Collectors.joining());
    }

    public static String toLlvmStringLiteral(List<Integer> uInt8ArrayValues) {
        return uInt8ArrayValues.stream()
            .map(LlvmGeneratorUtil::toLlvmStringLiteralHexSequence)
            .collect(Collectors.joining());
    }

    public static String getLlvmReferenceFromType(Type type) {
        var llvmType = getLlvmType(type);

        if (llvmType.endsWith("**"))
            return llvmType;
        else if (llvmType.endsWith("*"))
            return llvmType + "*";
        else
            return llvmType + "**";
    }

    private static String toLlvmStringLiteralHexSequence(int integer) {
        var javaHexString = Integer.toHexString(integer);

        if (javaHexString.length() < 2)
            javaHexString = "0" + javaHexString;

        return "\\" + javaHexString;
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

        return ListUtils.union(List.of(signature.getMethodName()), parameterNameParts);
    }

    private static String getLlvmNamePartSeparator() {
        return "$";
    }
}
