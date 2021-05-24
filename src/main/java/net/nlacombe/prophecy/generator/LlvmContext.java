package net.nlacombe.prophecy.generator;

public class LlvmContext {

    private int lastUsedTemporaryLlvmName;
    private int lastUsedForeachNumber;

    public LlvmContext() {
        lastUsedTemporaryLlvmName = -1;
        lastUsedForeachNumber = 0;
    }

    public String getNewTemporaryLlvmName() {
        lastUsedTemporaryLlvmName++;

        return "%" + lastUsedTemporaryLlvmName;
    }

    public String getNewForeachName() {
        lastUsedForeachNumber++;

        return "foreach" + lastUsedForeachNumber;
    }

}
