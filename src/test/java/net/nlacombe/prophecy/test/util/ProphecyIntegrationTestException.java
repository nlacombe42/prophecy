package net.nlacombe.prophecy.test.util;

import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProphecyIntegrationTestException extends RuntimeException {

    private final String expectedOutput;
    private final String actualOutput;
    private final ProphecyAstNode astRoot;
    private final GlobalScope globalScope;
    private final Path llvmCodeFilePath;
    private final String llvmCode;

    public ProphecyIntegrationTestException(Throwable cause) {
        this(null, null, null, null, null, cause);
    }

    public ProphecyIntegrationTestException(String expectedOutput, String actualOutput, ProphecyAstNode astRoot, GlobalScope globalScope, Path llvmCodeFilePath) {
        this(expectedOutput, actualOutput, astRoot, globalScope, llvmCodeFilePath, null);
    }

    public ProphecyIntegrationTestException(String expectedOutput, String actualOutput, ProphecyAstNode astRoot, GlobalScope globalScope, Path llvmCodeFilePath, Throwable cause) {
        super(
            "Expected program output:\n" +
                "<<<" + actualOutput + ">>>\n" +
                "to be equal to:\n" +
                "<<<" + expectedOutput + ">>>\n" +
                "ast root:\n" +
                "<<<\n" + astRoot + "\n>>>\n" +
                "global scope:\n" +
                "<<<\n" + globalScope + "\n>>>\n" +
                "llvm code file path: " + llvmCodeFilePath
            , cause);

        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.astRoot = astRoot;
        this.globalScope = globalScope;
        this.llvmCodeFilePath = llvmCodeFilePath;

        if (llvmCodeFilePath == null)
            this.llvmCode = null;
        else {
            try {
                this.llvmCode = new String(Files.readAllBytes(llvmCodeFilePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public ProphecyAstNode getAstRoot() {
        return astRoot;
    }

    public GlobalScope getGlobalScope() {
        return globalScope;
    }

    public Path getLlvmCodeFilePath() {
        return llvmCodeFilePath;
    }

    public String getLlvmCode() {
        return llvmCode;
    }
}
