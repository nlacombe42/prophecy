package net.nlacombe.prophecy.parser;

import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import java.nio.file.Path;

public class AntlrParseMessageListener extends ConsoleErrorListener {

    private final Path filePath;
    private final BuildMessageService buildMessageService;

    public AntlrParseMessageListener(Path filePath, BuildMessageService buildMessageService) {
        this.filePath = filePath;
        this.buildMessageService = buildMessageService;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String message, RecognitionException recognitionException) {

        var token = getOffendingTokenOrNull(recognitionException, offendingSymbol);
        var sourceCodeLocation = getSourceCodeLocation(token, line, charPositionInLine);

        buildMessageService.error(sourceCodeLocation, message);
    }

    private SourceCodeLocation getSourceCodeLocation(Token token, int line, int charPositionInLine) {
        return token != null ?
            SourceCodeLocation.fromPosition(filePath, token.getLine(), token.getCharPositionInLine() + 1)
            :
            SourceCodeLocation.fromPosition(filePath, line, charPositionInLine + 1);
    }

    private Token getOffendingTokenOrNull(RecognitionException recognitionException, Object offendingSymbol) {
        if (recognitionException != null && recognitionException.getOffendingToken() != null)
            return recognitionException.getOffendingToken();

        if (offendingSymbol instanceof Token)
            return (Token) offendingSymbol;

        return null;
    }
}
