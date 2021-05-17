package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

public class UInt8ArrayIntegrationTest {

    @Test
    public void test_print_uint8_array_element_1_element() {
        var prophecyCode = """
            println([7].get(0))
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "7\n");
    }

    @Test
    public void test_print_uint8_array_element_2_elements() {
        var prophecyCode = """
            println([7, 3].get(1))
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "3\n");
    }
}
