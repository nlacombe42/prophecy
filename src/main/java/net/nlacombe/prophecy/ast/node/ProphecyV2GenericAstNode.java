package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyV2GenericAstNode extends AbstractProphecyV2AstNode {

    private final List<ProphecyV2AstNode> children;
    private final String typeName;

    public ProphecyV2GenericAstNode(SourceCodeLocation definitionSourceCodeLocation, List<ProphecyV2AstNode> children, String typeName) {
        super(definitionSourceCodeLocation);
        this.children = children;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<ProphecyV2AstNode> getChildren() {
        return children;
    }
}
