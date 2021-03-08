package net.nlacombe.prophecy.reporting;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BuildMessageService {

    private static final Logger logger = LoggerFactory.getLogger(BuildMessageService.class);

    private final List<BuildMessage> buildMessages;

    public BuildMessageService() {
        buildMessages = new ArrayList<>();
    }

    public void error(SourceCodeLocation sourceCodeLocation, String message) {
        newBuildMessage(BuildMessageLevel.ERROR, sourceCodeLocation, message);
    }

    public void newBuildMessage(BuildMessageLevel buildMessageLevel, SourceCodeLocation sourceCodeLocation, String message) {
        buildMessages.add(new BuildMessage(buildMessageLevel, sourceCodeLocation, message));

        switch (buildMessageLevel) {
            case ERROR -> logger.error("Error at " + sourceCodeLocation + " : " + message);
            case WARNING -> logger.warn("Warning at " + sourceCodeLocation + " : " + message);
            default -> throw new ProphecyCompilerException("Unimplemented build message level logging: " + buildMessageLevel);
        }
    }

    public boolean hasErrorBuildMessage() {
        return buildMessages.stream()
            .anyMatch(buildMessage -> BuildMessageLevel.ERROR.equals(buildMessage.getBuildMessageLevel()));
    }

    public List<BuildMessage> getBuildMessages() {
        return buildMessages;
    }
}
