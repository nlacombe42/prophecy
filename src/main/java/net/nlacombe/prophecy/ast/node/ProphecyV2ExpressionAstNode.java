package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.symboltable.domain.Type;

public interface ProphecyV2ExpressionAstNode extends ProphecyV2AstNode {

    Type getEvaluatedType();

}
