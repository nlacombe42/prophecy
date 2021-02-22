package net.nlacombe.prophecy.shared.symboltable.domain.symbol;

import net.nlacombe.prophecy.shared.symboltable.domain.Type;

import java.util.List;

public class BuiltInTypeSymbol extends Symbol implements Type
{
	public static final BuiltInTypeSymbol tVoid = new BuiltInTypeSymbol("void");
	public static final BuiltInTypeSymbol tBool = new BuiltInTypeSymbol("bool");
	public static final BuiltInTypeSymbol tChar = new BuiltInTypeSymbol("char");
	public static final BuiltInTypeSymbol tInt = new BuiltInTypeSymbol("int");
	public static final BuiltInTypeSymbol tFloat = new BuiltInTypeSymbol("float");

	public static final List<BuiltInTypeSymbol> BUILT_IN_TYPES = List.of(tVoid, tBool, tChar, tInt, tFloat);

	public BuiltInTypeSymbol(String name)
	{
		super(name);
	}

	/**
	 * Returns true if <code>type</code> is a char, int or float.
	 */
	public static boolean isNumeric(Type type)
	{
		return type.equals(tChar) || type.equals(tInt) || type.equals(tFloat);
	}

	/**
	 * Returns true if <code>type</code> is a char or int.
	 */
	public static boolean isIntegerType(Type type)
	{
		return type.equals(tChar) || type.equals(tInt);
	}

	/**
	 * In the case of numeric types, returns the number of
	 * conversions necessary to convert from this numeric type to <code>type</code>.
	 * <p>
	 * In the case this type is a <code>char</code> and <code>type</code> is <code>float</code>
	 * this method return 2.
	 */
	public int numberOfConversions(BuiltInTypeSymbol type)
	{
		if (!isNumeric(this) || !isNumeric(type) || !canAssignTo(type))
			throw new IllegalArgumentException();

		if (this.equals(type))
			return 0;

		if (this.equals(tChar) && type.equals(tFloat))
			return 2;

		return 1;
	}

	@Override
	public boolean canAssignTo(Type type)
	{
		if (this.equals(type))
			return true;

		if (!isNumeric(this) || !isNumeric(type))
			return false;

		if (type.equals(tFloat))
			return true;

		if (type.equals(tInt) && this.equals(tChar))
			return true;

		return false;
	}
}
