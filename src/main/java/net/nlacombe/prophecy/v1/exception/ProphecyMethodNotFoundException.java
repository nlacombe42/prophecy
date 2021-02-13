package net.nlacombe.prophecy.v1.exception;

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
