package net.nlacombe.prophecy.shared.symboltable.domain.symbol;

import net.nlacombe.prophecy.shared.symboltable.domain.ArraySignature;
import net.nlacombe.prophecy.shared.symboltable.domain.Type;

public class ArraySymbol extends Symbol implements Type
{
	public ArraySymbol(String name, Type elementType)
	{
		super(name, elementType);
	}

	public Type getElemtnType()
	{
		return getType();
	}

	@Override
	public ArraySignature getSignature()
	{
		return new ArraySignature(getType().getName());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;

		if (obj == this)
			return true;

		if (!(obj instanceof ArraySymbol))
			return false;

		ArraySymbol arraySymbol = (ArraySymbol) obj;

		return arraySymbol.getName().equals(getName());
	}

	@Override
	public String toString()
	{
		return getName();
	}

	@Override
	public boolean canAssignTo(Type type)
	{
		if (!(type instanceof ArraySymbol))
			return false;

		ArraySymbol array = (ArraySymbol) type;

		return this.getElemtnType().canAssignTo(array.getElemtnType());
	}
}
