package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProphecyV2FileAstNode extends AbstractProphecyV2AstNode {

    private final List<ProphecyV2AstNode> statements;

    private GlobalScope globalScope;

    public ProphecyV2FileAstNode(List<ProphecyV2AstNode> statements) {
        super(new SourceCodeLocation(null, null, null));

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
