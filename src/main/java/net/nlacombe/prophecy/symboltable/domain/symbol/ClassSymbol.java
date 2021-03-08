package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.symboltable.domain.signature.NameOnlySymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;
import org.apache.commons.collections4.ListUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClassSymbol extends Symbol implements Scope, Type {

    private ClassSymbol superClass;
    private final Scope enclosingScope;
    private final Map<SymbolSignature, Symbol> members;

    public ClassSymbol(String name, Scope enclosingScope, ClassSymbol superClass) {
        super(name);

        this.superClass = superClass;
        this.enclosingScope = enclosingScope;

        members = new LinkedHashMap<>();
    }

    public List<ClassSymbol> getSuperClasses() {
        if (superClass == null)
            return List.of();

        return ListUtils.union(superClass.getSuperClasses(), List.of(superClass));
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
        return List.of();
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
    public Symbol define(Symbol symbol) {
        throw new ProphecyCompilerException("unimplemented");
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
        var text = new StringBuilder();

        if (superClass != null)
            text.append("<").append(superClass.getName()).append(">");

        text.append("class ").append(getName()).append("{ !unimplemented! }");

        return text.toString();
    }

    public ClassSymbol getSuperClass() {
        return superClass;
    }

    public void setSuperClass(ClassSymbol superClass) {
        this.superClass = superClass;
    }
}
