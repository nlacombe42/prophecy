package net.nlacombe.prophecy.v1.ast.nodewrapper;

import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNodeType;

public class AstCall
{
	private final int METHOD = 0;
	private final int ARGUMENTS = 1;

	private ProphecyAstNode node;

	public AstCall(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.CALL)
			throw new IllegalArgumentException("node must be a CALL node");
	}

	public ProphecyAstNode getMethodNode()
	{
		return node.getChild(METHOD);
	}

	public ProphecyAstNode getArgumentsNode()
	{
		return node.getChild(ARGUMENTS);
	}
}
