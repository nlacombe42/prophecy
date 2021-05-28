package net.nlacombe.prophecy.test.integrationtests;

import net.nlacombe.prophecy.test.util.TestUtil;
import org.junit.jupiter.api.Test;

public class CommentsIntegrationTest {

    @Test
    public void one_line_comment() {
        var prophecyCode = """
            # this is a comment before
            System.println('text')
            # this is a comment after
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "text\n");
    }

    @Test
    public void shebang() {
        var prophecyCode = """
            #!/opt/prophecy

            System.println('text')
            """;

        TestUtil.testProphecyProgramOutput(prophecyCode, "text\n");
    }

}
