package net.nlacombe.prophecy.analyser.symboltable.symbol;

import net.nlacombe.prophecy.analyser.symboltable.MethodSignature;
import net.nlacombe.prophecy.analyser.symboltable.SymbolSignature;
import net.nlacombe.prophecy.analyser.symboltable.Type;
import net.nlacombe.prophecy.analyser.symboltable.scope.Scope;
import net.nlacombe.prophecy.ast.nodewrapper.AstParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MethodSymbol extends ScopedSymbol
{
	private Map<SymbolSignature, Symbol> orderedParameters = new LinkedHashMap<SymbolSignature, Symbol>();

	/**
	 * Class this method is defined in.
	 */
	private ClassSymbol parentClass;

	private boolean isStatic;

	public MethodSymbol(String name, Type retType, ClassSymbol parent)
	{
		super(name, retType);

		parentClass = parent;

		isStatic = false;
	}

	public MethodSymbol(String name, Type retType, ClassSymbol parent, Scope child)
	{
		this(name, retType, parent);

		addChildScope(child);
	}


	@Override
	public Scope getEnclosingScope()
	{
		return parentClass;
	}

	/**
	 * Gets the class this method is defined in.
	 */
	public ClassSymbol getParentClass()
	{
		return parentClass;
	}

	/**
	 * Returns the method's identifier (name).
	 */
	public String getName()
	{
		return super.getName();
	}

	/**
	 * Returns method signature.
	 */
	@Override
	public MethodSignature getSignature()
	{
		MethodSignature signature = new MethodSignature(super.getName());

		for (Symbol parameter : orderedParameters.values()) {
			/* If parameter type has been resolved add the type to the signature
			 * otherwise add the parameter type name.
			 */

			if (parameter.getType() != null)
				signature.addParameter(parameter.getType());
			else {
				AstParam param = new AstParam(parameter.getDefinition());

				signature.addParameter(param.getTypeText());
			}
		}

		return signature;
	}

	public String toString()
	{
		StringBuilder ret = new StringBuilder();

		Type retType = getType();

		if (retType != null) {
			ret.append("<" + retType.toString() + ">");
		}

		ret.append(super.getName());

		ret.append("(");

		int i = 0;
		for (Symbol arg : orderedParameters.values()) {
			ret.append(arg.toString());

			if (i != orderedParameters.size() - 1)
				ret.append(", ");

			i++;
		}

		ret.append(")");
		ret.append(getChildrenScopes().get(0).toString());

		return ret.toString();
	}

	public List<Symbol> getParameters()
	{
		List<Symbol> parameters = new ArrayList<Symbol>(orderedParameters.size());

		for (Symbol symbol : orderedParameters.values())
			parameters.add(symbol);

		return parameters;
	}

	@Override
	public Symbol getMember(SymbolSignature signature)
	{
		return orderedParameters.get(signature);
	}

	@Override
	public void putMember(Symbol symbol)
	{
		orderedParameters.put(symbol.getSignature(), symbol);
	}

	@Override
	public boolean containsMember(Symbol symbol)
	{
		return orderedParameters.containsKey(symbol.getSignature());
	}

	public boolean isStatic()
	{
		return isStatic;
	}

	public void setStatic(boolean isStatic)
	{
		this.isStatic = isStatic;
	}
}
