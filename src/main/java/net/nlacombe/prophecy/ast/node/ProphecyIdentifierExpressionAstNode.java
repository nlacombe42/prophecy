package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;

import java.util.List;

public class ProphecyIdentifierExpressionAstNode extends AbstractProphecyAstNode implements ProphecyExpressionAstNode {

    private final String identifier;

    private Symbol symbol;

    public ProphecyIdentifierExpressionAstNode(SourceCodeLocation definitionSourceCodeLocation, String identifier) {
        super(definitionSourceCodeLocation);

        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public Type getEvaluatedType() {
        return symbol == null ? null : symbol.getType();
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return identifier;
    }
}
