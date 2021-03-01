package net.nlacombe.prophecy.test.util;

import net.nlacombe.prophecy.v2.compiler.ProphecyV2Compiler;
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

            var compiler = new ProphecyV2Compiler(inputStream, null, outputStream);
            var compilationResult = compiler.compile();

            var programOutput = runLlvmCodeAndGetOutput(llvmCodeFilePath);

            if (!expectedOutput.equals(programOutput)) {
                var message = "";
                message += "Expected program output:\n";
                message += "<<<" + programOutput +">>>\n";
                message += "to be equal to:\n";
                message += "<<<" + expectedOutput +">>>\n";
                message += "ast root:\n";
                message += "<<<\n" + compilationResult.getAstRoot() +"\n>>>\n";
                message += "global scope:\n";
                message += "<<<\n" + compilationResult.getGlobalScope() +"\n>>>\n";

                throw new RuntimeException(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String runLlvmCodeAndGetOutput(Path llvmCodeFilePath) throws IOException {
        var process = Runtime.getRuntime().exec("lli " + llvmCodeFilePath);
        process.onExit().join();

        return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
    }

}
