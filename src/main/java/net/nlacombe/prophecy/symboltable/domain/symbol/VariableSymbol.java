package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.signature.NameOnlySymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;

public class VariableSymbol extends Symbol {

    public VariableSymbol(String name, Type type) {
        super(name, type);
    }

    @Override
    public SymbolSignature getSignature() {
        return new NameOnlySymbolSignature(getName());
    }
}
