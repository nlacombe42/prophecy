package net.nlacombe.prophecy.v2.reporting;

import java.nio.file.Path;

public class SourceCodeLocation {

    private final Path filePath;
    private final Integer line;
    private final Integer column;

    public SourceCodeLocation(Path filePath, Integer line, Integer column) {
        this.filePath = filePath;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        if (filePath == null && line == null && column == null)
            return  "{ no location specified }";

        var text = "{ ";

        if (filePath != null)
            text += "file: " + filePath;

        if (line != null)
            text += ", line: " + line;

        if (column != null)
            text += ", column: " + column;

        text += " }";

        return text;
    }

    public Path getFilePath() {
        return filePath;
    }

    public Integer getLine() {
        return line;
    }

    public Integer getColumn() {
        return column;
    }
}
