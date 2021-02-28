package net.nlacombe.prophecy.v2.compiler;

import net.nlacombe.prophecy.v2.analyser.symboltable.SymbolTableBuilderV2;
import net.nlacombe.prophecy.v2.analyser.type.TypeAnalyserV2;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.v2.generator.llvm.LlvmGenerator;
import net.nlacombe.prophecy.v2.parser.ProphecyV2AstBuilder;
import net.nlacombe.prophecy.v2.reporting.BuildMessageService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ProphecyV2Compiler {

    private final InputStream inputStream;
    private final Path inputFilePath;
    private final OutputStream outputStream;

    public ProphecyV2Compiler(InputStream inputStream, Path inputFilePath, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.inputFilePath = inputFilePath;
        this.outputStream = outputStream;
    }

    /**
     * @throws ProphecyCompilationErrorsException, ProphecyCompilerException
     */
    public ProphecyCompilerResult compile() {
        try (var outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            var buildMessageService = new BuildMessageService();

            var astRoot = new ProphecyV2AstBuilder(inputStream, inputFilePath, buildMessageService).parse();
            var globalScope = new SymbolTableBuilderV2().buildSymbolTable(astRoot);
            new TypeAnalyserV2().evaluateTypesAndResolveCalls(astRoot, buildMessageService);

            if (buildMessageService.hasErrorBuildMessage())
                throw new ProphecyCompilationErrorsException("Error(s) found while compiling aborting.", buildMessageService.getBuildMessages());

            new LlvmGenerator().generate(outputStreamWriter, globalScope);

            return new ProphecyCompilerResult(astRoot, globalScope);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
