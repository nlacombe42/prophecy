package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.util.CollectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProphecyCallAstNode extends AbstractProphecyExpressionAstNode {

    private final ProphecyExpressionAstNode expression;
    private final String methodName;
    private final List<ProphecyExpressionAstNode> arguments;
    private MethodSymbol methodSymbol;

    public ProphecyCallAstNode(
        SourceCodeLocation definitionSourceCodeLocation,
        ProphecyExpressionAstNode expression,
        String methodName,
        List<ProphecyExpressionAstNode> arguments
    ) {
        super(definitionSourceCodeLocation);

        this.expression = expression;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    public ProphecyExpressionAstNode getExpression() {
        return expression;
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
        var argumentsNodes = CollectionUtil.castToGeneric(arguments, ProphecyAstNode.class);

        return ListUtils.union(List.of(expression), argumentsNodes);
    }

    public void setEvaluatedType(Type evaluatedType) {
        this.evaluatedType = evaluatedType;
    }

    @Override
    public String toString() {
        var arguments = getArguments().stream()
            .map(Objects::toString)
            .collect(Collectors.joining(", "));

        return "$expression.$methodName($arguments)"
            .replace("$expression", "" + expression)
            .replace("$methodName", methodName)
            .replace("$arguments", arguments);
    }
}
