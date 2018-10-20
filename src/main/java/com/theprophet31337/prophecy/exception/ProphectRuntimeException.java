package com.theprophet31337.prophecy.exception;

public class ProphectRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = 2902275679950335035L;

	public ProphectRuntimeException()
	{
		//Do nothing
	}

	public ProphectRuntimeException(String message)
	{
		super(message);
	}

	public ProphectRuntimeException(Throwable t)
	{
		super(t);
	}
}
