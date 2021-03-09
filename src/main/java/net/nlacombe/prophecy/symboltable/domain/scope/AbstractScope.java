package net.nlacombe.prophecy.symboltable.domain.scope;

import net.nlacombe.prophecy.symboltable.domain.SymbolSignatureAlreadyDefined;
import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractScope implements Scope {

    private final Scope enclosingScope;
    private final List<Scope> children;
    private final Map<SymbolSignature, Symbol> symbols;

    public AbstractScope(Scope parent) {
        enclosingScope = parent;

        symbols = new LinkedHashMap<>();
        children = new LinkedList<>();
    }

    @Override
    public List<Scope> getChildrenScopes() {
        return children;
    }

    public List<Symbol> getSymbols() {
        List<Symbol> result = new ArrayList<Symbol>(symbols.size());

        result.addAll(symbols.values());

        return result;
    }

    @Override
    public Symbol resolve(SymbolSignature signature) {
        var symbol = symbols.get(signature);

        if (symbol != null)
            return symbol;

        if (getParentScope() != null)
            return getParentScope().resolve(signature);

        return null;
    }

    @Override
    public void define(Symbol symbol) {
        var previouslyDefined = symbols.get(symbol.getSignature());

        if (previouslyDefined != null)
            throw new SymbolSignatureAlreadyDefined(previouslyDefined);

        symbols.put(symbol.getSignature(), symbol);
        symbol.setScope(this);
    }

    public Scope getParentScope() {
        return getEnclosingScope();
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public String toString() {
        return Scope.toString(symbols, children);
    }
}
