package net.nlacombe.prophecy.v1.generator;

import net.nlacombe.prophecy.v1.analyser.symboltable.Type;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.MethodSymbol;

public interface GeneratorTargetSpecifics
{
	/**
	 * Calls getBuiltInTypeName(BuiltInTypeSymbol type) or getIdentifierName(String sourceName)
	 * depending on if <code>type</code> is a BuiltInTypeSymbol or not.
	 */
	public String getTypeName(Type type);

	public String getMethodName(MethodSymbol method);

	public String getBuiltInTypeName(BuiltInTypeSymbol type);

	public String getLiteralValue(BuiltInTypeSymbol type, String literalValueText);

	/**
	 * Provided a Prophecy identifier, generate a valid and unique target identifier.
	 * <p>
	 * Given 2 different <code>sourceName</code> this method must return 2 different results.
	 * This method must return the same target identifier if provided the same <code>sourceName</code>.
	 */
	public String getIdentifierName(String sourceName);

	/**
	 * Provided 2 Prophecy identifiers, generate a valid and unique target identifier.
	 * <p>
	 * Given 2 different set of inputs (the order of the parts counts as a different input)
	 * this method must return 2 different results.
	 * This method must return the same target identifier if provided the same input.
	 * This method must not return a target identifier that can be returned by this class'
	 * getTargetName(String sourceName) method.
	 */
	public String getIdentifierName(String... nameParts);

	/**
	 * Gets the target text for a pointer pointing to Prophecy identifier <code>sourceName</code>.
	 */
	public String getPointerTypeName(String sourceName);

	public String getPointerTypeName(String... nameParts);

	public String getMainMethodName();
}
