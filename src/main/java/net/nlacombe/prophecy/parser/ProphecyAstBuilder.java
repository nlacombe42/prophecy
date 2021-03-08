package net.nlacombe.prophecy.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyLexer;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser;
import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ProphecyAstBuilder {

    private final InputStream input;
    private final Path filePath;
    private final BuildMessageService buildMessageService;

    public ProphecyAstBuilder(InputStream inputStream, Path filePath, BuildMessageService buildMessageService) {
        this.input = inputStream;
        this.filePath = filePath;
        this.buildMessageService = buildMessageService;
    }

    public ProphecyFileAstNode parse() {
        var parser = getParser(input, buildMessageService);

        var fileContext = parser.file();

        return visit(fileContext);
    }

    private ProphecyFileAstNode visit(ProphecyParser.FileContext fileContext) {
        return (ProphecyFileAstNode) fileContext.accept(new ProphecyAstBuilderParseTreeVisitor(filePath, buildMessageService)).get(0);
    }

    private ProphecyParser getParser(InputStream input, BuildMessageService buildMessageService) {
        try {
            var antlrParseMessageListener = new AntlrParseMessageListener(filePath, buildMessageService);
            var charStreams = CharStreams.fromStream(input);
            var lexer = new ProphecyLexer(charStreams);
            lexer.removeErrorListeners();
            lexer.addErrorListener(antlrParseMessageListener);

            var tokens = new CommonTokenStream(lexer);

            var parser = new ProphecyParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(antlrParseMessageListener);

            return parser;
        } catch (IOException e) {
            throw new ProphecyCompilerException(e);
        }
    }
}
