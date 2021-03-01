package net.nlacombe.prophecy.v2.reporting;

import java.nio.file.Path;
import java.util.ArrayList;

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
        var textParts = new ArrayList<String>();

        if (filePath != null)
            textParts.add("file: " + filePath);

        if (startLine == endLine && startColumn == endColumn) {
            textParts.add("line: " + startLine);
            textParts.add("column: " + startColumn);
        } else {
            textParts.add("start line: " + startLine);
            textParts.add("start column: " + startColumn);
            textParts.add("end line: " + endLine);
            textParts.add("end column: " + endColumn);
        }

        return String.join(", ", textParts);
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
