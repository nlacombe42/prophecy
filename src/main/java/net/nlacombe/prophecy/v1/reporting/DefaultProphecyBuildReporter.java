package net.nlacombe.prophecy.v1.reporting;

import net.nlacombe.prophecy.shared.reporting.BuildMessageLevel;
import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;

public class DefaultProphecyBuildReporter implements ProphecyBuildListener
{
	@Override
	public void buildMessage(BuildMessageLevel level, String message)
	{
		System.out.println(ProphecyFormatter.formatBuildMessage(level, message));
	}

	@Override
	public void buildMessage(BuildMessageLevel level, int line, int column, String message)
	{
		System.out.println(ProphecyFormatter.formatBuildMessage(level, line, column, message));
	}
}
