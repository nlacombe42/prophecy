package net.nlacombe.prophecy.v2.analyser.symboltable;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.VariableSymbol;
import net.nlacombe.prophecy.v2.constants.ConstantsV2;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;

public class SymbolTableBuilderV2 {

    public GlobalScope buildSymbolTable(ProphecyV2AstNode astRoot) {
        var globalScope = new GlobalScope();
        globalScope.define(getSystemPrintlnMethodSymbol(globalScope));

        var mainMethod = new MethodSymbol(ConstantsV2.MAIN_METHOD_SIGNATURE.getName(), BuiltInTypeSymbol.tVoid, null, new LocalScope(globalScope));
        mainMethod.setEnclosingScope(globalScope);
        globalScope.define(mainMethod);

        new SymbolDefinerV2(globalScope, mainMethod).visit(astRoot);
        new SymbolResolverV2().visit(astRoot);

        return globalScope;
    }

    private MethodSymbol getSystemPrintlnMethodSymbol(GlobalScope globalScope) {
        var system_println = new MethodSymbol(ConstantsV2.PRINTLN_INT_SYSTEM_METHOD_SIGNATURE.getName(), BuiltInTypeSymbol.tVoid, null, new LocalScope(globalScope));
        system_println.setStatic(true);
        system_println.putMember(new VariableSymbol("i", BuiltInTypeSymbol.tInt));

        return system_println;
    }
}
