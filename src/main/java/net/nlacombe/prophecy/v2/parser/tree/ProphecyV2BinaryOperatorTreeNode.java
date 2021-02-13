package net.nlacombe.prophecy.v2.parser.tree;

import java.util.List;

public class ProphecyV2BinaryOperatorTreeNode implements ProphecyV2TreeNode {

    public enum OperationType {
        MULTIPLICATION, DIVISION, ADDITION, SUBTRACTION
    }

    private final OperationType operationType;
    private final ProphecyV2TreeNode leftOperand;
    private final ProphecyV2TreeNode rightOperand;

    public ProphecyV2BinaryOperatorTreeNode(OperationType operationType, ProphecyV2TreeNode leftOperand, ProphecyV2TreeNode rightOperand) {
        this.operationType = operationType;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public ProphecyV2TreeNode getLeftOperand() {
        return leftOperand;
    }

    public ProphecyV2TreeNode getRightOperand() {
        return rightOperand;
    }

    @Override
    public List<ProphecyV2TreeNode> getChildren() {
        return List.of(leftOperand, rightOperand);
    }
}
