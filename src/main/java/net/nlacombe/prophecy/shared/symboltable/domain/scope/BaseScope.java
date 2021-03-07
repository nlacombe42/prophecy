package net.nlacombe.prophecy.shared.symboltable.domain.scope;

import net.nlacombe.prophecy.shared.symboltable.domain.SymbolSignature;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.Symbol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	    if (children.contains(child))
	        return;

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
	    if (symbols.isEmpty() && children.isEmpty())
	        return  "";

        var symbolsText = symbols.values().stream()
            .map(Symbol::toString)
            .collect(Collectors.joining("\n"));

        var scopesText = children.stream()
            .filter(scope -> !(scope instanceof Symbol))
            .map(Scope::toString)
            .collect(Collectors.joining(""))
            .indent(4);

        var text = "";

        if (!symbols.isEmpty())
            text += symbolsText +  "\n";

        if (!scopesText.isBlank())
            text += "{\n" + scopesText + "}\n";

        return text;
	}
}
