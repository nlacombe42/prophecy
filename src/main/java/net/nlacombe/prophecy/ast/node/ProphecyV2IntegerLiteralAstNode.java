package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyV2IntegerLiteralAstNode extends AbstractProphecyV2ExpressionAstNode {

    private final int literalValue;

    public ProphecyV2IntegerLiteralAstNode(SourceCodeLocation definitionSourceCodeLocation, int literalValue) {
        super(definitionSourceCodeLocation, BootstrapTypeSymbols.getInstance().getUInt8Class());

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
