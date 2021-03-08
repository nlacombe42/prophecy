package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;

public abstract class Symbol {

    private final String name;
    private Type type;
    private Scope scope;
    private ProphecyAstNode definitionAstNode;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, Type type) {
        this(name);
        this.type = type;
    }

    abstract public SymbolSignature getSignature();

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Scope getScope() {
        return scope;
    }

    public ProphecyAstNode getDefinitionAstNode() {
        return definitionAstNode;
    }

    public void setDefinitionAstNode(ProphecyAstNode definitionAstNode) {
        this.definitionAstNode = definitionAstNode;
    }

    public String toString() {
        String s = "";

        if (type != null)
            return '<' + s + getName() + ":" + type.getName() + '>';

        return s + getName();
    }
}
