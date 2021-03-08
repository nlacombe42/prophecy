package net.nlacombe.prophecy.reporting;

public class BuildMessage {
    private final BuildMessageLevel buildMessageLevel;
    private final SourceCodeLocation sourceCodeLocation;
    private final String message;

    public BuildMessage(BuildMessageLevel buildMessageLevel, SourceCodeLocation sourceCodeLocation, String message) {
        this.buildMessageLevel = buildMessageLevel;
        this.sourceCodeLocation = sourceCodeLocation;
        this.message = message;
    }

    public BuildMessageLevel getBuildMessageLevel() {
        return buildMessageLevel;
    }

    public SourceCodeLocation getSourceCodeLocation() {
        return sourceCodeLocation;
    }

    public String getMessage() {
        return message;
    }
}
