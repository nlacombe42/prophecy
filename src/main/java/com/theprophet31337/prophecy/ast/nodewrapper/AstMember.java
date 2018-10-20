package com.theprophet31337.prophecy.ast.nodewrapper;

import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstNodeType;

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
