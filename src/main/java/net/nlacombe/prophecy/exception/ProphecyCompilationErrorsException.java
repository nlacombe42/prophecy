package net.nlacombe.prophecy.exception;

import net.nlacombe.prophecy.reporting.BuildMessage;
import net.nlacombe.prophecy.reporting.BuildMessageLevel;

import java.util.List;
import java.util.stream.Collectors;

public class ProphecyCompilationErrorsException extends ProphecyCompilerException {

    private final List<BuildMessage> buildMessages;

    public ProphecyCompilationErrorsException(String message, List<BuildMessage> buildMessages) {
        super(message);
        this.buildMessages = buildMessages;
    }

    public ProphecyCompilationErrorsException(Throwable cause, List<BuildMessage> buildMessages) {
        super(cause);
        this.buildMessages = buildMessages;
    }

    public ProphecyCompilationErrorsException(String message, Throwable cause, List<BuildMessage> buildMessages) {
        super(message, cause);
        this.buildMessages = buildMessages;
    }

    public List<BuildMessage> getErrorBuildMessages() {
        return buildMessages.stream()
            .filter(buildMessage -> BuildMessageLevel.ERROR.equals(buildMessage.getBuildMessageLevel()))
            .collect(Collectors.toList());
    }

    public List<BuildMessage> getBuildMessages() {
        return buildMessages;
    }
}
