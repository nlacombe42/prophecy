package net.nlacombe.prophecy.exception;

public class ProphecyAmbiguousCallException extends ProphecyException
{
	private static final long serialVersionUID = 6522965285528901314L;

	public ProphecyAmbiguousCallException()
	{
		//Do nothing
	}

	public ProphecyAmbiguousCallException(String message)
	{
		super(message);
	}

	public ProphecyAmbiguousCallException(Throwable t)
	{
		super(t);
	}
}
