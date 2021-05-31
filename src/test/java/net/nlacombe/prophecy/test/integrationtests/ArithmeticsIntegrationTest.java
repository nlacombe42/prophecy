package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArithmeticsIntegrationTest {

    @Test
    public void adding_literal_integers_works() {
        var prophecyCode = """
            System.println(2 + 3)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "5\n");
    }

    @Test
    public void adding_variable_and_literal_integer_works() {
        var prophecyCode = """
            val number = 42
            System.println(number + 24)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "66\n");
    }

    @Test
    public void throws_error_when_adding_string_and_literal_integer() {
        var prophecyCode = """
            System.println('test' + 24)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void subtracting_literal_integers_works() {
        var prophecyCode = """
            System.println(42 - 12)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "30\n");
    }

    @Test
    public void subtracting_variable_and_literal_integer_works() {
        var prophecyCode = """
            val number = 42
            System.println(number - 7)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "35\n");
    }

    @Test
    public void throws_error_when_subtracting_string_and_literal_integer() {
        var prophecyCode = """
            System.println('test' - 42)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void adding_results_from_calls_works() {
        var prophecyCode = """
            val leftArray = [1, 2]
            val rightArray = [1, 2, 3]
            System.println(leftArray.size() + rightArray.size())
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "5\n");
    }

}
