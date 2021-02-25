package net.nlacombe.prophecy.v2.analyser.symboltable;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.v2.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.v2.constants.ConstantsV2;

public class SymbolTableBuilderV2 {

    public GlobalScope buildSymbolTable(ProphecyV2AstNode astRoot) {
        var globalScope = new GlobalScope();

        BootstrapTypeSymbols.getInstance().getAll().forEach(globalScope::define);

        var mainMethod = getMainMethodSymbol(globalScope);
        globalScope.define(mainMethod);

        new SymbolDefinerV2(globalScope, mainMethod).visit(astRoot);
        new SymbolResolverV2().visit(astRoot);

        return globalScope;
    }

    private MethodSymbol getMainMethodSymbol(GlobalScope globalScope) {
        var mainMethod = new MethodSymbol(ConstantsV2.MAIN_METHOD_SIGNATURE.getName(), BootstrapTypeSymbols.getInstance().getVoidClass(), null, new LocalScope(globalScope));
        mainMethod.setEnclosingScope(globalScope);

        return mainMethod;
    }
}
