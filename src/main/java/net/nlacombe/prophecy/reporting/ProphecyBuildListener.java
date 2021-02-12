package net.nlacombe.prophecy.reporting;

public interface ProphecyBuildListener
{
	public void buildMessage(BuildMessageLevel level, String message);

	public void buildMessage(BuildMessageLevel level, int line, int column, String message);
}
