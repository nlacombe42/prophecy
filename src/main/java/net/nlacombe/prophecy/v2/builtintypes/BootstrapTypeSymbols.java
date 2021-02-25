package net.nlacombe.prophecy.v2.builtintypes;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.VariableSymbol;

import java.util.List;

public class BootstrapTypeSymbols {

    private static BootstrapTypeSymbols instance;

    private final ClassSymbol voidClass;
    private final ClassSymbol objectClass;
    private final ClassSymbol stringClass;
    private final ClassSymbol integerClass;
    private final MethodSymbol systemPrintlnInt;
    private final MethodSymbol systemPrintlnString;

    private BootstrapTypeSymbols() {
        voidClass = new ClassSymbol("Void", null, null);
        objectClass = new ClassSymbol("Object", null, null);
        integerClass = new ClassSymbol("Integer", null, objectClass);
        stringClass = new ClassSymbol("String", null, objectClass);
        systemPrintlnInt = getSystemPrintlnIntMethodSymbol(voidClass, integerClass);
        systemPrintlnString = getSystemPrintlnStringMethodSymbol(voidClass, stringClass);
    }

    public static BootstrapTypeSymbols getInstance() {
        if (instance == null)
            instance = new BootstrapTypeSymbols();

        return instance;
    }

    public List<Symbol> getAll() {
        return List.of(voidClass, objectClass, integerClass, stringClass, systemPrintlnInt, systemPrintlnString);
    }

    private MethodSymbol getSystemPrintlnIntMethodSymbol(ClassSymbol voidClass, ClassSymbol integerClass) {
        var methodSymbol = new MethodSymbol("println", voidClass, null, new LocalScope(null));
        methodSymbol.setStatic(true);
        methodSymbol.putMember(new VariableSymbol("i", integerClass));

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

    public ClassSymbol getIntegerClass() {
        return integerClass;
    }

    public ClassSymbol getStringClass() {
        return stringClass;
    }

    public MethodSymbol getSystemPrintlnInt() {
        return systemPrintlnInt;
    }

    public MethodSymbol getSystemPrintlnString() {
        return systemPrintlnString;
    }
}
