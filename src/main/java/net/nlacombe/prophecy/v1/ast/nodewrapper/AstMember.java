package net.nlacombe.prophecy.v1.ast.nodewrapper;

import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNodeType;

public class AstMember
{
	private final int OBJECT = 0;
	private final int MEMBER = 1;

	private ProphecyAstNode node;

	public AstMember(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.MEMBER)
			throw new IllegalArgumentException("node must be a MEMBER node");
	}

	public ProphecyAstNode getObjectNode()
	{
		return node.getChild(OBJECT);
	}

	public ProphecyAstNode getMemberNode()
	{
		return node.getChild(MEMBER);
	}
}