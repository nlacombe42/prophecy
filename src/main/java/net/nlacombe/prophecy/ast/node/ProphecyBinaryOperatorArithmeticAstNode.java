package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.symboltable.domain.Type;

import java.util.Arrays;
import java.util.List;

public class ProphecyBinaryOperatorArithmeticAstNode extends AbstractProphecyExpressionAstNode {

    public enum OperationType {
        ADDITION("+"),
        SUBTRACTION("-"),
        ;

        private final String operatorSymbol;

        OperationType(String operatorSymbol) {
            this.operatorSymbol = operatorSymbol;
        }

        public String getOperatorSymbol() {
            return operatorSymbol;
        }

        public static OperationType fromOperatorSymbol(String operatorSymbol) {
            return Arrays.stream(values())
                .filter(operationType -> operationType.getOperatorSymbol().equals(operatorSymbol))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("no OperationType with operatorSymbol: " + operatorSymbol));
        }
    }

    private final OperationType operationType;
    private final ProphecyExpressionAstNode left;
    private final ProphecyExpressionAstNode right;

    public ProphecyBinaryOperatorArithmeticAstNode(
        SourceCodeLocation definitionSourceCodeLocation,
        OperationType operationType,
        ProphecyExpressionAstNode left,
        ProphecyExpressionAstNode right
    ) {
        super(definitionSourceCodeLocation);

        this.operationType = operationType;
        this.left = left;
        this.right = right;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public ProphecyExpressionAstNode getLeft() {
        return left;
    }

    public ProphecyExpressionAstNode getRight() {
        return right;
    }

    public void setEvaluatedType(Type evaluatedType) {
        this.evaluatedType = evaluatedType;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return List.of(left, right);
    }

    @Override
    public String toString() {
        return left.toString() + " " + operationType.getOperatorSymbol() + " " + right.toString();
    }
}
