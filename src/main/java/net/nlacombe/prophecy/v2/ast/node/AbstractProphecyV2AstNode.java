package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        var simpleName = getClass().getSimpleName();
        var children = getChildren();

        if (children == null || children.isEmpty())
            return "(" + simpleName +")";

        var childrenText = children.stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n"));

        return "(" + simpleName + "\n" + childrenText.indent(2) + ")";
    }
}
