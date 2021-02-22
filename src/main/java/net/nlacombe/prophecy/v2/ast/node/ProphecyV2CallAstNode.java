package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.Type;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

import java.util.List;
import java.util.stream.Collectors;

public class ProphecyV2CallAstNode extends AbstractProphecyV2ExpressionAstNode {

    private final String methodName;
    private final List<ProphecyV2ExpressionAstNode> arguments;
    private MethodSymbol methodSymbol;

    public ProphecyV2CallAstNode(SourceCodeLocation definitionSourceCodeLocation, String methodName, List<ProphecyV2ExpressionAstNode> arguments) {
        super(definitionSourceCodeLocation);

        this.methodName = methodName;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<ProphecyV2ExpressionAstNode> getArguments() {
        return arguments;
    }

    public MethodSymbol getMethodSymbol() {
        return methodSymbol;
    }

    public void setMethodSymbol(MethodSymbol methodSymbol) {
        this.methodSymbol = methodSymbol;
    }

    @Override
    public List<ProphecyV2AstNode> getChildren() {
        return arguments.stream().map(node -> (ProphecyV2AstNode)node).collect(Collectors.toList());
    }

    public void setEvaluatedType(Type evaluatedType) {
        this.evaluatedType = evaluatedType;
    }
}
