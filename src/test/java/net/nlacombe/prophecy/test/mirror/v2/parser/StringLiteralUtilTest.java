package net.nlacombe.prophecy.test.mirror.v2.parser;

import net.nlacombe.prophecy.v2.parser.StringLiteralUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringLiteralUtilTest {

    @Test
    public void throws_IllegalArgumentException_on_unkown_escape_sequence() {
        assertThrows(IllegalArgumentException.class, () -> StringLiteralUtil.getStringValue("\\f"));
    }

    @Test
    public void return_same_string_when_no_escape() {
        var expected = "dat simple string";

        var stringValue = StringLiteralUtil.getStringValue(expected);

        assertThat(stringValue).isEqualTo(expected);
    }

    @Test
    public void escape_multi_test() {
        var input = "a\\tb\\rc\\nd\\\\e\\\"f";
        var expected = "a\tb\rc\nd\\e\"f";

        var stringValue = StringLiteralUtil.getStringValue(input);

        assertThat(stringValue).isEqualTo(expected);
    }

    @Test
    public void escape_backslash_then_t() {
        var input = "\\\\t";
        var expected = "\\t";

        var stringValue = StringLiteralUtil.getStringValue(input);

        assertThat(stringValue).isEqualTo(expected);
    }
}
