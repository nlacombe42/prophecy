package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.Type;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

public abstract class AbstractProphecyV2ExpressionAstNode extends AbstractProphecyV2AstNode implements ProphecyV2ExpressionAstNode {

    protected Type evaluatedType;

    public AbstractProphecyV2ExpressionAstNode(SourceCodeLocation definitionSourceCodeLocation, Type evaluatedType) {
        super(definitionSourceCodeLocation);

        this.evaluatedType = evaluatedType;
    }

    public AbstractProphecyV2ExpressionAstNode(SourceCodeLocation definitionSourceCodeLocation) {
        this(definitionSourceCodeLocation, null);
    }

    public Type getEvaluatedType() {
        return evaluatedType;
    }
}
