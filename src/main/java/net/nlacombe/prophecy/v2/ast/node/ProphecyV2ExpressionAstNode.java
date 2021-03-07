package net.nlacombe.prophecy.v2.ast.node;

import net.nlacombe.prophecy.shared.symboltable.domain.Type;

public interface ProphecyV2ExpressionAstNode extends ProphecyV2AstNode {

    Type getEvaluatedType();

}
