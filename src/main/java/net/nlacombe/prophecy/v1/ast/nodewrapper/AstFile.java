package net.nlacombe.prophecy.v1.ast.nodewrapper;

import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNodeType;

public class AstFile
{
	private final int CLASSDEF = 0;

	private ProphecyAstNode node;

	public AstFile(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.FILE)
			throw new IllegalArgumentException("node must be a FILE node");
	}

	public ProphecyAstNode getClassDef()
	{
		return node.getChild(CLASSDEF);
	}
}
