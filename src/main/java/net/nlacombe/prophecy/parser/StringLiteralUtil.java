package net.nlacombe.prophecy.parser;

public class StringLiteralUtil {

    public static String getStringValue(String stringLiteralSourceText) {
        var result = new StringBuilder();

        for (int i = 0; i < stringLiteralSourceText.length(); i++) {
            var currentChar = stringLiteralSourceText.charAt(i);

            if (currentChar != '\\') {
                result.append(currentChar);
                continue;
            }

            if (i == stringLiteralSourceText.length() - 1)
                throw new IllegalArgumentException("escape character at last string position");

            var nextChar = stringLiteralSourceText.charAt(i + 1);

            var replacement = switch (nextChar) {
                case 't' -> "\t";
                case 'r' -> "\r";
                case 'n' -> "\n";
                case '\"' -> "\"";
                case '\\' -> "\\";
                default -> throw new IllegalArgumentException("unknown escape sequence: \"\\" + nextChar + "\"");
            };

            result.append(replacement);
            i++;
        }

        return result.toString();
    }

}
