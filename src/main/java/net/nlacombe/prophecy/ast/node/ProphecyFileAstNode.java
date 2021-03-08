package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProphecyFileAstNode extends AbstractProphecyAstNode {

    private final List<ProphecyAstNode> statements;

    private GlobalScope globalScope;

    public ProphecyFileAstNode(SourceCodeLocation sourceCodeLocation, List<ProphecyAstNode> statements) {
        super(sourceCodeLocation);

        this.statements = statements;
    }

    public List<ProphecyAstNode> getStatements() {
        return statements;
    }

    public GlobalScope getGlobalScope() {
        return globalScope;
    }

    public void setGlobalScope(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return statements;
    }

    @Override
    public String toString() {
        return statements.stream()
            .map(Objects::toString)
            .collect(Collectors.joining("\n"));
    }
}
