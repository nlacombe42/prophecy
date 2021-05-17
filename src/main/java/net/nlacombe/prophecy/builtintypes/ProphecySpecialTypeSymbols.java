package net.nlacombe.prophecy.builtintypes;

import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;

import java.util.List;
import java.util.Map;

public class ProphecySpecialTypeSymbols {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();

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

        return arrayClass.substitute(Map.of(parameterType, bootstrapTypeSymbols.getUInt8Class()));
    }

    public MethodSignature getUInt8ArrayGetMethodSignature() {
        var methodSymbol = uInt8Array.resolve(new MethodSignature("get", List.of(bootstrapTypeSymbols.getUInt8Class())));

        return (MethodSignature) methodSymbol.getSignature();
    }

    public MethodSignature getUInt8ArraySizeMethodSignature() {
        var methodSymbol = uInt8Array.resolve(new MethodSignature("size", List.of()));

        return (MethodSignature) methodSymbol.getSignature();
    }
}
