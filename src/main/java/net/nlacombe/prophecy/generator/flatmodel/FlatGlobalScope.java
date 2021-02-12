package net.nlacombe.prophecy.generator.flatmodel;

import java.util.LinkedList;
import java.util.List;

public class FlatGlobalScope
{
	private String customStartCode;
	private List<Structure> structures;
	private List<Function> functions;

	public FlatGlobalScope()
	{
		structures = new LinkedList<Structure>();
		functions = new LinkedList<Function>();
		customStartCode = "";
	}

	public void addCustomStartCode(String customStartCode)
	{
		this.customStartCode += customStartCode;
	}

	public void addStructure(Structure structure)
	{
		structures.add(structure);
	}

	public void addFunction(Function function)
	{
		functions.add(function);
	}

	public String getCustomStartCode()
	{
		return customStartCode;
	}

	public List<Structure> getStructures()
	{
		return structures;
	}

	public List<Function> getFunctions()
	{
		return functions;
	}
}
