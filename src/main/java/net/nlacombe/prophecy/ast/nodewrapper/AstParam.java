package net.nlacombe.prophecy.ast.nodewrapper;

import net.nlacombe.prophecy.ast.ProphecyAstNode;
import net.nlacombe.prophecy.ast.ProphecyAstNodeType;

public class AstParam
{
	private final int TYPE_INDEX = 0;
	private final int NAME_INDEX = 1;

	private ProphecyAstNode node;

	public AstParam(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.PARAM)
			throw new IllegalArgumentException("node must be of type PARAM.");
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

	public String getTypeText()
	{
		return node.getChild(TYPE_INDEX).getText();
	}
}
