package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.Type;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyV2IntegerLiteralAstNode extends AbstractProphecyV2ExpressionAstNode {

    private final String literalValue;

    public ProphecyV2IntegerLiteralAstNode(SourceCodeLocation definitionSourceCodeLocation, String literalValue) {
        super(definitionSourceCodeLocation, BuiltInTypeSymbol.tInt);

        this.literalValue = literalValue;
    }

    @Override
    public Type getEvaluatedType() {
        return BuiltInTypeSymbol.tInt;
    }

    public String getLiteralValue() {
        return literalValue;
    }

    @Override
    public List<ProphecyV2AstNode> getChildren() {
        return List.of();
    }
}
