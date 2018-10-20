package com.theprophet31337.prophecy.parser;

import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.parser.antlr4.ProphecyLexer;
import com.theprophet31337.prophecy.parser.antlr4.ProphecyParser;
import com.theprophet31337.prophecy.parser.antlr4.ProphecyParser.FileContext;
import com.theprophet31337.prophecy.reporting.BuildMessageLevel;
import com.theprophet31337.prophecy.reporting.DefaultProphecyBuildReporter;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;

/**
 * Builds Prophecy AST from input (InputStream).
 */
public class ProphecyAstBuilder
{
	private class CustomConsoleErrorListener extends ConsoleErrorListener
	{
		@Override
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int ruleStartLine, int ruleStartColumn, String msg, RecognitionException e)
		{
			/** Token at which position we report the error.
			 */
			Token token;

			if (e != null) {
				token = e.getOffendingToken();
			} else {
				token = (Token) offendingSymbol;
			}

			listener.buildMessage(BuildMessageLevel.ERROR,
					token.getLine(), token.getCharPositionInLine() + 1, msg);
		}
	}

	private InputStream input;
	private ProphecyBuildListener listener;

	public ProphecyAstBuilder(InputStream input, ProphecyBuildListener listener)
	{
		this.input = input;
		this.listener = listener;
	}

	public ProphecyAstBuilder(InputStream input)
	{
		this.input = input;
		this.listener = new DefaultProphecyBuildReporter();
	}

	public ProphecyAstNode parse() throws IOException, ProphecyParserException
	{
		//Setup parser
		ProphecyParser parser = setupParser(input);

		//Parse
		FileContext tree = parser.file();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new ProphecyParserException("Syntax errors found; aborting build.");
		}

		AstBuilder astBuilder = new AstBuilder();

		ParseTreeWalker walker = new ParseTreeWalker();

		walker.walk(astBuilder, tree);

		return astBuilder.getTreeRoot();
	}

	/**
	 * Setup parser for the specified input and add error hander and error listener.
	 */
	private ProphecyParser setupParser(InputStream input) throws IOException
	{
		ANTLRInputStream antlrInput = new ANTLRInputStream(input);

		ProphecyLexer lexer = new ProphecyLexer(antlrInput);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ProphecyParser parser = new ProphecyParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new CustomConsoleErrorListener());
		parser.setErrorHandler(new ParseErrorHandler());

		return parser;
	}
}
