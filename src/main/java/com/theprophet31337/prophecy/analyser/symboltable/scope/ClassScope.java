package com.theprophet31337.prophecy.analyser.symboltable.scope;

import com.theprophet31337.prophecy.analyser.symboltable.SymbolSignature;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.ClassSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.MethodSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.Symbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.VariableSymbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassScope implements Scope
{
	/**
	 * Static ClassScope when this scope is an instance scope
	 * or GlobalScope when this scope is the static ClassScope.
	 */
	private Scope enclosingScope;

	/**
	 * Children scopes: method scopes.
	 */
	private List<Scope> children;

	private ClassSymbol superClass;

	/**
	 * True if this is a static ClassScope.
	 */
	private boolean isStatic;

	/**
	 * List of all fields and methods
	 */
	private Map<SymbolSignature, Symbol> members;

	/**
	 * List of methods grouped by identifier (name).
	 */
	private Map<String, Set<MethodSymbol>> methods;

	public ClassScope(Scope enclosingScope, ClassSymbol superClass, boolean isStatic)
	{
		members = new LinkedHashMap<SymbolSignature, Symbol>();
		methods = new HashMap<String, Set<MethodSymbol>>();
		children = new LinkedList<Scope>();

		this.superClass = superClass;
		this.enclosingScope = enclosingScope;
		this.isStatic = isStatic;
	}

	public boolean isStatic()
	{
		return isStatic;
	}

	public ClassSymbol getSuperClass()
	{
		return superClass;
	}

	public void setSuperClass(ClassSymbol superClass)
	{
		this.superClass = superClass;
	}

	/**
	 * Gets all fields and methods.
	 */
	public List<Symbol> getMembers()
	{
		List<Symbol> result = new ArrayList<Symbol>(members.size());

		result.addAll(members.values());

		return result;
	}

	/**
	 * Gets all methods.
	 */
	public List<MethodSymbol> getMethods()
	{
		List<MethodSymbol> result = new ArrayList<MethodSymbol>(methods.size());

		for (Set<MethodSymbol> methodset : methods.values())
			result.addAll(methodset);

		return result;
	}

	/**
	 * Gets all the super classes in the super class chain.
	 */
	public List<ClassSymbol> getSuperClasses()
	{
		if (superClass == null)
			return new ArrayList<ClassSymbol>(0);

		List<ClassSymbol> superClasses = superClass.getSuperClasses();
		superClasses.add(superClass);

		return superClasses;
	}

	/**
	 * If this is an instance ClassScope it returns the static ClassScope,
	 * if this is a static ClassScope it returns the super class of this ClassScope (if there is one),
	 * if this is a static ClassScope and there is no super class this returns
	 * the enclosing scope (GlobalScope).
	 */
	@Override
	public Scope getParentScope()
	{
		if (isStatic) {
			//If no super class return enclosing scope (GlobalScope).
			if (superClass == null)
				return getEnclosingScope();

			//If there is a super class return the super class' (instance) scope.
			return superClass;
		} else {
			//Return static ClassScope (which is the enclosingScope).
			return enclosingScope;
		}
	}

	/**
	 * Try to get the member in this class' scope,
	 * if the member is not present go look in the super class scope
	 * and repeat steps until the member is found or there is no more super class scopes.
	 */
	public Symbol resolveMember(SymbolSignature signature)
	{
		Symbol symbol = members.get(signature);

		if (symbol != null)
			return symbol;

		if (superClass != null) {
			return superClass.resolveMember(signature);
		}

		return null;
	}

	/**
	 * Gets a set of the methods that have the specified name but different
	 * method signatures in this class specifically (not in the super class chain).
	 */
	public Set<MethodSymbol> getMethods(String name)
	{
		Set<MethodSymbol> methodset = methods.get(name);

		if (methodset != null)
			return methodset;
		else
			return new HashSet<MethodSymbol>();
	}

	/**
	 * Gets a set of the methods that have the specified name but different
	 * method signatures, searches in the super class chain.
	 * <p>
	 * This will get all methods with the specified name in this class and then
	 * will search in the super class chain in subclass to superclass order.
	 * In the case where there is multiple methods with the same method signature but
	 * in different classes, only the subclass' version will be included in the list.
	 */
	public Set<MethodSymbol> resolveMethods(String name)
	{
		Set<MethodSymbol> resultset = new HashSet<MethodSymbol>();

		resultset.addAll(getMethods(name));

		if (superClass != null) {
			Set<MethodSymbol> superClassesSet = superClass.resolveMethods(name);

			for (MethodSymbol methodToAdd : superClassesSet) {
				boolean alreadyPresent = false;

				loop:
				for (MethodSymbol method : resultset) {
					if (method.getSignature().equals(methodToAdd.getSignature())) {
						alreadyPresent = true;
						break loop;
					}
				}

				if (!alreadyPresent)
					resultset.add(methodToAdd);
			}
		}

		return resultset;
	}

	public void putMember(Symbol symbol)
	{
		/* We need to keep track of methods that have the same name.
		 * For this reason we have 2 different "put" methods.
		 */

		if (symbol instanceof MethodSymbol)
			putMethod((MethodSymbol) symbol);
		else
			putField(symbol);
	}

	private void putField(Symbol symbol)
	{
		members.put(symbol.getSignature(), symbol);
	}

	/**
	 * Adds the method to the instanceMembers and to the methods maps.
	 */
	private void putMethod(MethodSymbol method)
	{
		members.put(method.getSignature(), method);

		Set<MethodSymbol> methodSet = methods.get(method.getName());

		if (methodSet == null) {
			methodSet = new HashSet<MethodSymbol>();
		}

		putMethodToSet(methodSet, method);

		methods.put(method.getName(), methodSet);
	}

	private void putMethodToSet(Set<MethodSymbol> set, MethodSymbol method)
	{
		MethodSymbol methodToRemove = null;

		for (MethodSymbol methodSym : set)
			if (methodSym.getSignature().equals(method.getSignature()))
				methodToRemove = methodSym;

		if (methodToRemove != null)
			set.remove(methodToRemove);

		set.add(method);
	}

	/**
	 * Returns true if this class contains at least 1 method with the specified
	 * name.
	 */
	public boolean containsMethod(String name)
	{
		return methods.get(name) != null && methods.get(name).size() != 0;
	}

	@Override
	public String getScopeName()
	{
		return "class";
	}

	@Override
	public Scope getEnclosingScope()
	{
		return enclosingScope;
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
	public Symbol define(Symbol symbol)
	{
		Symbol previouslyDefined = getMember(symbol.getSignature());

		putMember(symbol);

		//track the scope in each symbol
		symbol.setScope(this);

		/* If the symbol is a method then set its enclosing scope.
		 *
		 * Setting the enclosing scope of a method is delayed to post instantiation since
		 * we only know if the enclosing scope is a static ClassScope or instance ClassScope
		 * when we define the method in its scope.
		 */

		if (symbol instanceof MethodSymbol) {
			MethodSymbol method = (MethodSymbol) symbol;
			method.setEnclosingScope(this);
		}

		if (isStatic) {
			//Mark static members as static.

			if (symbol instanceof VariableSymbol) {
				VariableSymbol field = (VariableSymbol) symbol;
				field.setStatic(true);
			} else if (symbol instanceof MethodSymbol) {
				MethodSymbol method = (MethodSymbol) symbol;
				method.setStatic(true);
			}
		}

		return previouslyDefined;
	}

	@Override
	public Symbol resolve(SymbolSignature signature)
	{
		Symbol symbol = getMember(signature);

		if (symbol != null)
			return symbol;

		if (getParentScope() != null)
			return getParentScope().resolve(signature);

		return null;
	}

	public Symbol getMember(SymbolSignature signature)
	{
		return members.get(signature);
	}

	@Override
	public String toString()
	{
		return toString(true);
	}

	public String toString(boolean withBrackets)
	{
		StringBuilder ret = new StringBuilder();

		if (withBrackets)
			ret.append("{ ");

		boolean first = true;

		for (Symbol symbol : members.values()) {
			if (first)
				first = false;
			else
				ret.append(", ");

			ret.append(symbol.toString());
		}

		if (withBrackets)
			ret.append(" }");

		return ret.toString();
	}

	public boolean containsMember(Symbol symbol)
	{
		return members.containsKey(symbol.getSignature());
	}
}
