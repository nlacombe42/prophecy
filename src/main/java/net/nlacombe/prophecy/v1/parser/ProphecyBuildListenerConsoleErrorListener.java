package net.nlacombe.prophecy.v1.parser;

import net.nlacombe.prophecy.v1.reporting.BuildMessageLevel;
import net.nlacombe.prophecy.v1.reporting.ProphecyBuildListener;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class ProphecyBuildListenerConsoleErrorListener extends ConsoleErrorListener {

    private final ProphecyBuildListener listener;

    public ProphecyBuildListenerConsoleErrorListener(ProphecyBuildListener listener) {
        this.listener = listener;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int ruleStartLine, int ruleStartColumn, String msg, RecognitionException recognitionException) {
        var token = recognitionException != null ? recognitionException.getOffendingToken() : (Token) offendingSymbol;

        listener.buildMessage(BuildMessageLevel.ERROR, token.getLine(), token.getCharPositionInLine() + 1, msg);
    }
}
