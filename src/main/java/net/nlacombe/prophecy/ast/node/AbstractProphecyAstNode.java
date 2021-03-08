package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.stream.Collectors;

public abstract class AbstractProphecyAstNode implements ProphecyAstNode {

    private final SourceCodeLocation definitionSourceCodeLocation;
    private Scope scope;

    public AbstractProphecyAstNode(SourceCodeLocation definitionSourceCodeLocation) {
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
