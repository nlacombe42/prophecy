package net.nlacombe.prophecy.analyser.symboltable;

import net.nlacombe.prophecy.ast.ProphecyAstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIdentifierExpressionAstNode;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.symboltable.domain.signature.NameOnlySymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;

public class SymbolResolverV2 extends ProphecyAstVisitor<Void> {

    private final BuildMessageService buildMessageService;

    public SymbolResolverV2(BuildMessageService buildMessageService) {
        this.buildMessageService = buildMessageService;
    }

    @Override
    protected Void visitProphecyIdentifierExpressionAstNode(ProphecyIdentifierExpressionAstNode node) {
        var identifier = node.getIdentifier();
        var symbol = node.getEnclosingScope().resolve(new NameOnlySymbolSignature(identifier));

        if (symbol == null) {
            this.buildMessageService.error(node.getDefinitionSourceCodeLocation(), "unknown identifier: " + identifier);
            return null;
        }

        node.setSymbol(symbol);

        return null;
    }

    @Override
    protected Void defaultForNonImplementedNodeTypes(ProphecyAstNode node) {
        return visitChildren(node);
    }
}
