package net.nlacombe.prophecy.builtintypes;

import net.nlacombe.prophecy.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;

import java.util.List;

public class BootstrapTypeSymbols {

    private static BootstrapTypeSymbols instance;

    private final ClassSymbol voidClass;
    private final ClassSymbol objectClass;
    private final ClassSymbol stringClass;
    private final ClassSymbol uInt8Class;
    private final MethodSymbol systemPrintlnUInt8;
    private final MethodSymbol systemPrintlnString;

    private BootstrapTypeSymbols() {
        voidClass = new ClassSymbol("Void", null, null);
        objectClass = new ClassSymbol("Object", null, null);
        uInt8Class = new ClassSymbol("UInt8", null, objectClass);
        stringClass = new ClassSymbol("String", null, objectClass);
        systemPrintlnUInt8 = getSystemPrintlnUInt8MethodSymbol(voidClass, uInt8Class);
        systemPrintlnString = getSystemPrintlnStringMethodSymbol(voidClass, stringClass);
    }

    public static BootstrapTypeSymbols getInstance() {
        if (instance == null)
            instance = new BootstrapTypeSymbols();

        return instance;
    }

    public List<Symbol> getAll() {
        return List.of(voidClass, objectClass, uInt8Class, stringClass, systemPrintlnUInt8, systemPrintlnString);
    }

    private MethodSymbol getSystemPrintlnUInt8MethodSymbol(ClassSymbol voidClass, ClassSymbol uInt8Class) {
        var methodSymbol = new MethodSymbol("println", voidClass, null, new LocalScope(null));
        methodSymbol.setStatic(true);
        methodSymbol.putMember(new VariableSymbol("i", uInt8Class));

        return methodSymbol;
    }

    private MethodSymbol getSystemPrintlnStringMethodSymbol(ClassSymbol voidClass, ClassSymbol stringClass) {
        var methodSymbol = new MethodSymbol("println", voidClass, null, new LocalScope(null));
        methodSymbol.setStatic(true);
        methodSymbol.putMember(new VariableSymbol("s", stringClass));

        return methodSymbol;
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
