package net.nlacombe.prophecy.v2.parser.tree;

import java.util.List;

public class ProphecyV2IntegerLiteralTreeNode implements ProphecyV2TreeNode {

    private final String literalValue;

    public ProphecyV2IntegerLiteralTreeNode(String literalValue) {
        this.literalValue = literalValue;
    }

    public String getLiteralValue() {
        return literalValue;
    }

    @Override
    public List<ProphecyV2TreeNode> getChildren() {
        return List.of();
    }
}
