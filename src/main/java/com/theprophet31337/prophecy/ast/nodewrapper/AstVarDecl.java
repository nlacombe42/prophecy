package com.theprophet31337.prophecy.ast.nodewrapper;

import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstNodeType;

public class AstVarDecl
{
	private final int TYPE_INDEX = 0;
	private final int NAME_INDEX = 1;
	private final int INIT_INDEX = 2;

	private ProphecyAstNode node;

	public AstVarDecl(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.VARDECL)
			throw new IllegalArgumentException("node must be a VARDECL node");
	}

	public ProphecyAstNode getTypeNode()
	{
		return node.getChild(TYPE_INDEX);
	}

	public ProphecyAstNode getNameNode()
	{
		return node.getChild(NAME_INDEX);
	}

	public String getNameText()
	{
		return node.getChild(NAME_INDEX).getText();
	}

	public ProphecyAstNode getInitNode()
	{
		return node.getChild(INIT_INDEX);
	}
}
