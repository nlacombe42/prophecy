package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyGenericAstNode extends AbstractProphecyAstNode {

    private final List<ProphecyAstNode> children;
    private final String typeName;

    public ProphecyGenericAstNode(SourceCodeLocation definitionSourceCodeLocation, List<ProphecyAstNode> children, String typeName) {
        super(definitionSourceCodeLocation);
        this.children = children;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<ProphecyAstNode> getChildren() {
        return children;
    }
}
