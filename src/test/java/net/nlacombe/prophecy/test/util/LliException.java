package net.nlacombe.prophecy.test.util;

import java.nio.file.Path;

public class LliException extends RuntimeException {

    private final Path llvmCodeFilePath;
    private final String stdout;
    private final String stderr;

    public LliException(Path llvmCodeFilePath, String stdout, String stderr) {
        super(
            "Error when compiling or running llvm code.\nLlvm code file path: $llvmCodeFilePath\nstdout:\n<<<$stdout>>>\nstderr:\n<<<$stderr>>>\n"
                .replace("$llvmCodeFilePath", llvmCodeFilePath.toString())
                .replace("$stdout", stdout)
                .replace("$stderr", stderr)
        );

        this.llvmCodeFilePath = llvmCodeFilePath;
        this.stdout = stdout;
        this.stderr = stderr;
    }

    public Path getLlvmCodeFilePath() {
        return llvmCodeFilePath;
    }

    public String getStdout() {
        return stdout;
    }

    public String getStderr() {
        return stderr;
    }
}
