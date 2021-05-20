package net.nlacombe.prophecy.symboltable.domain;

import net.nlacombe.prophecy.algorithm.MostSpecificCommonType;

import java.util.Map;

public interface Type {

    String getName();

    String getNameWithParameterTypesOrSubstitution();

    boolean canAssignTo(Type type);

    Type substitute(Map<NamedParameterType, Type> parameterTypeSubstitutions);

    static Type getSubstituteOrOriginal(Type original, Map<NamedParameterType, Type> parameterTypeSubstitutions) {
        return original instanceof NamedParameterType && parameterTypeSubstitutions.containsKey(original) ?
            parameterTypeSubstitutions.get(original)
            :
            original;
    }

    static Type getMostSpecificCommonType(Type left, Type right) {
        return MostSpecificCommonType.getMostSpecificCommonType(left, right);
    }
}
