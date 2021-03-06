package net.nlacombe.prophecy.analyser.symboltable;

import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.constants.ConstantsV2;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;

import java.util.List;

public class SymbolTableBuilderV2 {

    public GlobalScope buildSymbolTable(ProphecyAstNode astRoot, BuildMessageService buildMessageService) {
        var globalScope = new GlobalScope();

        BootstrapTypeSymbols.getInstance().getAll().forEach(globalScope::define);

        var mainMethod = getMainMethodSymbol(globalScope);
        globalScope.define(mainMethod);

        new SymbolDefinerV2(globalScope, mainMethod, buildMessageService).visit(astRoot);
        new SymbolResolverV2(buildMessageService).visit(astRoot);

        return globalScope;
    }

    private MethodSymbol getMainMethodSymbol(GlobalScope globalScope) {
        var voidClass = BootstrapTypeSymbols.getInstance().getVoidClass();

        return MethodSymbol.newGlobalMethod(ConstantsV2.MAIN_METHOD_SIGNATURE.getMethodName(), voidClass, globalScope, List.of());
    }
}
