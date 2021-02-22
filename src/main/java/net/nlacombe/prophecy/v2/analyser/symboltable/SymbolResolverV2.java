package net.nlacombe.prophecy.v2.analyser.symboltable;

import net.nlacombe.prophecy.v2.ast.ProphecyV2AstVisitor;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;

public class SymbolResolverV2 extends ProphecyV2AstVisitor<Void> {

    public SymbolResolverV2() {
    }

    @Override
    protected Void defaultForNonImplementedNodeTypes(ProphecyV2AstNode node) {
        return visitChildren(node);
    }
}
