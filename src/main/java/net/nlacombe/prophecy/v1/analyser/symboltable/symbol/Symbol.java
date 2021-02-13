package net.nlacombe.prophecy.v1.analyser.symboltable.symbol;

import net.nlacombe.prophecy.v1.analyser.symboltable.SymbolSignature;
import net.nlacombe.prophecy.v1.analyser.symboltable.Type;
import net.nlacombe.prophecy.v1.analyser.symboltable.scope.Scope;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;

public class Symbol
{
	private String name;
	private Type type;
	private Scope scope;
	private ProphecyAstNode definition;

	public Symbol(String name)
	{
		this.name = name;
	}

	public Symbol(String name, Type type)
	{
		this(name);
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public void setScope(Scope scope)
	{
		this.scope = scope;
	}

	public Scope getScope()
	{
		return scope;
	}

	public void setDefinition(ProphecyAstNode definition)
	{
		this.definition = definition;
	}

	public ProphecyAstNode getDefinition()
	{
		return definition;
	}

	public SymbolSignature getSignature()
	{
		return new SymbolSignature(name);
	}

	public String toString()
	{
		String s = "";

//		if(scope!=null)
//			s=scope.getScopeName()+".";

		if (type != null)
			return '<' + s + getName() + ":" + type.getName() + '>';

		return s + getName();
	}

	public static String stripBrackets(String s)
	{
		return s.substring(1, s.length() - 1);
	}
}
