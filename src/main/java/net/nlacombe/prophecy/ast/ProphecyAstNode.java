package net.nlacombe.prophecy.ast;

import net.nlacombe.prophecy.analyser.symboltable.Type;
import net.nlacombe.prophecy.analyser.symboltable.scope.Scope;
import net.nlacombe.prophecy.analyser.symboltable.symbol.Symbol;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProphecyAstNode
{
	private int lineNumber;
	private int column;

	private String text;
	private ProphecyAstNodeType type;

	private Scope scope;
	private Symbol symbol;
	private Type promoteToType;
	private Type evalType;

	private ProphecyAstNode parent;
	private Map<Integer, ProphecyAstNode> children;

	public ProphecyAstNode()
	{
		lineNumber = -1;
		column = -1;
	}

	/**
	 * @param capacity The initial capacity for the children container (hashmap).
	 */
	public ProphecyAstNode(int capacity)
	{
		children = new HashMap<Integer, ProphecyAstNode>(capacity);
	}

	public ProphecyAstNode(ProphecyAstNodeType type, String text, int lineNumber, int column)
	{
		this.type = type;
		this.text = text;
		this.lineNumber = lineNumber;
		this.column = column;
	}

	public ProphecyAstNode(ProphecyAstNodeType type, int lineNumber, int column)
	{
		this(type, type.name(), lineNumber, column);
	}

	public ProphecyAstNode(ProphecyAstNodeType type, String text)
	{
		this(type, text, -1, -1);
	}

	public ProphecyAstNode(ProphecyAstNodeType type)
	{
		this(type, type.name());
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public ProphecyAstNodeType getType()
	{
		return type;
	}

	public void setType(ProphecyAstNodeType type)
	{
		this.type = type;
	}

	public void setTypeAndText(ProphecyAstNodeType type)
	{
		this.type = type;
		this.text = type.name();
	}

	public ProphecyAstNode getParent()
	{
		return parent;
	}

	protected void setParent(ProphecyAstNode parent)
	{
		this.parent = parent;
	}

	public void addChild(ProphecyAstNode child)
	{
		if (children == null) {
			children = new HashMap<Integer, ProphecyAstNode>();
		}

		children.put(children.size(), child);

		child.setParent(this);
	}

	public ProphecyAstNode getChild(int index)
	{
		if (children == null)
			return null;

		return children.get(index);
	}

	public ProphecyAstNode[] getChildrenAsArray()
	{
		if (children == null)
			return new ProphecyAstNode[0];

		ProphecyAstNode[] childArray = new ProphecyAstNode[children.size()];

		for (int i = 0; i < childArray.length; i++)
			childArray[i] = children.get(i);

		return childArray;
	}

	public String getChildText(int index)
	{
		return getChild(index).getText();
	}

	public int getChildCount()
	{
		if (children == null)
			return 0;

		return children.size();
	}

	@Override
	public String toString()
	{
		return toStringTree();
	}

	/**
	 * Prints a tree node and its children in a tabular manner.
	 */
	public String toStringTree()
	{
		return toStringTree(0);
	}

	/**
	 * Prints a tree node and its children in a tabular manner.
	 */
	public String toStringTree(int tabNumber)
	{
		return toStringTree(tabNumber, false);
	}

	/**
	 * Prints a tree node and its children in a tabular manner.
	 */
	public String toStringTree(int tabNumber, boolean forceNewLine)
	{
		StringBuilder ret = new StringBuilder(tabNumber + 1 + getText().length());

		if (getChildCount() == 0) {
			if (forceNewLine) {
				ret.append("\r\n");
				ret.append(StringUtils.repeat("\t", tabNumber));
			}

			ret.append(getText() + "<" + getType().name() + ">");

			ret.append(getAttributeString());

			return ret.toString();
		}

		if (tabNumber != 0)
			ret.append("\r\n");

		ret.append(StringUtils.repeat("\t", tabNumber));
		ret.append("(");
		ret.append(getText() + getAttributeString() + " ");

		tabNumber++;

		ProphecyAstNode child;
		boolean hasChildTree = false;

		for (int i = 0; i < getChildCount(); i++) {
			child = getChild(i);

			if (child.getChildCount() > 0)
				hasChildTree = true;

			ret.append(child.toStringTree(tabNumber, hasChildTree));

			if (!hasChildTree && i != getChildCount() - 1)
				ret.append(" ");
		}

		if (hasChildTree) {
			ret.append("\r\n");
			ret.append(StringUtils.repeat("\t", tabNumber - 1));
		}

		ret.append(")");

		return ret.toString();
	}

	/**
	 * Returns the string representation of this node's attributes for display.
	 */
	private String getAttributeString()
	{
		List<String> attrs = new LinkedList<String>();

		if (evalType != null)
			attrs.add("evalType: " + evalType.getName());

		if (promoteToType != null)
			attrs.add("promoteToType: " + promoteToType);

		StringBuilder ret = new StringBuilder();

		if (attrs.size() > 0) {
			ret.append("[");

			for (int i = 0; i < attrs.size(); i++) {
				if (i != 0)
					ret.append(", ");

				ret.append(attrs.get(i));
			}

			ret.append("]");
		}

		return ret.toString();
	}

	public Scope getScope()
	{
		return scope;
	}

	public void setScope(Scope scope)
	{
		this.scope = scope;
	}

	public Symbol getSymbol()
	{
		return symbol;
	}

	public void setSymbol(Symbol symbol)
	{
		this.symbol = symbol;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}

	public int getColumn()
	{
		return column;
	}

	public void setcolumn(int column)
	{
		this.column = column;
	}

	public Type getPromoteToType()
	{
		return promoteToType;
	}

	public void setPromoteToType(Type promoteToType)
	{
		this.promoteToType = promoteToType;
	}

	public Type getEvalType()
	{
		return evalType;
	}

	public void setEvalType(Type evalType)
	{
		this.evalType = evalType;
	}
}
