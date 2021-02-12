package net.nlacombe.prophecy.analyser.symboltable;

/**
 * An array signature.
 * <p>
 * This is no different than a symbol signature except the symbol name is the
 * array element type and this signature is only equal to other ArraySignature (and not plain SymbolSignature).
 */
public class ArraySignature extends SymbolSignature
{
	public ArraySignature(String elementName)
	{
		super(elementName);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (!(obj instanceof ArraySignature))
			return false;

		return super.equals(obj);
	}
}
