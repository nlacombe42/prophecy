package net.nlacombe.prophecy.parser;

public class ProphecyParserException extends Exception
{
	private static final long serialVersionUID = -7200219078099846165L;

	public ProphecyParserException()
	{
		//Do nothing
	}

	public ProphecyParserException(String message)
	{
		super(message);
	}

	public ProphecyParserException(Throwable e)
	{
		super(e);
	}
}
