package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

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
