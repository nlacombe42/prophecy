package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.Type;

public interface ProphecyExpressionAstNode extends ProphecyAstNode {

    Type getEvaluatedType();

}
