package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIdentifierExpressionAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
            functionNamePartsLlvm.add(methodSymbol.getParentClass().getFullyQualifiedName());

        functionNamePartsLlvm.addAll(getNameParts(methodSymbol.getSignature()));

        return functionNamePartsLlvm.stream()
            .map(name -> name.replaceAll("[<>,]", "_"))
            .collect(Collectors.toList());
    }

    private static List<String> getNameParts(MethodSignature signature) {
        var parameterNameParts = signature.getParameterTypes().stream()
            .map(type -> type instanceof ClassSymbol ? ((ClassSymbol) type).getFullyQualifiedName() : type.getName())
            .collect(Collectors.toList());

        return ListUtils.union(List.of(signature.getMethodName()), parameterNameParts);
    }

    private static String getLlvmNamePartSeparator() {
        return "$";
    }

    public static List<VariableSymbol> getAllVariableSymbols(List<ProphecyAstNode> astNodes) {
        return astNodes.stream()
            .flatMap(astNode -> getAllSymbols(astNode).stream())
            .filter(symbol -> symbol instanceof VariableSymbol)
            .map(symbol -> (VariableSymbol) symbol)
            .distinct()
            .collect(Collectors.toList());
    }

    private static List<Symbol> getAllSymbols(ProphecyAstNode astNode) {
        var symbols = new LinkedList<Symbol>();

        if (astNode instanceof ProphecyIdentifierExpressionAstNode)
            symbols.add(((ProphecyIdentifierExpressionAstNode) astNode).getSymbol());

        symbols.addAll(
            astNode.getChildren().stream()
                .flatMap(astNodeStream -> getAllSymbols(astNodeStream).stream())
                .collect(Collectors.toList())
        );

        return symbols;
    }

    public static Map<VariableSymbol, String> getLlvmNameBySymbol(List<ProphecyAstNode> statements) {
        var allVariableSymbolsByName = LlvmGeneratorUtil.getAllVariableSymbols(statements).stream()
            .collect(Collectors.groupingBy(Symbol::getName, Collectors.mapping(Function.identity(), Collectors.toList())));

        return allVariableSymbolsByName.entrySet().stream()
            .flatMap(entry -> {
                var symbols = entry.getValue();

                if (symbols.size() == 1)
                    return Stream.of(Map.entry("%" + entry.getKey(), symbols.get(0)));

                return IntStream.range(0, symbols.size())
                    .mapToObj(index -> {
                        var symbol = symbols.get(index);

                        return Map.entry("%" + symbol.getName() + getLlvmNamePartSeparator() + (index + 1), symbol);
                    });
            })
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
