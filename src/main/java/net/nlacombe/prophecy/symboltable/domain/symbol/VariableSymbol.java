package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.symboltable.domain.Type;

public class VariableSymbol extends Symbol
{
	/**
	 * If this symbol is a member of a class and is static.
	 */
	private boolean isStatic;

	public VariableSymbol(String name, Type type)
	{
		super(name, type);

		isStatic = false;
	}

	/**
	 * True if this symbol is a member of a class and is static.
	 */
	public boolean isStatic()
	{
		return isStatic;
	}

	public void setStatic(boolean isStatic)
	{
		this.isStatic = isStatic;
	}
}
