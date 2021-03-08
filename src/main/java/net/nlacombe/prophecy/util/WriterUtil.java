package net.nlacombe.prophecy.util;

import java.io.IOException;
import java.io.Writer;

public class WriterUtil {

    public static void writeRuntimeException(Writer writer, String string) {
        try {
            writer.write(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
