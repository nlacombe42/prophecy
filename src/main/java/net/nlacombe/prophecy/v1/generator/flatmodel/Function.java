package net.nlacombe.prophecy.v1.generator.flatmodel;

import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;

import java.util.LinkedList;
import java.util.List;

public class Function extends FlatSymbol
{
	private List<FlatSymbol> parameters;
	private String instructions;
	private ProphecyAstNode methodBodyBlockNode;

	public Function(String name, String returnType)
	{
		super(name, returnType);

		this.parameters = new LinkedList<FlatSymbol>();
	}

	public Function(String name, String returnType, List<FlatSymbol> parameters, ProphecyAstNode methodBodyBlockNode)
	{
		this(name, returnType);

		this.parameters = parameters;
		this.methodBodyBlockNode = methodBodyBlockNode;
	}

	public void setInstructions(String instructions)
	{
		this.instructions = instructions;
	}

	public String getInstructions()
	{
		return instructions;
	}

	public void addParameter(FlatSymbol parameter)
	{
		parameters.add(parameter);
	}

	public void setMethodBodyBlockNode(ProphecyAstNode methodBodyBlockNode)
	{
		this.methodBodyBlockNode = methodBodyBlockNode;
	}

	public ProphecyAstNode getMethodBodyBlockNode()
	{
		return methodBodyBlockNode;
	}

	public List<FlatSymbol> getParameters()
	{
		return parameters;
	}

	/**
	 * Same thing as getType(), this is just for better naming.
	 */
	public String getReturnType()
	{
		return getType();
	}
}
