package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2TreeNode;

import java.util.List;

public class ProphecyV2TreeContext {

    private List<ProphecyV2TreeNode> currentChildren;

    public void addChild(ProphecyV2TreeNode child) {
        currentChildren.add(child);
    }

    public List<ProphecyV2TreeNode> getCurrentChildren() {
        return currentChildren;
    }
}
