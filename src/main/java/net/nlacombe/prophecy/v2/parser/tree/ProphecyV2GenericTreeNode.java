package net.nlacombe.prophecy.v2.parser.tree;

import java.util.List;

public class ProphecyV2GenericTreeNode implements ProphecyV2TreeNode {

    private final List<ProphecyV2TreeNode> children;
    private final String typeName;

    public ProphecyV2GenericTreeNode(String typeName, List<ProphecyV2TreeNode> children) {
        this.typeName = typeName;
        this.children = children;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<ProphecyV2TreeNode> getChildren() {
        return children;
    }
}
