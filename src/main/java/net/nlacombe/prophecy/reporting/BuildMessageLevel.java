package net.nlacombe.prophecy.reporting;

public enum BuildMessageLevel
{
	INFO("Info"),
	WARNING("Warning"),
	ERROR("Error");

	private String text;

	private BuildMessageLevel(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	@Override
	public String toString()
	{
		return text;
	}
}
