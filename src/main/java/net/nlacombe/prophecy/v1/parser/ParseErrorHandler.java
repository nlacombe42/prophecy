package net.nlacombe.prophecy.v1.parser;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.IntervalSet;

import java.util.List;

/*
 * NOTE: the ConsoleErrorListener takes care of the general error header with the line number and etc.
 */
public class ParseErrorHandler extends DefaultErrorStrategy
{

	@Override
	protected void reportInputMismatch(Parser recognizer, InputMismatchException e)
	{
		Token token = e.getOffendingToken();

		String message = "unexpected \"" + token.getText() + "\", expecting " + getExpectedTokensText(recognizer, e.getExpectedTokens());

		recognizer.notifyErrorListeners(token, message, e);
	}

	@Override
	protected void reportMissingToken(Parser parser)
	{
		parser.notifyErrorListeners("missing " + getExpectedTokensText(parser, parser.getExpectedTokens()) +
				" to complete " + getCurrentRuleName(parser));
	}

	@Override
	protected void reportNoViableAlternative(Parser parser, NoViableAltException e)
	{
		Token token = e.getOffendingToken();
		String message = "error at \"" + token.getText() + "\"." +
				" Invalid " + getCurrentRuleName(parser) +
				" starting at " + getTokenPositionString(parser.getRuleContext().getStart());

		parser.notifyErrorListeners(token, message, e);
	}

	@Override
	protected void reportUnwantedToken(Parser recognizer)
	{
		recognizer.notifyErrorListeners("superfluous \"" + recognizer.getCurrentToken().getText() + "\" " +
				"expecting " + getExpectedTokensText(recognizer, recognizer.getExpectedTokens()));
	}

	private String getCurrentRuleName(Parser parser)
	{
		return parser.getRuleInvocationStack().get(0);
	}

	private String getExpectedTokensText(Parser parser, IntervalSet expectedTokens)
	{
		if (expectedTokens.size() == 0 || expectedTokens.get(0) == -1)
			return "";

		StringBuilder ret = new StringBuilder();

		String[] tokenNames = parser.getTokenNames();
		List<Integer> tokenOffsets = expectedTokens.toList();

		ret.append(tokenNames[tokenOffsets.get(0)]);

		for (int i = 1; i < tokenOffsets.size(); i++) {
			ret.append(" or " + tokenNames[tokenOffsets.get(i)]);
		}

		return ret.toString();
	}

	private String getTokenPositionString(Token token)
	{
		return "line " + token.getLine() + " column " + (token.getCharPositionInLine() + 1);
	}
}
