package net.nlacombe.prophecy.v1.analyser.symboltable.symbol;

import net.nlacombe.prophecy.v1.analyser.symboltable.SymbolSignature;
import net.nlacombe.prophecy.v1.analyser.symboltable.Type;
import net.nlacombe.prophecy.v1.analyser.symboltable.scope.Scope;

import java.util.LinkedList;
import java.util.List;

public abstract class ScopedSymbol extends Symbol implements Scope
{
	private Scope enclosingScope;
	private List<Scope> children;

	public ScopedSymbol(String name, Type type)
	{
		super(name, type);

		init();
	}

	public ScopedSymbol(String name)
	{
		super(name);

		init();
	}

	private void init()
	{
		children = new LinkedList<Scope>();
	}

	public void setEnclosingScope(Scope enclosingScope)
	{
		this.enclosingScope = enclosingScope;

		enclosingScope.addChildScope(this);
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

	/**
	 * Resolves an identifier residing in this ScopedSymbol scope.
	 *
	 * @return null if identifier not resolved.
	 */
	@Override
	public Symbol resolve(SymbolSignature signature)
	{
		Symbol s = getMember(signature);

		if (s != null)
			return s;

		// if not here, check any parent scope
		if (getParentScope() != null) {
			return getParentScope().resolve(signature);
		}

		return null; // not found
	}

	@Override
	public Symbol define(Symbol symbol)
	{
		Symbol previouslyDefined = getMember(symbol.getSignature());

		putMember(symbol);

		symbol.setScope(this); // track the scope in each symbol

		return previouslyDefined;
	}

	@Override
	public Scope getParentScope()
	{
		return enclosingScope;
	}

	@Override
	public Scope getEnclosingScope()
	{
		return enclosingScope;
	}

	@Override
	public String getScopeName()
	{
		return getName();
	}

	/**
	 * Returns the symbol with the corresponding SymbolSignature or null if there is no symbol
	 * with that SymbolSignature.
	 */
	public abstract Symbol getMember(SymbolSignature signature);

	public abstract void putMember(Symbol symbol);

	public abstract boolean containsMember(Symbol symbol);
}
