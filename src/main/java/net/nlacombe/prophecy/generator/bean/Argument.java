package net.nlacombe.prophecy.generator.bean;

public class Argument
{
	private String type;
	private String valueId;

	public Argument()
	{
		//Do nothing
	}

	public Argument(String type, String valueId)
	{
		this.type = type;
		this.valueId = valueId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getValueId()
	{
		return valueId;
	}

	public void setValueId(String valueId)
	{
		this.valueId = valueId;
	}
}
