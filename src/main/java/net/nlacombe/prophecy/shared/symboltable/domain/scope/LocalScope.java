package net.nlacombe.prophecy.shared.symboltable.domain.scope;

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
