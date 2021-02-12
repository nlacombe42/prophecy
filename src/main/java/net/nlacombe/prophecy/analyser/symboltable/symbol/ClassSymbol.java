package net.nlacombe.prophecy.analyser.symboltable.symbol;

import net.nlacombe.prophecy.analyser.symboltable.SymbolSignature;
import net.nlacombe.prophecy.analyser.symboltable.Type;
import net.nlacombe.prophecy.analyser.symboltable.scope.ClassScope;
import net.nlacombe.prophecy.analyser.symboltable.scope.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassSymbol extends Symbol implements Scope, Type
{
	/**
	 * This is the superclass not enclosingScope field. We still record the
	 * enclosing scope so we can push in and pop out of class defs.
	 */
	private ClassSymbol superClass;

	private ClassScope staticScope;
	private ClassScope instanceScope;

	/**
	 * True if this is a class defined by the system/language.
	 * <p>
	 * Those classes are special classes that are not generated the same way.
	 */
	private boolean isSystem;

	public ClassSymbol(String name, Scope enclosingScope, ClassSymbol superClass)
	{
		super(name);

		staticScope = new ClassScope(enclosingScope, superClass, true);
		instanceScope = new ClassScope(staticScope, superClass, false);

		this.superClass = superClass;
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

	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();

		if (superClass != null)
			ret.append("<" + superClass.getName() + ">");

		ret.append("class " + getName() + "{ static " + staticScope + ", " + instanceScope.toString(false) + " }");

		return ret.toString();
	}

	public boolean isInstanceOf(ClassSymbol classSymbol)
	{
		if (this.equals(classSymbol))
			return true;

		if (superClass == null)
			return false;

		return superClass.isInstanceOf(classSymbol);
	}

	@Override
	public boolean canAssignTo(Type type)
	{
		if (this.equals(type))
			return true;

		if (!(type instanceof ClassSymbol))
			return false;

		return isInstanceOf((ClassSymbol) type);
	}

	public boolean isSystem()
	{
		return isSystem;
	}

	public void setSystem(boolean isSystem)
	{
		this.isSystem = isSystem;
	}

	@Override
	public String getScopeName()
	{
		return getName();
	}

	@Override
	public Scope getParentScope()
	{
		//This is equivalent to staticScope
		return instanceScope.getParentScope();
	}

	@Override
	public Scope getEnclosingScope()
	{
		return staticScope.getEnclosingScope();
	}

	@Override
	public List<Scope> getChildrenScopes()
	{
		return instanceScope.getChildrenScopes();
	}

	@Override
	public void addChildScope(Scope child)
	{
		instanceScope.addChildScope(child);
	}

	/**
	 * Defines a symbol in this class' static scope.
	 */
	public Symbol defineStatic(Symbol symbol)
	{
		return staticScope.define(symbol);
	}

	/**
	 * Defines a symbol in this class' instance scope.
	 */
	@Override
	public Symbol define(Symbol symbol)
	{
		return instanceScope.define(symbol);
	}

	/**
	 * Resolve symbol in this class' static scope.
	 */
	public Symbol resolveStatic(SymbolSignature signature)
	{
		return staticScope.resolve(signature);
	}

	/**
	 * Resolve symbol in this class' instance scope.
	 */
	@Override
	public Symbol resolve(SymbolSignature signature)
	{
		return instanceScope.resolve(signature);
	}

	public Symbol getMember(SymbolSignature signature)
	{
		return instanceScope.getMember(signature);
	}

	public void putMember(Symbol symbol)
	{
		instanceScope.putMember(symbol);
	}

	public boolean containsMember(Symbol symbol)
	{
		return instanceScope.containsMember(symbol);
	}

	public Symbol resolveMember(SymbolSignature signature)
	{
		return instanceScope.resolveMember(signature);
	}

	public Set<MethodSymbol> resolveMethods(String name)
	{
		return instanceScope.resolveMethods(name);
	}

	public List<MethodSymbol> getInstanceMethods()
	{
		return instanceScope.getMethods();
	}

	public List<MethodSymbol> getStaticMethods()
	{
		return staticScope.getMethods();
	}

	public List<Symbol> getMembers()
	{
		return instanceScope.getMembers();
	}
}
