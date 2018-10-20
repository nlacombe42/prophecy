package com.theprophet31337.prophecy.exception;

public class ProphecyMethodNotFoundException extends ProphecyException
{
	private static final long serialVersionUID = -2083254490192733232L;

	public ProphecyMethodNotFoundException()
	{
		//Do nothing
	}

	public ProphecyMethodNotFoundException(String message)
	{
		super(message);
	}

	public ProphecyMethodNotFoundException(Throwable t)
	{
		super(t);
	}
}
