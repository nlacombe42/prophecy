package net.nlacombe.prophecy.symboltable.domain;

import net.nlacombe.prophecy.algorithm.MostSpecificCommonType;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import org.apache.commons.lang3.StringUtils;

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

    static boolean sameType(Type left, Type right) {
        if (left == right)
            return true;

        if (left == null || right == null)
            return false;

        if (left instanceof NamedParameterType || right instanceof NamedParameterType)
            return false;

        var leftClass = (ClassSymbol) left;
        var rightClass = (ClassSymbol) right;

        return StringUtils.equals(leftClass.getFullyQualifiedName(), rightClass.getFullyQualifiedName());
    }
}
