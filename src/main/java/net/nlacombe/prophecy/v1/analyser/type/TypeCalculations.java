package net.nlacombe.prophecy.v1.analyser.type;

import net.nlacombe.prophecy.shared.symboltable.domain.Type;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;

public class TypeCalculations
{
	/**
	 * Determine if the <code>from</code> Type needs to be promoted, and to what Type
	 * it needs to be promoted, in order to be of the same type as the type <code>to</code>.
	 * <p>
	 * Notes: this does NOT determine if the types are compatible for any given operation.
	 *
	 * @return Returns null if no promotion is required.
	 */
	public static Type promoteToType(Type from, Type to)
	{
		if (from.equals(to))
			return null;

		if (!(from instanceof BuiltInTypeSymbol) || !(to instanceof BuiltInTypeSymbol))
			return null;

		BuiltInTypeSymbol builtInFrom = (BuiltInTypeSymbol) from;
		BuiltInTypeSymbol builtInTo = (BuiltInTypeSymbol) to;

		if (!BuiltInTypeSymbol.isNumeric(builtInFrom) || !BuiltInTypeSymbol.isNumeric(builtInTo))
			return null;

		if (to.equals(BuiltInTypeSymbol.tFloat) && (from.equals(BuiltInTypeSymbol.tInt) || from.equals(BuiltInTypeSymbol.tChar)))
			return BuiltInTypeSymbol.tFloat;
		else if (to.equals(BuiltInTypeSymbol.tInt) && from.equals(BuiltInTypeSymbol.tChar))
			return BuiltInTypeSymbol.tInt;
		else
			return null;
	}

	/**
	 * Returns the Type of the result for any arithmetic operations with the provided operands types.
	 *
	 * @return Returns Type void if operand types are not compatible or are invalid for an arithmetic operation.
	 */
	public static Type getArithmeticResultType(Type lhs, Type rhs)
	{
		if (!(lhs instanceof BuiltInTypeSymbol) || !(rhs instanceof BuiltInTypeSymbol))
			return BuiltInTypeSymbol.tVoid;

		BuiltInTypeSymbol builtInLhs = (BuiltInTypeSymbol) lhs;
		BuiltInTypeSymbol builtInRhs = (BuiltInTypeSymbol) rhs;

		if (!BuiltInTypeSymbol.isNumeric(builtInLhs) || !BuiltInTypeSymbol.isNumeric(builtInRhs))
			return BuiltInTypeSymbol.tVoid;

		if (builtInLhs.equals(builtInRhs))
			return builtInLhs;

		if (builtInLhs.equals(BuiltInTypeSymbol.tFloat) || builtInRhs.equals(BuiltInTypeSymbol.tFloat))
			return BuiltInTypeSymbol.tFloat;
		else if (builtInLhs.equals(BuiltInTypeSymbol.tInt) || builtInRhs.equals(BuiltInTypeSymbol.tInt))
			return BuiltInTypeSymbol.tInt;
		else
			return BuiltInTypeSymbol.tChar;
	}

	/**
	 * Returns the Type of the result for any relational operations with the provided operands types.
	 *
	 * @return Returns Type void if operand types are not compatible or are invalid for a relational operation.
	 */
	public static Type getRelationalResultType(Type lhs, Type rhs)
	{
		if (!(lhs instanceof BuiltInTypeSymbol) || !(rhs instanceof BuiltInTypeSymbol))
			return BuiltInTypeSymbol.tVoid;

		BuiltInTypeSymbol builtInLhs = (BuiltInTypeSymbol) lhs;
		BuiltInTypeSymbol builtInRhs = (BuiltInTypeSymbol) rhs;

		if (!BuiltInTypeSymbol.isNumeric(builtInLhs) || !BuiltInTypeSymbol.isNumeric(builtInRhs))
			return BuiltInTypeSymbol.tVoid;

		return BuiltInTypeSymbol.tBool;
	}

	/**
	 * Returns the Type of the result for any equality operations with the provided operands types.
	 *
	 * @return Returns Type void if operand types are not compatible or are invalid for a equality operation.
	 */
	public static Type getEqualityResultType(Type lhs, Type rhs)
	{
		if (!(lhs instanceof BuiltInTypeSymbol) || !(rhs instanceof BuiltInTypeSymbol))
			return BuiltInTypeSymbol.tVoid;

		BuiltInTypeSymbol builtInLhs = (BuiltInTypeSymbol) lhs;
		BuiltInTypeSymbol builtInRhs = (BuiltInTypeSymbol) rhs;

		if (BuiltInTypeSymbol.isNumeric(builtInLhs) && BuiltInTypeSymbol.isNumeric(builtInRhs))
			return BuiltInTypeSymbol.tBool;
		else if (builtInLhs.equals(BuiltInTypeSymbol.tBool) && builtInRhs.equals(BuiltInTypeSymbol.tBool))
			return BuiltInTypeSymbol.tBool;
		else
			return BuiltInTypeSymbol.tVoid;
	}
}
