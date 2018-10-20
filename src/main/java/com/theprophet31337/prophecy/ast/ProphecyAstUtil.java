package com.theprophet31337.prophecy.ast;

public class ProphecyAstUtil
{
	/**
	 * Returns true if the identifier node whose parent is <code>parentNode</code>
	 * is part of a definition (as opposed to just a reference).
	 */
	public static boolean isDefinition(ProphecyAstNode parentNode)
	{
		return parentNode.getType() == ProphecyAstNodeType.CLASSDEF || parentNode.getType() == ProphecyAstNodeType.METHODDEF
				|| parentNode.getType() == ProphecyAstNodeType.VARDECL || parentNode.getType() == ProphecyAstNodeType.FIELDDEF
				|| parentNode.getType() == ProphecyAstNodeType.PARAM;
	}
}
