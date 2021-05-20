package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariableDeclarationIntegrationTest {

    @Test
    public void void_initializer_throws_error() {
        var prophecyCode = """
            val text = println("line")
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void no_error_on_string_initializer() {
        var prophecyCode = """
            val text = "some text"
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "");
    }

    @Test
    public void no_error_on_uint8_initializer() {
        var prophecyCode = """
            val text = 42
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "");
    }

    @Test
    public void no_error_on_uint8_array_initializer() {
        var prophecyCode = """
            val text = [1]
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "");
    }

    @Test
    public void no_error_on_typed_call_initializer() {
        var prophecyCode = """
            val text = [11].get(0)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "");
    }
}
