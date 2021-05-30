package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.exception.ProphecyCompilationErrorsException;
import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    @Disabled
    @Test
    public void test_test_file() {
        try {
            var prophecyCode = Files.readString(Path.of("./test.prophecy"));

            TestUtil.testProphecyProgramOutput(prophecyCode, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
