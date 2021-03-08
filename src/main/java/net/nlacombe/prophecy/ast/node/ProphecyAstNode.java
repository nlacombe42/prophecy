package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public interface ProphecyAstNode {

    List<ProphecyAstNode> getChildren();

    SourceCodeLocation getDefinitionSourceCodeLocation();

    Scope getEnclosingScope();

    void setEnclosingScope(Scope scope);
}
