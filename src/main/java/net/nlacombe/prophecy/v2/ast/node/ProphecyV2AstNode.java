package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;

import java.util.List;

public interface ProphecyV2AstNode {

    List<ProphecyV2AstNode> getChildren();

    SourceCodeLocation getDefinitionSourceCodeLocation();

    Scope getEnclosingScope();

    void setEnclosingScope(Scope scope);
}
