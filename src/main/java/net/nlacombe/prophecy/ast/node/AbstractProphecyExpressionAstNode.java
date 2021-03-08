package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

public abstract class AbstractProphecyExpressionAstNode extends AbstractProphecyAstNode implements ProphecyExpressionAstNode {

    protected Type evaluatedType;

    public AbstractProphecyExpressionAstNode(SourceCodeLocation definitionSourceCodeLocation, Type evaluatedType) {
        super(definitionSourceCodeLocation);

        this.evaluatedType = evaluatedType;
    }

    public AbstractProphecyExpressionAstNode(SourceCodeLocation definitionSourceCodeLocation) {
        this(definitionSourceCodeLocation, null);
    }

    public Type getEvaluatedType() {
        return evaluatedType;
    }
}
