package net.nlacombe.prophecy.builtintypes;

import net.nlacombe.prophecy.symboltable.domain.NamedParameterType;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;

import java.util.List;
import java.util.Map;

public class BootstrapTypeSymbols {

    private static BootstrapTypeSymbols instance;

    private final ClassSymbol objectClass;
    private final ClassSymbol voidClass;
    private final ClassSymbol uInt8Class;
    private final ClassSymbol arrayClass;
    private final ClassSymbol stringClass;
    private final ClassSymbol systemClass;

    private BootstrapTypeSymbols() {
        objectClass = ClassSymbol.newFromClassDefinition("Object", null, null);
        voidClass = ClassSymbol.newFromClassDefinition("Void", objectClass, null);
        uInt8Class = ClassSymbol.newFromClassDefinition("UInt8", objectClass, null);
        arrayClass = getArrayClassSymbol(voidClass, objectClass, uInt8Class);
        stringClass = ClassSymbol.newFromClassDefinition("String", objectClass, null);
        systemClass = getSystemClassSymbol(objectClass, voidClass, uInt8Class, stringClass);
    }

    public static BootstrapTypeSymbols getInstance() {
        if (instance == null)
            instance = new BootstrapTypeSymbols();

        return instance;
    }

    public List<Symbol> getAll() {
        return List.of(
            objectClass,
            voidClass,
            uInt8Class,
            arrayClass,
            stringClass,
            systemClass
        );
    }

    private ClassSymbol getArrayClassSymbol(ClassSymbol voidClass, ClassSymbol objectClass, ClassSymbol uInt8Class) {
        var parameterType = new NamedParameterType("T");
        var indexParameter = new VariableSymbol("index", uInt8Class);
        var valueParameter = new VariableSymbol("value", parameterType);
        var arrayClass = ClassSymbol.newFromClassDefinition("Array", objectClass, null, List.of(parameterType));

        arrayClass.define(MethodSymbol.newClassMethod("get", parameterType, arrayClass, false, List.of(indexParameter)));
        arrayClass.define(MethodSymbol.newClassMethod("set", voidClass, arrayClass, false, List.of(indexParameter, valueParameter)));
        arrayClass.define(MethodSymbol.newClassMethod("size", uInt8Class, arrayClass, false, List.of()));
        arrayClass.define(getInternalUInt8ArrayRangeMethodSymbol(voidClass, arrayClass, uInt8Class));
        arrayClass.define(getArrayRangeMethodSymbol(arrayClass, uInt8Class));

        return arrayClass;
    }

    private ClassSymbol getSystemClassSymbol(ClassSymbol objectClass, ClassSymbol voidClass, ClassSymbol uInt8Class, ClassSymbol stringClass) {
        var systemClass = ClassSymbol.newFromClassDefinition("System", objectClass, null, List.of());

        systemClass.define(getSystemPrintlnUInt8MethodSymbol(systemClass, voidClass, uInt8Class));
        systemClass.define(getSystemPrintlnStringMethodSymbol(systemClass, voidClass, stringClass));

        return systemClass;
    }

    private MethodSymbol getSystemPrintlnUInt8MethodSymbol(ClassSymbol systemClass, ClassSymbol voidClass, ClassSymbol uInt8Class) {
        var parameters = List.of(new VariableSymbol("i", uInt8Class));

        return MethodSymbol.newClassMethod("println", voidClass, systemClass, true, parameters);
    }

    private MethodSymbol getSystemPrintlnStringMethodSymbol(ClassSymbol systemClass, ClassSymbol voidClass, ClassSymbol stringClass) {
        var parameters = List.of(new VariableSymbol("s", stringClass));

        return MethodSymbol.newClassMethod("println", voidClass, systemClass, true, parameters);
    }

    private MethodSymbol getInternalUInt8ArrayRangeMethodSymbol(ClassSymbol voidClass, ClassSymbol arrayClass, ClassSymbol uInt8Class) {
        var uInt8Array = getUInt8ArrayClass(arrayClass, uInt8Class);
        var parameters = List.of(
            new VariableSymbol("array", uInt8Array),
            new VariableSymbol("start", uInt8Class),
            new VariableSymbol("end", uInt8Class)
        );

        return MethodSymbol.newClassMethod("$range", voidClass, arrayClass, true, parameters);
    }

    private MethodSymbol getArrayRangeMethodSymbol(ClassSymbol arrayClass, ClassSymbol uInt8Class) {
        var uInt8ArrayClass = getUInt8ArrayClass(arrayClass, uInt8Class);
        var parameters = List.of(
            new VariableSymbol("start", uInt8Class),
            new VariableSymbol("end", uInt8Class)
        );

        return MethodSymbol.newClassMethod("range", uInt8ArrayClass, arrayClass, true, parameters);
    }

    private ClassSymbol getUInt8ArrayClass(ClassSymbol arrayClass, ClassSymbol uInt8Class) {
        var parameterType = arrayClass.getParameterTypes().get(0);

        return arrayClass.substitute(Map.of(parameterType, uInt8Class));
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

    public ClassSymbol getSystemClass() {
        return systemClass;
    }
}
