package net.nlacombe.prophecy.analyser.symboltable;

import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.ast.ProphecyAstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;

public class SymbolDefinerV2 extends ProphecyAstVisitor<Void> {

    private final GlobalScope globalScope;
    private final MethodSymbol mainMethodSymbol;

    private Scope currentScope;

    public SymbolDefinerV2(GlobalScope globalScope, MethodSymbol mainMethodSymbol) {
        this.globalScope = globalScope;
        this.mainMethodSymbol = mainMethodSymbol;

        currentScope = globalScope;
    }

    @Override
    protected void enterEveryAstNode(ProphecyAstNode node) {
        node.setEnclosingScope(currentScope);
    }

    @Override
    protected Void visitFileAstNode(ProphecyFileAstNode node) {
        node.setGlobalScope(globalScope);
        mainMethodSymbol.setDefinitionAstNode(node);

        currentScope = mainMethodSymbol;

        return visitChildren(node);
    }

    @Override
    protected Void defaultForNonImplementedNodeTypes(ProphecyAstNode node) {
        return null;
    }
}
