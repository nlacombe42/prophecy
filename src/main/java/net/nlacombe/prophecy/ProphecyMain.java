package net.nlacombe.prophecy;

import net.nlacombe.prophecy.compiler.ProphecyCompiler;
import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class ProphecyMain {

    private static final Logger logger = LoggerFactory.getLogger(ProphecyMain.class);

    public static void main(String[] args) {
        var inputFilePath = Path.of("input.txt");

        try (
            var fileInputStream = new FileInputStream(inputFilePath.toFile());
            var fileOutputStream = new FileOutputStream("output.ll")
        ) {
            var compiler = new ProphecyCompiler(fileInputStream, inputFilePath, fileOutputStream);
            var compilationResult = compiler.compile();

            logger.info("ast root:<<<\n" + compilationResult.getAstRoot() + "\n>>>");
            logger.info("global scope:<<<\n" + compilationResult.getGlobalScope() + "\n>>>");
        } catch (ProphecyCompilationErrorsException exception) {
            logger.error(exception.getMessage());
            System.exit(-1);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
