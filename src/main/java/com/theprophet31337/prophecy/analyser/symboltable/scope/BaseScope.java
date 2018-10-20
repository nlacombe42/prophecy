package com.theprophet31337.prophecy.analyser.symboltable.scope;

import com.theprophet31337.prophecy.analyser.symboltable.SymbolSignature;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.Symbol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class BaseScope implements Scope
{
	/**
	 * null if global (outermost) scope.
	 */
	private Scope enclosingScope;
	private Map<SymbolSignature, Symbol> symbols = new LinkedHashMap<SymbolSignature, Symbol>();

	private List<Scope> children;

	public BaseScope(Scope parent)
	{
		this.enclosingScope = parent;

		children = new LinkedList<Scope>();

		if (parent != null)
			parent.addChildScope(this);
	}

	/**
	 * Gets all the symbol defined directly in this scope (does not include symbols in child scopes).
	 */
	public List<Symbol> getSymbols()
	{
		List<Symbol> result = new ArrayList<Symbol>(symbols.size());

		result.addAll(symbols.values());

		return result;
	}

	@Override
	public List<Scope> getChildrenScopes()
	{
		return children;
	}

	@Override
	public void addChildScope(Scope child)
	{
		children.add(child);
	}

	@Override
	public Symbol resolve(SymbolSignature signature)
	{
		Symbol symbol = symbols.get(signature);

		if (symbol != null)
			return symbol;

		// if not here, check any enclosing scope
		if (getParentScope() != null)
			return getParentScope().resolve(signature);

		return null; // not found
	}

	public Symbol define(Symbol symbol)
	{
		Symbol previouslyDefined = symbols.get(symbol.getSignature());

		symbols.put(symbol.getSignature(), symbol);
		symbol.setScope(this); // track the scope in each symbol

		return previouslyDefined;
	}

	public Scope getParentScope()
	{
		return getEnclosingScope();
	}

	public Scope getEnclosingScope()
	{
		return enclosingScope;
	}

	public String toString()
	{
		StringBuilder ret = new StringBuilder();

		ret.append("{");

		int i = 0;

		for (Symbol symbol : symbols.values()) {
			ret.append(symbol.toString());

			if (i != symbols.size() - 1)
				ret.append(", ");

			i++;
		}

		i = 0;
		for (Scope child : children) {
			if (child instanceof Symbol)
				continue;

			if (i == 0)
				ret.append(", ");

			ret.append(child.toString());

			if (i != children.size() - 1)
				ret.append(", ");

			i++;
		}

		ret.append("}");

		return ret.toString();
	}
}