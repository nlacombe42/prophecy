package net.nlacombe.prophecy.builtintypes;

import net.nlacombe.prophecy.symboltable.domain.NamedParameterType;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;

import java.util.List;

public class BootstrapTypeSymbols {

    private static BootstrapTypeSymbols instance;

    private final ClassSymbol voidClass;
    private final ClassSymbol objectClass;
    private final ClassSymbol uInt8Class;
    private final ClassSymbol arrayClass;
    private final ClassSymbol stringClass;
    private final MethodSymbol systemPrintlnUInt8;
    private final MethodSymbol systemPrintlnString;

    private BootstrapTypeSymbols() {
        objectClass = ClassSymbol.newFromClassDefinition("Object", null, null);
        voidClass = ClassSymbol.newFromClassDefinition("Void", objectClass, null);
        uInt8Class = ClassSymbol.newFromClassDefinition("UInt8", objectClass, null);
        arrayClass = getArrayClassSymbol(objectClass, uInt8Class);
        stringClass = ClassSymbol.newFromClassDefinition("String", objectClass, null);
        systemPrintlnUInt8 = getSystemPrintlnUInt8MethodSymbol(voidClass, uInt8Class);
        systemPrintlnString = getSystemPrintlnStringMethodSymbol(voidClass, stringClass);
    }

    public static BootstrapTypeSymbols getInstance() {
        if (instance == null)
            instance = new BootstrapTypeSymbols();

        return instance;
    }

    public List<Symbol> getAll() {
        return List.of(voidClass, objectClass, uInt8Class, arrayClass, stringClass, systemPrintlnUInt8, systemPrintlnString);
    }

    private ClassSymbol getArrayClassSymbol(ClassSymbol objectClass, ClassSymbol uInt8Class) {
        var parameterType = new NamedParameterType("T");

        var arrayClass = ClassSymbol.newFromClassDefinition("Array", objectClass, null, List.of(parameterType));
        var indexParameter = new VariableSymbol("index", uInt8Class);
        arrayClass.define(MethodSymbol.newClassMethod("get", parameterType, arrayClass, false, List.of(indexParameter)));

        return arrayClass;
    }

    private MethodSymbol getSystemPrintlnUInt8MethodSymbol(ClassSymbol voidClass, ClassSymbol uInt8Class) {
        var parameters = List.of(new VariableSymbol("i", uInt8Class));

        return MethodSymbol.newGlobalMethod("println", voidClass, null, parameters);
    }

    private MethodSymbol getSystemPrintlnStringMethodSymbol(ClassSymbol voidClass, ClassSymbol stringClass) {
        var parameters = List.of(new VariableSymbol("s", stringClass));

        return MethodSymbol.newGlobalMethod("println", voidClass, null, parameters);
    }

    public ClassSymbol getVoidClass() {
        return voidClass;
    }

    public ClassSymbol getObjectClass() {
        return objectClass;
    }

    public ClassSymbol getUInt8Class() {
        return uInt8Class;
    }

    public ClassSymbol getArrayClass() {
        return arrayClass;
    }

    public ClassSymbol getStringClass() {
        return stringClass;
    }

    public MethodSymbol getSystemPrintlnUInt8() {
        return systemPrintlnUInt8;
    }

    public MethodSymbol getSystemPrintlnString() {
        return systemPrintlnString;
    }
}
