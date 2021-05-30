package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeneralIntegrationTest {

    @Test
    public void consumes_all_input_for_file() {
        var prophecyCode = """
            val array = [11, 22, 33, 44, 77]

            foreach i in Array.range(0, array.size())
                System.println(array.get(i))
              asd
            """;

        assertThrows(ProphecyCompilationErrorsException.class, () -> TestUtil.testProphecyProgramOutput(prophecyCode, ""));
    }

}
