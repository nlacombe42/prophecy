package net.nlacombe.prophecy.v2;

import net.nlacombe.prophecy.v2.analyser.symboltable.SymbolTableBuilderV2;
import net.nlacombe.prophecy.v2.analyser.type.TypeAnalyserV2;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.v2.generator.LlvmGenerator;
import net.nlacombe.prophecy.v2.parser.ProphecyV2AstBuilder;
import net.nlacombe.prophecy.v2.reporting.Slf4jProphecyBuildListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ProphecyV2Compiler {

    private static final Logger logger = LoggerFactory.getLogger(ProphecyV2Compiler.class);

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ProphecyV2Compiler(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void compile() {
        try (var outputStreamWriter = new OutputStreamWriter(outputStream)) {
            var buildListener = new Slf4jProphecyBuildListener();

            logger.info("Building AST...");
            var astRoot = new ProphecyV2AstBuilder(inputStream, buildListener).parse();
            logger.info("astRoot: " + astRoot);

            logger.info("Building symbol table...");
            var globalScope = new SymbolTableBuilderV2().buildSymbolTable(astRoot);
            logger.info("globalScope: " + globalScope);

            logger.info("Performing Type Analysis and Check...");
            new TypeAnalyserV2().evaluateTypesAndResolveCalls(astRoot, buildListener);

            new LlvmGenerator().generate(outputStreamWriter, globalScope);
        } catch (ProphecyCompilerException exception) {
            logger.error(exception.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
