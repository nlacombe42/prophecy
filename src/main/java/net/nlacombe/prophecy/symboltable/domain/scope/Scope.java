package net.nlacombe.prophecy.symboltable.domain.scope;

import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.Symbol;

import java.util.List;

public interface Scope {

    Scope getParentScope();

    Scope getEnclosingScope();

    List<Scope> getChildrenScopes();

    Symbol define(Symbol symbol);

    Symbol resolve(SymbolSignature signature);

}
