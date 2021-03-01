package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class PrintlnIntegrationTest {

    @Test
    public void println_integer() {
        test_println_integer(42);
    }

    @Test
    public void println_random_integer() {
        test_println_integer(new Random().nextInt(Integer.MAX_VALUE));
    }

    private void test_println_integer(int expectedNumber) {
        var prophecyCode = """
            println($expectedNumber)
            """
            .replace("$expectedNumber", String.valueOf(expectedNumber));

        TestUtil.testProphecyProgramOutput(prophecyCode, expectedNumber + "\n");
    }

}
