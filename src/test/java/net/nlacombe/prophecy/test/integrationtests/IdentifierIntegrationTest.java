package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdentifierIntegrationTest {

    @Test
    public void unknown_identifier_throws_error() {
        var prophecyCode = """
            println(v2)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void test_variable_declaration_initializer_from_identifier_and_println_identifier() {
        var randomUInt8 = new Random().nextInt(256);
        var prophecyCode = """
            val agi = [$randomUInt8].get(0)
            val v2 = agi
            println(v2)
            """
            .replace("$randomUInt8", "" + randomUInt8);

        TestUtil.testProphecyProgramOutput(prophecyCode, "" + randomUInt8 + "\n");
    }

    @Test
    public void println_identifer_selection_expression_call_return_correct_value() {
        var expectedRandomUInt8 = new Random().nextInt(256);
        var firstRandomUInt8 = new Random().nextInt(256);
        var thirdRandomUInt8 = new Random().nextInt(256);
        var prophecyCode = """
            val arr = [$firstRandomUInt8, $expectedRandomUInt8, $thirdRandomUInt8]
            println(arr.get(1))
            """
            .replace("$firstRandomUInt8", "" + firstRandomUInt8)
            .replace("$expectedRandomUInt8", "" + expectedRandomUInt8)
            .replace("$thirdRandomUInt8", "" + thirdRandomUInt8);

        TestUtil.testProphecyProgramOutput(prophecyCode, "" + expectedRandomUInt8 + "\n");
    }
}