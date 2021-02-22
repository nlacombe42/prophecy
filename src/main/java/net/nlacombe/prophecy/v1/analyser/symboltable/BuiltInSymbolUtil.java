package net.nlacombe.prophecy.v1.analyser.symboltable;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.VariableSymbol;
import net.nlacombe.prophecy.shared.constants.Constants;

public class BuiltInSymbolUtil {

    public static GlobalScope getGlobalScopeWithBuiltInSymbols() {
        var globalScope = new GlobalScope();

        BuiltInTypeSymbol.BUILT_IN_TYPES.forEach(globalScope::define);

        var stringClass = new ClassSymbol(Constants.STRING_CLASS_NAME, globalScope, null);
        globalScope.define(stringClass);

        var systemClass = getSystemClass(globalScope);
        globalScope.define(systemClass);

        var systemObject = new VariableSymbol("system", systemClass);
        globalScope.define(systemObject);

        return globalScope;
    }

    private static ClassSymbol getSystemClass(GlobalScope globalScope) {
        var systemClass = new ClassSymbol(Constants.SYSTEM_CLASS_NAME, globalScope, null, true);

        var system_println = new MethodSymbol(Constants.SYSTEM_PRINTLN_METHODSIGNATURE.getName(), BuiltInTypeSymbol.tVoid, systemClass, new LocalScope(systemClass));
        system_println.setStatic(true);
        system_println.putMember(new VariableSymbol(Constants.SYSTEM_PRINTLN_PARAM_NAME, BuiltInTypeSymbol.tInt));
        systemClass.putMember(system_println);

        return systemClass;
    }

}
