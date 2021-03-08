package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.SymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MethodSymbol extends ScopedSymbol
{
	private Map<SymbolSignature, Symbol> orderedParameters = new LinkedHashMap<>();

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

    public MethodSymbol(String name, Type retType, ClassSymbol parent, boolean autoLocalScope) {
        this(name, retType, parent);

        if (autoLocalScope)
            addChildScope(new LocalScope(this));
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
			    throw new ProphecyCompilerException("unimplemented");
//				AstParam param = new AstParam(parameter.getDefinition());
//
//				signature.addParameter(param.getTypeText());
			}
		}

		return signature;
	}

	public String toString()
	{
		StringBuilder ret = new StringBuilder();

		Type retType = getType();

		if (retType != null)
			ret.append("<").append(((Symbol) retType).getName()).append(">");

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

        var localScope = (LocalScope) getChildrenScopes().get(0);

		if (localScope != null && localScope.getSymbols() != null && !localScope.getSymbols().isEmpty()) {
            ret.append("\n{\n").append(localScope.toString().indent(4)).append("}\n");
        } else {
		    ret.append(" {}");
        }

		return ret.toString();
	}

	public List<Symbol> getParameters()
	{
		List<Symbol> parameters = new ArrayList<>(orderedParameters.size());

        parameters.addAll(orderedParameters.values());

		return parameters;
	}

	public Symbol getParameter(int index) {
	    return getParameters().get(index);
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
