package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

public abstract class AbstractProphecyV2AstNode implements ProphecyV2AstNode {

    private final SourceCodeLocation definitionSourceCodeLocation;
    private Scope scope;

    public AbstractProphecyV2AstNode(SourceCodeLocation definitionSourceCodeLocation) {
        this.definitionSourceCodeLocation = definitionSourceCodeLocation;
    }

    @Override
    public SourceCodeLocation getDefinitionSourceCodeLocation() {
        return definitionSourceCodeLocation;
    }

    @Override
    public Scope getEnclosingScope() {
        return scope;
    }

    @Override
    public void setEnclosingScope(Scope scope) {
        this.scope = scope;
    }
}
