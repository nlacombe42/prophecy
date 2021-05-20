package net.nlacombe.prophecy.symboltable.domain;

import java.util.Map;

public class NamedParameterType implements Type {

    private final String name;

    public NamedParameterType(String name) {
        this.name = name;
    }

    @Override
    public boolean canAssignTo(Type type) {
        return false;
    }

    @Override
    public Type substitute(Map<NamedParameterType, Type> parameterTypeSubstitutions) {
        return Type.getSubstituteOrOriginal(this, parameterTypeSubstitutions);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getNameWithParameterTypesOrSubstitution() {
        return getName();
    }
}
