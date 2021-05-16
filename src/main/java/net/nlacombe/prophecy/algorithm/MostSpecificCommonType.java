package net.nlacombe.prophecy.algorithm;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.symboltable.domain.NamedParameterType;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;

import java.util.HashSet;
import java.util.Set;

public class MostSpecificCommonType {

    public static Type getMostSpecificCommonType(Type left, Type right) {
        if (left.equals(right))
            return left;

        if (left instanceof NamedParameterType || right instanceof NamedParameterType)
            return BootstrapTypeSymbols.getInstance().getObjectClass();

        validateIsClassSymbol(left);
        validateIsClassSymbol(right);

        var leftTypeWithScores = getTypeWithScores((ClassSymbol) left);
        var rightTypeWithScores = getTypeWithScores((ClassSymbol) right);

        return leftTypeWithScores.stream()
            .filter(leftTypeWithScore -> getTypeWithScoreWithClassOrNull(rightTypeWithScores, leftTypeWithScore.getClassSymbol()) != null)
            .map(leftTypeWithScore -> {
                var sameClassTypeFromRightTypeHierarchy = getTypeWithScoreWithClassOrNull(rightTypeWithScores, leftTypeWithScore.getClassSymbol());

                return getTypeWithScoreWithLowestScore(leftTypeWithScore, sameClassTypeFromRightTypeHierarchy);
            })
            .reduce(MostSpecificCommonType::getTypeWithScoreWithLowestScore)
            .orElseThrow(() -> new ProphecyCompilerException("All classes must derive from the Object class."))
            .getClassSymbol();
    }

    private static TypeWithScore getTypeWithScoreWithLowestScore(TypeWithScore left, TypeWithScore right) {
        return left.getScore() < right.getScore() ? left : right;
    }

    private static TypeWithScore getTypeWithScoreWithClassOrNull(Set<TypeWithScore> typeWithScores, ClassSymbol classSymbol) {
        return typeWithScores.stream()
            .filter(typeWithScore -> classSymbol.equals(typeWithScore.getClassSymbol()))
            .findAny()
            .orElse(null);
    }

    private static Set<TypeWithScore> getTypeWithScores(ClassSymbol classSymbol) {
        var typeWithScores = new HashSet<TypeWithScore>();
        var currentClass = classSymbol;
        var currentScore = 0;

        while (currentClass != null) {
            typeWithScores.add(new TypeWithScore(currentClass, currentScore));
            currentScore++;
            currentClass = currentClass.getSuperClass();
        }

        return typeWithScores;
    }

    private static void validateIsClassSymbol(Type type) {
        if (!(type instanceof ClassSymbol))
            throw new ProphecyCompilerException("determination of common type not implemented for this kind of type: " + type.getClass().getSimpleName());
    }
}

class TypeWithScore {

    private final ClassSymbol classSymbol;
    private final int score;

    public TypeWithScore(ClassSymbol classSymbol, int score) {
        this.classSymbol = classSymbol;
        this.score = score;
    }

    @Override
    public String toString() {
        return score + " " + classSymbol.toString();
    }

    public ClassSymbol getClassSymbol() {
        return classSymbol;
    }

    public int getScore() {
        return score;
    }
}
