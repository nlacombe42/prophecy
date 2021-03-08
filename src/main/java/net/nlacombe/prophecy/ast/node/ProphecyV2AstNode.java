package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public interface ProphecyV2AstNode {

    List<ProphecyV2AstNode> getChildren();

    SourceCodeLocation getDefinitionSourceCodeLocation();

    Scope getEnclosingScope();

    void setEnclosingScope(Scope scope);
}
