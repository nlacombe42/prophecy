package net.nlacombe.prophecy.compiler;

import net.nlacombe.prophecy.analyser.symboltable.SymbolTableBuilderV2;
import net.nlacombe.prophecy.analyser.type.TypeAnalyserV2;
import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.generator.LlvmGenerator;
import net.nlacombe.prophecy.parser.ProphecyAstBuilder;
import net.nlacombe.prophecy.reporting.BuildMessageService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ProphecyCompiler {

    private final InputStream inputStream;
    private final Path inputFilePath;
    private final OutputStream outputStream;

    public ProphecyCompiler(InputStream inputStream, Path inputFilePath, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.inputFilePath = inputFilePath;
        this.outputStream = outputStream;
    }

    /**
     * @throws ProphecyCompilationErrorsException, ProphecyCompilerException
     */
    public ProphecyCompilationResult compile() {
        try (var outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            var buildMessageService = new BuildMessageService();

            var astRoot = new ProphecyAstBuilder(inputStream, inputFilePath, buildMessageService).parse();
            var globalScope = new SymbolTableBuilderV2().buildSymbolTable(astRoot);
            new TypeAnalyserV2().evaluateTypesAndResolveCalls(astRoot, buildMessageService);

            if (buildMessageService.hasErrorBuildMessage())
                throw new ProphecyCompilationErrorsException("Error(s) found while compiling aborting.", buildMessageService.getBuildMessages());

            new LlvmGenerator().generate(outputStreamWriter, globalScope);

            return new ProphecyCompilationResult(astRoot, globalScope);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
