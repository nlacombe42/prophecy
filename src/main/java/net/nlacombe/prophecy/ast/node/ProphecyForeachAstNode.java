package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ProphecyForeachAstNode extends AbstractProphecyAstNode {

    private final String variableName;
    private final ProphecyExpressionAstNode expression;
    private final List<ProphecyAstNode> block;

    private VariableSymbol variableSymbol;

    public ProphecyForeachAstNode(
        SourceCodeLocation definitionSourceCodeLocation,
        String variableName,
        ProphecyExpressionAstNode expression,
        List<ProphecyAstNode> block
    ) {
        super(definitionSourceCodeLocation);

        this.variableName = variableName;
        this.expression = expression;
        this.block = block;
    }

    public String getVariableName() {
        return variableName;
    }

    public ProphecyExpressionAstNode getExpression() {
        return expression;
    }

    public List<ProphecyAstNode> getBlock() {
        return block;
    }

    public VariableSymbol getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(VariableSymbol variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return ListUtils.union(List.of(expression), block);
    }

    @Override
    public String toString() {
        var blockText = block.stream()
            .map(statement -> statement.toString() + "\n")
            .collect(Collectors.joining());

        return "foreach $variableName in $expression\n$block"
            .replace("$variableName", variableName)
            .replace("$expression", expression.toString())
            .replace("$block", blockText.indent(4))
            ;
    }
}
