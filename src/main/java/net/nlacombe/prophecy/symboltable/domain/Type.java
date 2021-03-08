package net.nlacombe.prophecy.symboltable.domain;

public interface Type
{
	/**
	 * The symbol name of this type.
	 */
	public String getName();

	/**
	 * Returns true if this type can be assigned to <code>type</code> with nothing more than
	 * an implicit conversion.
	 * <p>
	 * If a cast is needed or the conversion to <code>type</code> is prohibited,
	 * this method will return false.
	 */
	public boolean canAssignTo(Type type);
}
