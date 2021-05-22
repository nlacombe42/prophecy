package net.nlacombe.prophecy.builtintypes;

import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;

import java.util.List;
import java.util.Map;

public class ProphecySpecialTypeSymbols {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
    private static final ClassSymbol uInt8Class = bootstrapTypeSymbols.getUInt8Class();

    private static ProphecySpecialTypeSymbols instance;

    private final ClassSymbol uInt8Array;

    private ProphecySpecialTypeSymbols() {
        uInt8Array = createUInt8Array();
    }

    public static ProphecySpecialTypeSymbols getInstance() {
        if (instance == null)
            instance = new ProphecySpecialTypeSymbols();

        return instance;
    }

    public ClassSymbol getUInt8Array() {
        return uInt8Array;
    }

    private static ClassSymbol createUInt8Array() {
        var arrayClass = bootstrapTypeSymbols.getArrayClass();
        var parameterType = arrayClass.getParameterTypes().get(0);

        return arrayClass.substitute(Map.of(parameterType, uInt8Class));
    }

    public MethodSignature getUInt8ArrayGetMethodSignature() {
        var methodSymbol = uInt8Array.resolve(new MethodSignature("get", List.of(uInt8Class)));

        return (MethodSignature) methodSymbol.getSignature();
    }

    public MethodSignature getUInt8ArraySizeMethodSignature() {
        var methodSymbol = uInt8Array.resolve(new MethodSignature("size", List.of()));

        return (MethodSignature) methodSymbol.getSignature();
    }

    public MethodSignature getSystemPrintlnUInt8MethodSignature() {
        var systemClass = bootstrapTypeSymbols.getSystemClass();
        var methodSymbol = systemClass.resolve(new MethodSignature("println", List.of(uInt8Class)));

        return (MethodSignature) methodSymbol.getSignature();
    }

    public MethodSignature getSystemPrintlnStringMethodSignature() {
        var systemClass = bootstrapTypeSymbols.getSystemClass();
        var stringClass = bootstrapTypeSymbols.getStringClass();
        var methodSymbol = systemClass.resolve(new MethodSignature("println", List.of(stringClass)));

        return (MethodSignature) methodSymbol.getSignature();
    }

    public MethodSignature getInternalArrayRangeMethodSignature() {
        var methodSymbol = uInt8Array.resolve(new MethodSignature("$range", List.of(uInt8Array, uInt8Class, uInt8Class)));

        return (MethodSignature) methodSymbol.getSignature();
    }
}
