package net.nlacombe.prophecy.analyser.symboltable;

/**
 * A symbol's signature.
 * <p>
 * This represents the information necessary to uniquely identify a symbol.
 * <p>
 * This class is subclassed for other types of symbols that have other criteria
 * used to uniquely identify them (eg.: method signatures needs parameter types).
 */
public class SymbolSignature
{
	private String name;

	public SymbolSignature(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (!(obj instanceof SymbolSignature))
			return false;

		SymbolSignature signature = (SymbolSignature) obj;

		return name.equals(signature.getName());
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}
