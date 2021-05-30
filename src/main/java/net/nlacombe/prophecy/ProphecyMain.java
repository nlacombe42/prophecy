package net.nlacombe.prophecy;

import net.nlacombe.prophecy.compiler.ProphecyCompiler;
import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProphecyMain {

    private static final Logger logger = LoggerFactory.getLogger(ProphecyMain.class);

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            logger.error("Error: prophecy cli accepts only 1 argument, which must be a file path to a file with prophecy code.");
            System.exit(-1);
        }

        compileAndRun(Path.of(args[0]));
    }

    private static void compileAndRun(Path prophecyCodeFilePath) {
        try {
            var llvmOutputFilePath = Files.createTempFile("prophecy-llvm-output-", ".ll");

            try (
                var prophecyCodeInputStream = new FileInputStream(prophecyCodeFilePath.toFile());
                var llvmCodeOutputStream = new FileOutputStream(llvmOutputFilePath.toFile())
            ) {
                var compiler = new ProphecyCompiler(prophecyCodeInputStream, prophecyCodeFilePath, llvmCodeOutputStream);
                compiler.compile();

                var process = Runtime.getRuntime().exec("lli " + llvmOutputFilePath);

                var copyThread1 = new CopyStreamThread(process.getInputStream(), System.out);
                var copyThread2 =  new CopyStreamThread(process.getErrorStream(), System.err);
                copyThread1.start();
                copyThread2.start();

                process.waitFor();
                copyThread1.join();
                copyThread2.join();

                if (process.exitValue() != 0)
                    System.exit(-1);
            }
        } catch (ProphecyCompilationErrorsException exception) {
            logger.error(exception.getMessage());
            System.exit(-1);
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static class CopyStreamThread extends Thread {
        private final InputStream inputStream;
        private final OutputStream outputStream;

        private CopyStreamThread(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        public void run() {
            try {
                IOUtils.copy(inputStream, outputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
