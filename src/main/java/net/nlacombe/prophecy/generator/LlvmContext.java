package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;

import java.util.Map;

public class LlvmContext {

    private final Map<VariableSymbol, String> llvmNameBySymbol;

    private int lastUsedTemporaryLlvmName;
    private int lastUsedForeachNumber;

    public LlvmContext(Map<VariableSymbol, String> llvmNameBySymbol) {
        this.llvmNameBySymbol = llvmNameBySymbol;

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

    public String getLlvmVariableName(VariableSymbol variableSymbol) {
        return llvmNameBySymbol.get(variableSymbol);
    }

}
