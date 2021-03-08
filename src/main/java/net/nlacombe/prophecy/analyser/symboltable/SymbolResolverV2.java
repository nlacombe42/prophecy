package net.nlacombe.prophecy.analyser.symboltable;

import net.nlacombe.prophecy.ast.ProphecyAstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;

public class SymbolResolverV2 extends ProphecyAstVisitor<Void> {

    public SymbolResolverV2() {
    }

    @Override
    protected Void defaultForNonImplementedNodeTypes(ProphecyAstNode node) {
        return visitChildren(node);
    }
}
