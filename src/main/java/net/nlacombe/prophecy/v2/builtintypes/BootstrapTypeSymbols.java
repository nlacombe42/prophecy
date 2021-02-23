package net.nlacombe.prophecy.v2.builtintypes;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.Symbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.VariableSymbol;

import java.util.List;

public class BootstrapTypeSymbols {

    private static BootstrapTypeSymbols instance;

    private final ClassSymbol objectClass;
    private final ClassSymbol stringClass;
    private final MethodSymbol systemPrintlnInt;
    private final MethodSymbol systemPrintlnString;

    private BootstrapTypeSymbols() {
        objectClass = new ClassSymbol("Object", null, null);
        stringClass = new ClassSymbol("String", null, objectClass);
        systemPrintlnInt = getSystemPrintlnIntMethodSymbol();
        systemPrintlnString = getSystemPrintlnStringMethodSymbol(stringClass);
    }

    public static BootstrapTypeSymbols getInstance() {
        if (instance == null)
            instance = new BootstrapTypeSymbols();

        return instance;
    }

    public List<Symbol> getAll() {
        return List.of(objectClass, stringClass, systemPrintlnInt, systemPrintlnString);
    }

    private MethodSymbol getSystemPrintlnIntMethodSymbol() {
        var methodSymbol = new MethodSymbol("println", BuiltInTypeSymbol.tVoid, null, new LocalScope(null));
        methodSymbol.setStatic(true);
        methodSymbol.putMember(new VariableSymbol("i", BuiltInTypeSymbol.tInt));

        return methodSymbol;
    }

    private MethodSymbol getSystemPrintlnStringMethodSymbol(ClassSymbol stringClass) {
        var methodSymbol = new MethodSymbol("println", BuiltInTypeSymbol.tVoid, null, new LocalScope(null));
        methodSymbol.setStatic(true);
        methodSymbol.putMember(new VariableSymbol("s", stringClass));

        return methodSymbol;
    }

    public ClassSymbol getObjectClass() {
        return objectClass;
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
