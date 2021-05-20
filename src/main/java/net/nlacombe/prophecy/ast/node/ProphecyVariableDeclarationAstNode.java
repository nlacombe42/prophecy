package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;

import java.util.List;

public class ProphecyVariableDeclarationAstNode extends AbstractProphecyAstNode {

    private final String variableName;
    private final ProphecyExpressionAstNode initializer;

    private VariableSymbol variableSymbol;

    public ProphecyVariableDeclarationAstNode(SourceCodeLocation definitionSourceCodeLocation, String variableName, ProphecyExpressionAstNode initializer) {
        super(definitionSourceCodeLocation);
        this.variableName = variableName;
        this.initializer = initializer;
    }

    public String getVariableName() {
        return variableName;
    }

    public ProphecyExpressionAstNode getInitializer() {
        return initializer;
    }

    public VariableSymbol getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(VariableSymbol variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return List.of(initializer);
    }

    @Override
    public String toString() {
        return "val $variableName = $initializer"
            .replace("$variableName", variableName)
            .replace("$initializer", initializer.toString());
    }
}
