package net.nlacombe.prophecy.v2.exception;

public class ProphecyCompilerException extends RuntimeException {

    public ProphecyCompilerException(String message) {
        super(message);
    }

    public ProphecyCompilerException(Throwable cause) {
        super(cause);
    }

    public ProphecyCompilerException(String message, Throwable cause) {
        super(message, cause);
    }
}
