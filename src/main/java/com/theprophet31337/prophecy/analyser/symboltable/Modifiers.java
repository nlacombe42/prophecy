package com.theprophet31337.prophecy.analyser.symboltable;

public enum Modifiers
{
	STATIC("static");

	private String name;

	private Modifiers(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
