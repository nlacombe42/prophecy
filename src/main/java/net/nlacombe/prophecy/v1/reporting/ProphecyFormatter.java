package net.nlacombe.prophecy.v1.reporting;

import net.nlacombe.prophecy.shared.reporting.BuildMessageLevel;

public class ProphecyFormatter
{
	public static String formatBuildMessage(BuildMessageLevel level, String message)
	{
		return level + ": " + message;
	}

	public static String formatBuildMessage
			(
					BuildMessageLevel level,
					int lineNumber,
					int column,
					String message
			)
	{
		return formatBuildMessage(level, "line " + lineNumber + " column " + column + ": " + message);
	}
}
