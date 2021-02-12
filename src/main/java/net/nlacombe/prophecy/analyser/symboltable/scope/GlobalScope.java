package net.nlacombe.prophecy.analyser.symboltable.scope;

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
