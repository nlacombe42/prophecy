package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UInt8ArrayIntegrationTest {

    @Test
    public void test_print_uint8_array_element_1_element() {
        var prophecyCode = """
            System.println([7].get(0))
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "7\n");
    }

    @Test
    public void test_print_uint8_array_element_2_elements() {
        var prophecyCode = """
            System.println([7, 3].get(1))
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "3\n");
    }

    @Test
    public void test_print_uint8_array_siez_3_elements() {
        var prophecyCode = """
            System.println([7, 3, 54].size())
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "3\n");
    }

    @Test
    public void throw_error_on_empty_array() {
        var prophecyCode = """
            System.println([].size())
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void throw_error_on_static_get_call() {
        var prophecyCode = """
            Array.get(0)
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void throw_error_on_static_size_call() {
        var prophecyCode = """
            Array.size()
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

    @Test
    public void test_array_range() {
        var prophecyCode = """
            val arr = Array.range(0, 4)
            System.println(arr.size())
            System.println(arr.get(0))
            System.println(arr.get(1))
            System.println(arr.get(2))
            System.println(arr.get(3))
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "4\n0\n1\n2\n3\n");
    }
}
