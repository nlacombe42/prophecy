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
            System.println(v2)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void test_variable_declaration_initializer_from_identifier_and_println_identifier() {
        var randomUInt8 = new Random().nextInt(256);
        var prophecyCode = """
            val agi = [$randomUInt8].get(0)
            val v2 = agi
            System.println(v2)
            """
            .replace("$randomUInt8", "" + randomUInt8);

        TestUtil.testProphecyProgramOutput(prophecyCode, "" + randomUInt8 + "\n");
    }

    @Test
    public void println_identifier_selection_expression_call_return_correct_value() {
        var expectedRandomUInt8 = new Random().nextInt(256);
        var firstRandomUInt8 = new Random().nextInt(256);
        var thirdRandomUInt8 = new Random().nextInt(256);
        var prophecyCode = """
            val arr = [$firstRandomUInt8, $expectedRandomUInt8, $thirdRandomUInt8]
            System.println(arr.get(1))
            """
            .replace("$firstRandomUInt8", "" + firstRandomUInt8)
            .replace("$expectedRandomUInt8", "" + expectedRandomUInt8)
            .replace("$thirdRandomUInt8", "" + thirdRandomUInt8);

        TestUtil.testProphecyProgramOutput(prophecyCode, "" + expectedRandomUInt8 + "\n");
    }

    @Test
    public void throw_error_when_an_identifier_shadows_another_one() {
        var prophecyCode = """
            val element = "element"
            foreach element in [1]
                System.println(element)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void using_same_variable_name_in_different_foreach_loop_works() {
        var prophecyCode = """
            foreach i in Array.range(0, 2)
                System.println(i)

            foreach i in Array.range(0, 3)
                System.println(i)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "0\n1\n0\n1\n2\n");
    }
}
