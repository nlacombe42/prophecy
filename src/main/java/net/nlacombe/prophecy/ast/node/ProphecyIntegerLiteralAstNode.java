package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyIntegerLiteralAstNode extends AbstractProphecyExpressionAstNode {

    private final int literalValue;

    public ProphecyIntegerLiteralAstNode(SourceCodeLocation definitionSourceCodeLocation, int literalValue) {
        super(definitionSourceCodeLocation, BootstrapTypeSymbols.getInstance().getUInt8Class());

        this.literalValue = literalValue;
    }

    public int getLiteralValue() {
        return literalValue;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return "" + literalValue;
    }
}
