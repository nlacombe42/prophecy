package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProphecyCallAstNode extends AbstractProphecyExpressionAstNode {

    private final String methodName;
    private final List<ProphecyExpressionAstNode> arguments;
    private MethodSymbol methodSymbol;

    public ProphecyCallAstNode(SourceCodeLocation definitionSourceCodeLocation, String methodName, List<ProphecyExpressionAstNode> arguments) {
        super(definitionSourceCodeLocation);

        this.methodName = methodName;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<ProphecyExpressionAstNode> getArguments() {
        return arguments;
    }

    public MethodSymbol getMethodSymbol() {
        return methodSymbol;
    }

    public void setMethodSymbol(MethodSymbol methodSymbol) {
        this.methodSymbol = methodSymbol;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return arguments.stream().map(node -> (ProphecyAstNode)node).collect(Collectors.toList());
    }

    public void setEvaluatedType(Type evaluatedType) {
        this.evaluatedType = evaluatedType;
    }

    @Override
    public String toString() {
        var arguments = getArguments().stream()
            .map(Objects::toString)
            .collect(Collectors.joining(", "));

        return "$methodName($arguments)"
            .replace("$methodName", methodName)
            .replace("$arguments", arguments);
    }
}
