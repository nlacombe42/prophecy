package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.v2.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyV2IntegerLiteralAstNode extends AbstractProphecyV2ExpressionAstNode {

    private final int literalValue;

    public ProphecyV2IntegerLiteralAstNode(SourceCodeLocation definitionSourceCodeLocation, int literalValue) {
        super(definitionSourceCodeLocation, BootstrapTypeSymbols.getInstance().getIntegerClass());

        this.literalValue = literalValue;
    }

    public int getLiteralValue() {
        return literalValue;
    }

    @Override
    public List<ProphecyV2AstNode> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return "" + literalValue;
    }
}
