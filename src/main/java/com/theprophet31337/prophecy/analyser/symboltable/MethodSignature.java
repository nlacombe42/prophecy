package com.theprophet31337.prophecy.analyser.symboltable;

import java.util.LinkedList;
import java.util.List;

public class MethodSignature extends SymbolSignature
{
	private List<String> parameterTypeNames;
	private List<Type> parameterTypes;

	public MethodSignature(String name)
	{
		super(name);

		parameterTypeNames = new LinkedList<String>();
		parameterTypes = new LinkedList<Type>();
	}

	public int getNumberOfParameters()
	{
		return parameterTypes.size();
	}

	public void addParameter(String typeName)
	{
		parameterTypeNames.add(typeName);
		parameterTypes.add(null);
	}

	public void addParameter(Type type)
	{
		parameterTypeNames.add(type.getName());
		parameterTypes.add(type);
	}

	public String getParameterTypeName(int index)
	{
		return parameterTypeNames.get(index);
	}

	public Type getParameterType(int index)
	{
		return parameterTypes.get(index);
	}

	public List<String> getParameterTypeNames()
	{
		return parameterTypeNames;
	}

	public List<Type> getParameterTypes()
	{
		return parameterTypes;
	}

	/**
	 * Returns true if a method with this method signature can
	 * be called with the specified call signature.
	 * <p>
	 * This will return true if both the method signature and the call
	 * signature have the same number of parameters and the call signature is
	 * more or equally specific than this method signature.
	 */
	public boolean isCallableWith(MethodSignature callSignature)
	{
		if (getNumberOfParameters() != callSignature.getNumberOfParameters())
			return false;

		return callSignature.isMoreOrEquallySpecificThan(this);
	}

	/**
	 * Returns true if this method signature is more or equally specific than
	 * the specified method signature.
	 * <p>
	 * A method signature A is more or equally specific than a method signature B
	 * if for all of A's parameter types corresponding (same position) to B's parameter types,
	 * A's parameter types can be assigned to B's parameter types.
	 * <p>
	 * Eg.: Given class B extends class A: fb(B, B) is more specific than fa(A, A).
	 */
	public boolean isMoreOrEquallySpecificThan(MethodSignature signature)
	{
		if (getNumberOfParameters() != signature.getNumberOfParameters())
			throw new IllegalArgumentException("signature does not have same number of parameters");

		Type localType;
		Type signatureType;

		for (int i = 0; i < getNumberOfParameters(); i++) {
			localType = getParameterType(i);
			signatureType = signature.getParameterType(i);

			if (!localType.canAssignTo(signatureType))
				return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		String signature = getName();
		boolean first = true;

		signature += '(';

		for (String parameterTypeName : parameterTypeNames) {
			if (first)
				first = false;
			else
				signature += ',';

			signature += parameterTypeName;
		}

		signature += ')';

		return signature;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object == null)
			return false;

		if (object == this)
			return true;

		if (!(object instanceof MethodSignature))
			return false;

		MethodSignature signature = (MethodSignature) object;

		if (!signature.getName().equals(getName()))
			return false;

		if (signature.getParameterTypeNames().size() != parameterTypes.size())
			return false;

		String thisParameter;
		String otherParameter;

		for (int i = 0; i < signature.getParameterTypeNames().size(); i++) {
			thisParameter = getParameterTypeName(i);
			otherParameter = signature.getParameterTypeName(i);

			if (!thisParameter.equals(otherParameter))
				return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}
}
