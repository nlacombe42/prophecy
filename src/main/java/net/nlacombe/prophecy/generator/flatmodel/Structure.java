package net.nlacombe.prophecy.generator.flatmodel;

import java.util.LinkedList;
import java.util.List;

public class Structure extends FlatSymbol
{
	private List<FlatSymbol> members;

	public Structure(String name)
	{
		super(name, null);

		this.members = new LinkedList<FlatSymbol>();
	}

	public Structure(String name, List<FlatSymbol> members)
	{
		super(name, null);

		this.members = members;
	}

	public void addMember(FlatSymbol symbol)
	{
		members.add(symbol);
	}

	public void addMembers(List<FlatSymbol> symbols)
	{
		members.addAll(symbols);
	}

	public List<FlatSymbol> getMembers()
	{
		return members;
	}
}
