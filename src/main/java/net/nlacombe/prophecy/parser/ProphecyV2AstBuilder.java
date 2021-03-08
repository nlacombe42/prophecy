package net.nlacombe.prophecy.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Lexer;
import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Parser;
import net.nlacombe.prophecy.ast.node.ProphecyV2FileAstNode;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ProphecyV2AstBuilder {

    private final InputStream input;
    private final Path filePath;
    private final BuildMessageService buildMessageService;

    public ProphecyV2AstBuilder(InputStream inputStream, Path filePath, BuildMessageService buildMessageService) {
        this.input = inputStream;
        this.filePath = filePath;
        this.buildMessageService = buildMessageService;
    }

    public ProphecyV2FileAstNode parse() {
        var parser = getParser(input, buildMessageService);

        var fileContext = parser.file();

        return visit(fileContext);
    }

    private ProphecyV2FileAstNode visit(ProphecyV2Parser.FileContext fileContext) {
        return (ProphecyV2FileAstNode) fileContext.accept(new ProphecyV2AstBuilderParseTreeVisitor(filePath, buildMessageService)).get(0);
    }

    private ProphecyV2Parser getParser(InputStream input, BuildMessageService buildMessageService) {
        try {
            var antlrParseMessageListener = new AntlrParseMessageListener(filePath, buildMessageService);
            var charStreams = CharStreams.fromStream(input);
            var lexer = new ProphecyV2Lexer(charStreams);
            lexer.removeErrorListeners();
            lexer.addErrorListener(antlrParseMessageListener);

            var tokens = new CommonTokenStream(lexer);

            var parser = new ProphecyV2Parser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(antlrParseMessageListener);

            return parser;
        } catch (IOException e) {
            throw new ProphecyCompilerException(e);
        }
    }
}
