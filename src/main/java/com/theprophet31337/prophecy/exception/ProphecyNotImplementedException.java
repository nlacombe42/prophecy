package com.theprophet31337.prophecy.exception;

public class ProphecyNotImplementedException extends ProphectRuntimeException
{
	private static final long serialVersionUID = -8277571480833571715L;

	public ProphecyNotImplementedException()
	{
		//Do nothing
	}

	public ProphecyNotImplementedException(String message)
	{
		super(message);
	}

	public ProphecyNotImplementedException(Throwable t)
	{
		super(t);
	}
}
