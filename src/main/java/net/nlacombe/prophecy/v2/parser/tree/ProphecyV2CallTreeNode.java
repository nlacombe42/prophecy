package net.nlacombe.prophecy.v2.parser.tree;

import java.util.List;

public class ProphecyV2CallTreeNode implements ProphecyV2TreeNode {

    private final String methodName;
    private final List<ProphecyV2TreeNode> arguments;

    public ProphecyV2CallTreeNode(String methodName, List<ProphecyV2TreeNode> arguments) {
        this.methodName = methodName;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<ProphecyV2TreeNode> getArguments() {
        return arguments;
    }

    @Override
    public List<ProphecyV2TreeNode> getChildren() {
        return List.of();
    }
}
