package net.nlacombe.prophecy.ast.nodewrapper;

import net.nlacombe.prophecy.ast.ProphecyAstNode;
import net.nlacombe.prophecy.ast.ProphecyAstNodeType;

public class AstClassDef
{
	private final int NAME_INDEX = 0;
	private final int SUPER_INDEX = 1;
	private final int DEFBLOCK_INDEX = 2;

	private ProphecyAstNode node;

	public AstClassDef(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.CLASSDEF)
			throw new IllegalArgumentException("node must be a CLASSDEF node");
	}

	public ProphecyAstNode getNameNode()
	{
		return node.getChild(NAME_INDEX);
	}

	public ProphecyAstNode getSuperclassNode()
	{
		return node.getChild(SUPER_INDEX);
	}

	public ProphecyAstNode getDefBlockNode()
	{
		return node.getChild(DEFBLOCK_INDEX);
	}

	public String getNameText()
	{
		return node.getChild(NAME_INDEX).getText();
	}

	public String getSuperclassNameText()
	{
		return node.getChild(SUPER_INDEX).getText();
	}
}
