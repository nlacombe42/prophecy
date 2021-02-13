package net.nlacombe.prophecy.v1.exception;

public class ProphecyException extends Exception
{
	private static final long serialVersionUID = -2455512442993140435L;

	public ProphecyException()
	{
		//Do nothing
	}

	public ProphecyException(String message)
	{
		super(message);
	}

	public ProphecyException(Throwable t)
	{
		super(t);
	}
}
