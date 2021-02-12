package net.nlacombe.prophecy.ast.nodewrapper;

import net.nlacombe.prophecy.ast.ProphecyAstNode;
import net.nlacombe.prophecy.ast.ProphecyAstNodeType;

import java.util.ArrayList;
import java.util.List;

public class AstMethodDef
{
	private final int MODIFIERLIST_INDEX = 0;
	private final int TYPE_INDEX = 1;
	private final int NAME_INDEX = 2;
	private final int PARAMLIST_INDEX = 3;
	private final int BLOCK_INDEX = 4;

	private ProphecyAstNode node;

	public AstMethodDef(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.METHODDEF)
			throw new IllegalArgumentException("node must be a METHODDEF node");
	}

	public List<String> getModifiers()
	{
		List<String> modifiers = new ArrayList<String>(node.getChild(MODIFIERLIST_INDEX).getChildCount());

		for (ProphecyAstNode child : node.getChild(MODIFIERLIST_INDEX).getChildrenAsArray())
			modifiers.add(child.getText());

		return modifiers;
	}

	public ProphecyAstNode getTypeNode()
	{
		return node.getChild(TYPE_INDEX);
	}

	public ProphecyAstNode getNameNode()
	{
		return node.getChild(NAME_INDEX);
	}

	public ProphecyAstNode getBlockNode()
	{
		return node.getChild(BLOCK_INDEX);
	}

	public ProphecyAstNode[] getParameterNodes()
	{
		ProphecyAstNode paramListNode = node.getChild(PARAMLIST_INDEX);

		return paramListNode.getChildrenAsArray();
	}

	public String getNameText()
	{
		return node.getChild(NAME_INDEX).getText();
	}
}
