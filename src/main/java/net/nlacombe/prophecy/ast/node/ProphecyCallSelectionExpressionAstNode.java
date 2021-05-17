package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.util.CollectionUtil;

import java.util.List;

public class ProphecyCallSelectionExpressionAstNode extends AbstractProphecyExpressionAstNode {

    private final ProphecyExpressionAstNode selectionExpression;
    private final ProphecyCallAstNode call;

    public ProphecyCallSelectionExpressionAstNode
        (
            SourceCodeLocation definitionSourceCodeLocation,
            ProphecyExpressionAstNode selectionExpression,
            ProphecyCallAstNode call
        ) {
        super(definitionSourceCodeLocation);

        this.selectionExpression = selectionExpression;
        this.call = call;
    }

    public ProphecyExpressionAstNode getSelectionExpression() {
        return selectionExpression;
    }

    public ProphecyCallAstNode getCall() {
        return call;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return CollectionUtil.castToGeneric(List.of(selectionExpression, call), ProphecyAstNode.class);
    }

    public void setEvaluatedType(Type evaluatedType) {
        this.evaluatedType = evaluatedType;
    }

    @Override
    public String toString() {
        return "$selectionExpression.$call"
            .replace("$selectionExpression", selectionExpression.toString())
            .replace("$call", call.toString());
    }
}
