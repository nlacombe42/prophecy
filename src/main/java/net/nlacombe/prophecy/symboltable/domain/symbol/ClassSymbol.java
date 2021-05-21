package net.nlacombe.prophecy.symboltable.domain.symbol;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.NamedParameterType;
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
    private final List<NamedParameterType> parameterTypes;
    private final Map<NamedParameterType, Type> parameterTypeSubstitutions;
    private final ClassSymbol unsubstitutedClass;

    protected ClassSymbol(
        String name,
        Scope enclosingScope,
        ClassSymbol superClass,
        List<NamedParameterType> parameterTypes,
        Map<NamedParameterType, Type> parameterTypeSubstitutions,
        ClassSymbol unsubstitutedClass
    ) {
        super(name);

        this.superClass = superClass;
        this.enclosingScope = enclosingScope;
        this.unsubstitutedClass = unsubstitutedClass;
        this.parameterTypes = parameterTypes == null ? List.of() : parameterTypes;
        this.parameterTypeSubstitutions = parameterTypeSubstitutions == null ? Map.of() : parameterTypeSubstitutions;

        members = new LinkedHashMap<>();

        validateParameterTypeSubstitutions(parameterTypeSubstitutions);
        validateUnsubstitutedClass(parameterTypeSubstitutions, unsubstitutedClass);
    }

    public static ClassSymbol newFromClassDefinition(String name, ClassSymbol superClass, Scope enclosingScope, List<NamedParameterType> parameterTypes) {
        return new ClassSymbol(name, enclosingScope, superClass, parameterTypes, Map.of(), null);
    }

    public static ClassSymbol newFromClassDefinition(String name, ClassSymbol superClass, Scope enclosingScope) {
        return newFromClassDefinition(name, superClass, enclosingScope, List.of());
    }

    @Override
    public ClassSymbol substitute(Map<NamedParameterType, Type> parameterTypeSubstitutions) {
        var classSymbol = new ClassSymbol(getName(), getEnclosingScope(), getSuperClass(), getParameterTypes(), parameterTypeSubstitutions, this);

        members.forEach((symbolSignature, symbol) -> classSymbol.define(symbol.substitute(parameterTypeSubstitutions)));

        return classSymbol;
    }

    public boolean isSameOrSubclass(ClassSymbol classSymbol) {
        if (equals(classSymbol))
            return true;

        if (superClass == null)
            return false;

        return superClass.isSameOrSubclass(classSymbol);
    }

    public boolean isSameOrUnsubstitutedIsSameAs(ClassSymbol classSymbol) {
        return equals(classSymbol) || classSymbol.getUnsubstitutedClass() != null && classSymbol.getUnsubstitutedClass().equals(classSymbol);
    }

    public ClassSymbol getUnsubstitutedClass() {
        if (parameterTypeSubstitutions.isEmpty())
            return this;

        return unsubstitutedClass;
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

        return isSameOrSubclass((ClassSymbol) type);
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
        var superClassText = superClass == null ? "" : "extends " + superClass.getName();

        return "class " + getNameWithParameterTypesOrSubstitution() + " " + superClassText + " " + Scope.toString(Map.of(), List.of());
    }

    @Override
    public String getNameWithParameterTypesOrSubstitution() {
        var parameterTypesOrSubstitutionWitBrackets = parameterTypes.isEmpty() ? "" : "<" + getParameterTypesOrSubstitutionListText() + ">";

        return getName() + parameterTypesOrSubstitutionWitBrackets;
    }

    public List<Type> getSubstitutedParameterTypes() {
        return parameterTypes.stream()
            .map(parameterType -> parameterType.substitute(parameterTypeSubstitutions))
            .collect(Collectors.toList());
    }

    public String getFullyQualifiedName() {
        return getNameWithParameterTypesOrSubstitution();
    }

    public ClassSymbol getSuperClass() {
        return superClass;
    }

    public List<NamedParameterType> getParameterTypes() {
        return parameterTypes;
    }

    private String getParameterTypesOrSubstitutionListText() {
        return getSubstitutedParameterTypes().stream()
            .map(Type::getName)
            .collect(Collectors.joining(","));
    }

    private void validateParameterTypeSubstitutions(Map<NamedParameterType, Type> parameterTypeSubstitutions) {
        if (parameterTypeSubstitutions.isEmpty())
            return;

        var invalidSubstitutionException = new IllegalArgumentException("there must be a 1 to 1 mapping of named parameter type to their substitution");

        if (getParameterTypes().size() != parameterTypeSubstitutions.size())
            throw invalidSubstitutionException;

        var missingSubstitutions = getParameterTypes().stream()
            .anyMatch(namedParameterType -> !parameterTypeSubstitutions.containsKey(namedParameterType));

        if (missingSubstitutions)
            throw invalidSubstitutionException;
    }

    private void validateUnsubstitutedClass(Map<NamedParameterType, Type> parameterTypeSubstitutions, ClassSymbol unsubstitutedClass) {
        if (!parameterTypeSubstitutions.isEmpty() && unsubstitutedClass == null)
            throw new ProphecyCompilerException("must pass unsubstitutedClass when creating a class with substitution");

        if (parameterTypeSubstitutions.isEmpty() && unsubstitutedClass != null)
            throw new ProphecyCompilerException("must not pass unsubstitutedClass when creating a class without substitution");
    }
}
