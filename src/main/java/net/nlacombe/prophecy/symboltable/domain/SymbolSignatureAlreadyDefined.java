package net.nlacombe.prophecy.symboltable.domain;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;

public class SymbolSignatureAlreadyDefined extends ProphecyCompilerException {

    private Symbol alreadyDefinedSymbol;

    public SymbolSignatureAlreadyDefined(Symbol alreadyDefinedSymbol) {
        super("Symbol signature " + alreadyDefinedSymbol.getSignature() + " is already used by symbol: " + alreadyDefinedSymbol);

        this.alreadyDefinedSymbol = alreadyDefinedSymbol;
    }

    public Symbol getAlreadyDefinedSymbol() {
        return alreadyDefinedSymbol;
    }
}
