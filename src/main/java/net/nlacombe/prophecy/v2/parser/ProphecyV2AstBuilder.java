package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Lexer;
import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Parser;
import net.nlacombe.prophecy.shared.parser.ProphecyBuildListenerConsoleErrorListener;
import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2FileAstNode;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;

public class ProphecyV2AstBuilder {

    private final InputStream input;
    private final ProphecyBuildListener listener;

    public ProphecyV2AstBuilder(InputStream inputStream, ProphecyBuildListener prophecyBuildListener) {
        this.input = inputStream;
        this.listener = prophecyBuildListener;
    }

    public ProphecyV2FileAstNode parse() {
        var parser = getParser(input, listener);

        var fileContext = parser.file();

        if (parser.getNumberOfSyntaxErrors() > 0)
            throw new ProphecyCompilerException("Syntax errors found; aborting compilation.");

        return visit(fileContext);
    }

    private ProphecyV2FileAstNode visit(ProphecyV2Parser.FileContext fileContext) {
        return (ProphecyV2FileAstNode) fileContext.accept(new ProphecyV2AstBuilderParseTreeVisitor(listener)).get(0);
    }

    private ProphecyV2Parser getParser(InputStream input, ProphecyBuildListener listener) {
        try {
            var charStreams = CharStreams.fromStream(input);
            var lexer = new ProphecyV2Lexer(charStreams);
            var tokens = new CommonTokenStream(lexer);

            var parser = new ProphecyV2Parser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new ProphecyBuildListenerConsoleErrorListener(listener));

            return parser;
        } catch (IOException e) {
            throw new ProphecyCompilerException(e);
        }
    }
}
