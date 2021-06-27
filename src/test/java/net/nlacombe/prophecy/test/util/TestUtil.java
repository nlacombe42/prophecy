package net.nlacombe.prophecy.test.util;

import net.nlacombe.prophecy.compiler.ProphecyCompiler;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtil {

    public static void testProphecyProgramOutput(String prophecyCode, String expectedOutput) {
        try {
            var inputStream = new ByteArrayInputStream(prophecyCode.getBytes(StandardCharsets.UTF_8));
            var llvmCodeFilePath = Files.createTempFile("prophecy-test-llvm-output", ".ll");
            var outputStream = new FileOutputStream(llvmCodeFilePath.toFile());

            var compiler = new ProphecyCompiler(inputStream, null, outputStream);
            var compilationResult = compiler.compile();
            var programOutput = runLlvmCodeAndGetOutput(llvmCodeFilePath);

            if (!expectedOutput.equals(programOutput))
                throw new ProphecyIntegrationTestException(expectedOutput, programOutput, compilationResult.getAstRoot(), compilationResult.getGlobalScope(), llvmCodeFilePath);
        } catch (IOException e) {
            throw new ProphecyIntegrationTestException(e);
        }
    }

    public static String runLlvmCodeAndGetOutput(Path llvmCodeFilePath) throws IOException {
        var process = Runtime.getRuntime().exec("lli " + llvmCodeFilePath);
        process.onExit().join();

        var stdout = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
        var stderr = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);

        if (process.exitValue() == 1)
            throw new LliException(llvmCodeFilePath, stdout, stderr);

        return stdout;
    }

}
