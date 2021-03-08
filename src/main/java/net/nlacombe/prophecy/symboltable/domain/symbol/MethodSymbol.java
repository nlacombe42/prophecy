package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.signature.SymbolSignature;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MethodSymbol extends Symbol implements Scope {

    private final boolean isStatic;
    private final Map<SymbolSignature, Symbol> orderedParameters;
    private final LocalScope methodBodyScope;
    private final ClassSymbol parentClass;
    private final GlobalScope globalScope;

    private MethodSymbol(String methodName, Type returnType, ClassSymbol parentClass, GlobalScope globalScope, boolean isStatic) {
        super(methodName, returnType);

        if (parentClass != null && globalScope != null)
            throw new ProphecyCompilerException("a method is either global or defined in a class");

        this.parentClass = parentClass;
        this.globalScope = globalScope;
        this.isStatic = isStatic;

        orderedParameters = new LinkedHashMap<>();
        methodBodyScope = new LocalScope(null);
    }

    public static MethodSymbol newClassMethod(String methodName, Type returnType, ClassSymbol parentClass, boolean isStatic) {
        return new MethodSymbol(methodName, returnType, parentClass, null, isStatic);
    }

    public static MethodSymbol newGlobalMethod(String methodName, Type returnType, GlobalScope globalScope) {
        return new MethodSymbol(methodName, returnType, null, globalScope, true);
    }

    @Override
    public Symbol resolve(SymbolSignature signature) {
        var symbol = getParameter(signature);

        if (symbol != null)
            return symbol;

        if (getParentScope() != null)
            return getParentScope().resolve(signature);

        return null;
    }

    @Override
    public Symbol define(Symbol symbol) {
        var previouslyDefined = getParameter(symbol.getSignature());

        putParameter(symbol);

        symbol.setScope(this);

        return previouslyDefined;
    }

    @Override
    public List<Scope> getChildrenScopes() {
        return List.of(methodBodyScope);
    }

    @Override
    public Scope getParentScope() {
        return parentClass != null ? parentClass : globalScope;
    }

    @Override
    public Scope getEnclosingScope() {
        return getParentScope();
    }

    public ClassSymbol getParentClass() {
        return parentClass;
    }

    @Override
    public MethodSignature getSignature() {
        var parameterTypes = orderedParameters.values().stream()
            .map(Symbol::getType)
            .collect(Collectors.toList());

        return new MethodSignature(getMethodName(), parameterTypes);
    }

    public String toString() {
        var parametersText = orderedParameters.values().stream()
            .map(Symbol::toString)
            .collect(Collectors.joining(", "));
        var scopeText = methodBodyScope.getSymbols().isEmpty() ? " {}" : "\n{\n" + methodBodyScope.toString().indent(4) + "}\n";

        return "" + getReturnType().getName() + " " + getMethodName() + "(" + parametersText + ")" + scopeText;
    }

    public List<Symbol> getParameters() {
        return new ArrayList<>(orderedParameters.values());
    }

    public Symbol getParameter(int index) {
        return getParameters().get(index);
    }

    public Symbol getParameter(SymbolSignature signature) {
        return orderedParameters.get(signature);
    }

    public void putParameter(Symbol symbol) {
        orderedParameters.put(symbol.getSignature(), symbol);
    }

    public String getMethodName() {
        return getName();
    }

    public Type getReturnType() {
        return getType();
    }

    public boolean isStatic() {
        return isStatic;
    }
}
