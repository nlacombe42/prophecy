package net.nlacombe.prophecy.v2.reporting;

import java.nio.file.Path;

public class SourceCodeLocation {

    private final Path filePath;
    private final int startLine;
    private final int startColumn;
    private final int endLine;
    private final int endColumn;

    public SourceCodeLocation(Path filePath, int startLine, int startColumn, int endLine, int endColumn) {
        this.filePath = filePath;
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public static SourceCodeLocation fromPosition(Path filePath, int line, int column) {
        return new SourceCodeLocation(filePath, line, column, line, column);
    }

    public static SourceCodeLocation fromRange(Path filePath, int startLine, int startColumn, int endLine, int endColumn) {
        return new SourceCodeLocation(filePath, startLine, startColumn, endLine, endColumn);
    }

    @Override
    public String toString() {
        var text = "{ ";

        if (filePath != null)
            text += "file: " + filePath;

        if (startLine == endLine && startColumn == endColumn) {
            text += ", line: " + startLine;
            text += ", column: " + startColumn;
        } else {
            text += ", start line: " + startLine;
            text += ", start column: " + startColumn;
            text += ", end line: " + endLine;
            text += ", end column: " + endColumn;
        }

        text += " }";

        return text;
    }

    public Path getFilePath() {
        return filePath;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }
}
