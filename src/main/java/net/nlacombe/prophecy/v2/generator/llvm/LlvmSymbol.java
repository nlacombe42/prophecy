package net.nlacombe.prophecy.v2.generator.llvm;

class LlvmSymbol {
    private final String type;
    private final String name;

    public LlvmSymbol(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
