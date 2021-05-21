package net.nlacombe.prophecy.symboltable.domain.scope;

import net.nlacombe.prophecy.symboltable.domain.SymbolSignatureAlreadyDefined;
import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Scope {

    Scope getParentScope();

    Scope getEnclosingScope();

    List<Scope> getChildrenScopes();

    /**
     * @throws SymbolSignatureAlreadyDefined
     */
    void define(Symbol symbol);

    /**
     * @return null if not found
     */
    Symbol resolve(SymbolSignature signature);

    static String toString(Map<SymbolSignature, Symbol> symbols, List<Scope> childScopes) {
        if (symbols.isEmpty() && childScopes.isEmpty())
            return "";

        var symbolsText = symbols.values().stream()
            .map(Symbol::toString)
            .collect(Collectors.joining("\n"));

        var scopesText = childScopes.stream()
            .filter(scope -> !(scope instanceof Symbol))
            .map(Scope::toString)
            .collect(Collectors.joining(""))
            .indent(4);

        var text = "";

        if (!symbols.isEmpty())
            text += symbolsText + "\n";

        if (!scopesText.isBlank())
            text += "{\n" + scopesText + "}\n";

        return text;
    }
}
