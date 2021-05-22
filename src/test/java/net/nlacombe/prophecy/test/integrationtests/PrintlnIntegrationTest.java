package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.test.util.TestUtil;
import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrintlnIntegrationTest {

    @Test
    public void println_integer() {
        test_println_integer(42);
    }

    @Test
    public void println_random_integer() {
        test_println_integer(new Random().nextInt(256));
    }

    @Test
    public void println_integer_bigger_than_UInt8_throws_error() {
        assertThrows(ProphecyCompilationErrorsException.class, () -> test_println_integer(256));
    }

    private void test_println_integer(int expectedNumber) {
        var prophecyCode = """
            System.println($expectedNumber)
            """
            .replace("$expectedNumber", String.valueOf(expectedNumber));

        TestUtil.testProphecyProgramOutput(prophecyCode, expectedNumber + "\n");
    }

}
