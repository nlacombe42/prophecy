package net.nlacombe.prophecy.shared.symboltable.domain.scope;

import net.nlacombe.prophecy.shared.symboltable.domain.SymbolSignature;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.Symbol;

import java.util.List;

public interface Scope
{
	public String getScopeName();

	/**
	 * Where to look next for symbols; superclass or enclosing scope
	 */
	public Scope getParentScope();

	/**
	 * Scope in which this scope defined. For global scope, it's null
	 */
	public Scope getEnclosingScope();

	public List<Scope> getChildrenScopes();

	public void addChildScope(Scope child);

	/**
	 * Define the specified symbol in this scope.
	 * <p>
	 * If a symbol with the same identifier/signature already exists
	 * in this scope, it will be replaced and the replaced symbol will be returned.
	 *
	 * @return If a symbol with the same name was previously defined,
	 * it returns the previously defined symbol. Otherwise,
	 * it returns null.
	 */
	public Symbol define(Symbol symbol);

	/**
	 * Look up signature in this scope or in parent scope if not here.
	 *
	 * @return the resolved Symbol or null if the SymbolSignature cannot be resolved.
	 */
	public Symbol resolve(SymbolSignature signature);
}
