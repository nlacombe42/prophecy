package net.nlacombe.prophecy.symboltable.domain.scope;

public class GlobalScope extends BaseScope
{
	public GlobalScope()
	{
		super(null);
	}

	public String getScopeName()
	{
		return "global";
	}
}
