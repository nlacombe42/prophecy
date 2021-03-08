package net.nlacombe.prophecy.analyser.symboltable;

import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.ast.ProphecyV2AstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.ast.node.ProphecyV2FileAstNode;

public class SymbolDefinerV2 extends ProphecyV2AstVisitor<Void> {

    private final GlobalScope globalScope;
    private final MethodSymbol mainMethodSymbol;

    private Scope currentScope;

    public SymbolDefinerV2(GlobalScope globalScope, MethodSymbol mainMethodSymbol) {
        this.globalScope = globalScope;
        this.mainMethodSymbol = mainMethodSymbol;

        currentScope = globalScope;
    }

    @Override
    protected void enterEveryAstNode(ProphecyV2AstNode node) {
        node.setEnclosingScope(currentScope);
    }

    @Override
    protected Void visitFileAstNode(ProphecyV2FileAstNode node) {
        node.setGlobalScope(globalScope);
        mainMethodSymbol.setDefinitionAstNode(node);

        currentScope = mainMethodSymbol;

        return visitChildren(node);
    }

    @Override
    protected Void defaultForNonImplementedNodeTypes(ProphecyV2AstNode node) {
        return null;
    }
}
