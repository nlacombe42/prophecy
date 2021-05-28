package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ForeachIntegrationTest {

    @Test
    public void throw_error_when_foreach_does_not_have_a_block() {
        var prophecyCode = """
            foreach i in [1, 2, 3]
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void throw_error_when_foreach_does_not_have_a_block_and_following_statement() {
        var prophecyCode = """
            foreach i in [1, 2, 3]
            System.println("test")
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void print_correct_values_when_printing_elements_from_foreach_array() {
        var prophecyCode = """
            foreach i in [1, 2, 3]
                System.println(i)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "1\n2\n3\n");
    }

    @Test
    public void print_correct_values_when_printing_elements_from_foreach_array_identifier() {
        var prophecyCode = """
            val array = [45, 34]

            foreach i in array
                System.println(i)
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "45\n34\n");
    }

    @Test
    public void throw_error_when_foreach_has_string_expression() {
        var prophecyCode = """
            foreach i in 'test'
                System.println(i)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void throw_error_when_foreach_has_string_array_expression() {
        var prophecyCode = """
            foreach i in ['a', 'b']
                System.println(i)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void prints_correctly_when_nested_foreach() {
        var prophecyCode = """
            foreach i1 in [1, 2]
                foreach i2 in [11, 22]
                    System.println(i1)
                    System.println(i2)
                    System.println('')
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "1\n11\n\n1\n22\n\n2\n11\n\n2\n22\n\n");
    }

    @Test
    public void prints_correctly_when_nested_foreach_with_print_statement_at_every_level() {
        var prophecyCode = """
            foreach i1 in [1, 2]
                System.println('f1 start')

                foreach i2 in [11, 22]
                    System.println(i1)
                    System.println(i2)
                    System.println('')

                System.println('f1 end')
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "f1 start\n1\n11\n\n1\n22\n\nf1 end\nf1 start\n2\n11\n\n2\n22\n\nf1 end\n");
    }

    @Test
    public void prints_content_of_array_correctly_using_range_to_generate_indexes() {
        var prophecyCode = """
            val array = [11, 22, 33, 44, 55]

            foreach i in Array.range(0, array.size())
                System.println(array.get(i))
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "11\n22\n33\n44\n55\n");
    }

    @Test
    public void throw_error_when_using_tab_indentation() {
        var prophecyCode = """
            foreach i in [11, 22]
            \tSystem.println(i)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void throw_error_when_using_2_spaces_indentation() {
        var prophecyCode = """
            foreach i in [11, 22]
              System.println(i)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }
}
