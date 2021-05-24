package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.NamedParameterType;
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
    private final LinkedHashMap<SymbolSignature, VariableSymbol> parameters;
    private final LocalScope methodBodyScope;
    private final ClassSymbol parentClass;
    private final GlobalScope globalScope;

    private MethodSymbol(
        String methodName,
        Type returnType,
        ClassSymbol parentClass,
        GlobalScope globalScope,
        boolean isStatic,
        List<VariableSymbol> parameters
    ) {
        super(methodName, returnType);

        if (parentClass != null && globalScope != null)
            throw new ProphecyCompilerException("a method is either global or defined in a class");

        this.parentClass = parentClass;
        this.globalScope = globalScope;
        this.isStatic = isStatic;

        this.parameters = new LinkedHashMap<>();
        parameters.forEach(this::addParameter);

        methodBodyScope = new LocalScope(this);
    }

    public static MethodSymbol newClassMethod(String methodName, Type returnType, ClassSymbol parentClass,
                                              boolean isStatic, List<VariableSymbol> parameters) {
        return new MethodSymbol(methodName, returnType, parentClass, null, isStatic, parameters);
    }

    public static MethodSymbol newGlobalMethod(String methodName, Type returnType, GlobalScope globalScope,
                                               List<VariableSymbol> parameters) {
        return new MethodSymbol(methodName, returnType, null, globalScope, true, parameters);
    }

    @Override
    public MethodSymbol substitute(Map<NamedParameterType, Type> parameterTypeSubstitutions) {
        var substituteReturnType = Type.getSubstituteOrOriginal(getReturnType(), parameterTypeSubstitutions);
        var substituteParameters = getParameters().stream()
            .map(parameter -> parameter.substitute(parameterTypeSubstitutions))
            .collect(Collectors.toList());

        return new MethodSymbol(getMethodName(), substituteReturnType, getParentClass(), this.globalScope, isStatic(), substituteParameters);
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
    public void define(Symbol symbol) {
        throw new ProphecyCompilerException("add parameters through specialized methods");
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

    public LocalScope getMethodBodyScope() {
        return methodBodyScope;
    }

    public ClassSymbol getParentClass() {
        return parentClass;
    }

    @Override
    public MethodSignature getSignature() {
        var parameterTypes = parameters.values().stream()
            .map(Symbol::getType)
            .collect(Collectors.toList());

        return new MethodSignature(getMethodName(), parameterTypes);
    }

    public String toString() {
        var parametersText = parameters.values().stream()
            .map(Symbol::toString)
            .collect(Collectors.joining(", "));
        var scopeText = methodBodyScope.getSymbols().isEmpty() ? " {}" : "\n{\n" + methodBodyScope.toString().indent(4) + "}\n";

        return "" + getReturnType().getName() + " " + getMethodName() + "(" + parametersText + ")" + scopeText;
    }

    public List<VariableSymbol> getParameters() {
        return new ArrayList<>(parameters.values());
    }

    public Symbol getParameter(int index) {
        return getParameters().get(index);
    }

    public Symbol getParameter(SymbolSignature signature) {
        return parameters.get(signature);
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

    private void addParameter(VariableSymbol symbol) {
        var alreadyExistingParameter = parameters.get(symbol.getSignature());

        if (alreadyExistingParameter != null)
            throw new ProphecyCompilerException("a parameter with the same signature already exists: " + alreadyExistingParameter + ", when trying to add: " + symbol);

        parameters.put(symbol.getSignature(), symbol);
    }
}
