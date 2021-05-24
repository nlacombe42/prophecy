package net.nlacombe.prophecy.symboltable.domain;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;

public class SymbolSignatureShadowException extends ProphecyCompilerException {

    private final Symbol shadowedSymbol;

    public SymbolSignatureShadowException(Symbol shadowedSymbol) {
        super("Symbol signature " + shadowedSymbol.getSignature() + " shadows symbol: " + shadowedSymbol);

        this.shadowedSymbol = shadowedSymbol;
    }

    public Symbol getShadowedSymbol() {
        return shadowedSymbol;
    }
}
