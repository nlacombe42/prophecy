package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.symboltable.domain.SymbolSignatureAlreadyDefined;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.symboltable.domain.signature.NameOnlySymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassSymbol extends Symbol implements Scope, Type {

    private final ClassSymbol superClass;
    private final Scope enclosingScope;
    private final Map<SymbolSignature, Symbol> members;

    public ClassSymbol(String name, Scope enclosingScope, ClassSymbol superClass) {
        super(name);

        this.superClass = superClass;
        this.enclosingScope = enclosingScope;

        members = new LinkedHashMap<>();
    }

    public boolean isInstanceOf(ClassSymbol classSymbol) {
        if (equals(classSymbol))
            return true;

        if (superClass == null)
            return false;

        return superClass.isInstanceOf(classSymbol);
    }

    @Override
    public List<Scope> getChildrenScopes() {
        return members.values().stream()
            .filter(member -> member instanceof Scope)
            .map(member -> (Scope) member)
            .collect(Collectors.toList());
    }

    @Override
    public SymbolSignature getSignature() {
        return new NameOnlySymbolSignature(getName());
    }

    @Override
    public boolean canAssignTo(Type type) {
        if (equals(type))
            return true;

        if (!(type instanceof ClassSymbol))
            return false;

        return isInstanceOf((ClassSymbol) type);
    }

    @Override
    public Scope getParentScope() {
        return superClass != null ? superClass : enclosingScope;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol symbol) {
        var alreadyDefinedSymbol = members.get(symbol.getSignature());

        if (alreadyDefinedSymbol != null)
            throw new SymbolSignatureAlreadyDefined(alreadyDefinedSymbol);

        members.put(symbol.getSignature(), symbol);
    }

    @Override
    public Symbol resolve(SymbolSignature signature) {
        var symbol = members.get(signature);

        if (symbol != null)
            return symbol;

        var parentScope = getParentScope();

        return parentScope == null ? null : parentScope.resolve(signature);
    }

    @Override
    public String toString() {
        var superClassText = superClass == null ? "" : " extends " + superClass.getName();

        return "class " + getName() + superClassText + " " + Scope.toString(Map.of(), List.of());
    }

    public ClassSymbol getSuperClass() {
        return superClass;
    }
}
