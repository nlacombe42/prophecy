package net.nlacombe.prophecy.generator;

public class LlvmTemporaryNameGenerator {

    private int lastUsedTemporaryLlvmName;

    public LlvmTemporaryNameGenerator() {
        lastUsedTemporaryLlvmName = 0;
    }

    public String getNewTemporaryLlvmName() {
        lastUsedTemporaryLlvmName++;

        return "%" + lastUsedTemporaryLlvmName;
    }

}
