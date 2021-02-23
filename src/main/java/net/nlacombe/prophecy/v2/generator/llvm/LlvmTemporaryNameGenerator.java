package net.nlacombe.prophecy.v2.generator.llvm;

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
