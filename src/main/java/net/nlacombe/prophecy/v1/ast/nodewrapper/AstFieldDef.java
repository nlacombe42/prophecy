package net.nlacombe.prophecy.v1.ast.nodewrapper;

import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNodeType;

import java.util.ArrayList;
import java.util.List;

public class AstFieldDef
{
	private final int MODIFIERLIST_INDEX = 0;
	private final int TYPE_INDEX = 1;
	private final int NAME_INDEX = 2;

	private ProphecyAstNode node;

	public AstFieldDef(ProphecyAstNode node)
	{
		this.node = node;

		if (node.getType() != ProphecyAstNodeType.FIELDDEF)
			throw new IllegalArgumentException("node must be a FIELDDEF node");
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

	public String getNameText()
	{
		return node.getChild(NAME_INDEX).getText();
	}
}
