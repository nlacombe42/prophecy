package com.theprophet31337.prophecy.analyser.symboltable.scope;

public class LocalScope extends BaseScope
{
	public LocalScope(Scope parent)
	{
		super(parent);
	}

	public String getScopeName()
	{
		return "local";
	}
}
