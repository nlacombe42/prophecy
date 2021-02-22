package net.nlacombe.prophecy;

import net.nlacombe.prophecy.v1.compiler.ProphecyCompiler;
import net.nlacombe.prophecy.v2.ProphecyV2Compiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProphecyMain {
    private static final Logger logger = LoggerFactory.getLogger(ProphecyMain.class);

    public static void main(String[] args) {
        executeProphecyV2Compiler();
    }

    private static void executeProphecyV2Compiler() {
        try (
            var fileInputStream = new FileInputStream("inputv2.txt");
            var fileOutputStream = new FileOutputStream("output.ll");
        ) {
            var compiler = new ProphecyV2Compiler(fileInputStream, fileOutputStream);
            compiler.compile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void executeProphecyV1Compiler() {
        try {
            var fileInputStream = new FileInputStream("input.txt");
            var fileOutputStream = new FileOutputStream("output.ll");

            var compiler = new ProphecyCompiler(fileInputStream, fileOutputStream);
            compiler.compile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
