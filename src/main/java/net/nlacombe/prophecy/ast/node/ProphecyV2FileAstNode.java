package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProphecyV2FileAstNode extends AbstractProphecyV2AstNode {

    private final List<ProphecyV2AstNode> statements;

    private GlobalScope globalScope;

    public ProphecyV2FileAstNode(SourceCodeLocation sourceCodeLocation, List<ProphecyV2AstNode> statements) {
        super(sourceCodeLocation);

        this.statements = statements;
    }

    public List<ProphecyV2AstNode> getStatements() {
        return statements;
    }

    public GlobalScope getGlobalScope() {
        return globalScope;
    }

    public void setGlobalScope(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public List<ProphecyV2AstNode> getChildren() {
        return statements;
    }

    @Override
    public String toString() {
        return statements.stream()
            .map(Objects::toString)
            .collect(Collectors.joining("\n"));
    }
}
