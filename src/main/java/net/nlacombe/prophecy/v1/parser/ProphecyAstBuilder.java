package net.nlacombe.prophecy.v1.parser;

import net.nlacombe.prophecy.shared.parser.ProphecyBuildListenerConsoleErrorListener;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.parser.antlr4.ProphecyLexer;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser;
import net.nlacombe.prophecy.v1.reporting.DefaultProphecyBuildReporter;
import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;

public class ProphecyAstBuilder
{
    private final InputStream input;
	private final ProphecyBuildListener listener;

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
		var parser = setupParser(input);
		var tree = parser.file();

		if (parser.getNumberOfSyntaxErrors() > 0)
			throw new ProphecyParserException("Syntax errors found; aborting build.");

		var astBuilder = new AstBuilder();
		var walker = new ParseTreeWalker();

		walker.walk(astBuilder, tree);

		return astBuilder.getTreeRoot();
	}

	private ProphecyParser setupParser(InputStream input) throws IOException
	{
        var charStreams = CharStreams.fromStream(input);
		var lexer = new ProphecyLexer(charStreams);
		var tokens = new CommonTokenStream(lexer);

		var parser = new ProphecyParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new ProphecyBuildListenerConsoleErrorListener(listener));
		parser.setErrorHandler(new ParseErrorHandler());

		return parser;
	}
}
