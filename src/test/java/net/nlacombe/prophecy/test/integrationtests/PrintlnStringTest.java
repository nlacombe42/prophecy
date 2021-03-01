package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.test.util.TestUtil;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilationErrorsException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrintlnStringTest {

    @Test
    public void println_string_one_line() {
        test_println_string_without_escape("one line message.");
    }

    @Test
    public void println_string_multi_line() {
        test_println_string_without_escape("multi\nline\nmessage.");
    }

    @Test
    public void println_string_all_escapes() {
        var prophecyCode = """
            println("newline\\ntab\\tcarriage return\\rafter carriage return. quote\\"backslash\\\\")
            """;
        var expectedString = "newline\ntab\tcarriage return\rafter carriage return. quote\"backslash\\";

        TestUtil.testProphecyProgramOutput(prophecyCode, expectedString + "\n");
    }

    @Test
    public void println_string_interpolation_result_in_error() {
        var prophecyCode = """
            println("we are trying ${forbidenStringInterpolation}")
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void println_random_string() {
        test_println_string_without_escape(getRandomStringNoEscapeSequenceOrInterpolation());
    }

    private void test_println_string_without_escape(String expectedString) {
        var prophecyCode = """
            println("$expectedString")
            """
            .replace("$expectedString", String.valueOf(expectedString));

        TestUtil.testProphecyProgramOutput(prophecyCode, expectedString + "\n");
    }

    private String getRandomStringNoEscapeSequenceOrInterpolation() {
        String randomString;

        do {
            randomString = RandomStringUtils.randomPrint(1, 20);
        } while (randomString.contains("\\") || randomString.contains("${"));

        return randomString;
    }

}
