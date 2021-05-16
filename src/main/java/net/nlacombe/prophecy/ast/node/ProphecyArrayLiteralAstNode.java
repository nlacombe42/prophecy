package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.util.CollectionUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProphecyArrayLiteralAstNode extends AbstractProphecyExpressionAstNode {

    private final List<ProphecyExpressionAstNode> elements;

    public ProphecyArrayLiteralAstNode(SourceCodeLocation definitionSourceCodeLocation, List<ProphecyExpressionAstNode> elements) {
        super(definitionSourceCodeLocation);

        this.elements = elements;
    }

    public List<ProphecyExpressionAstNode> getElements() {
        return elements;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return CollectionUtil.castToGeneric(elements, ProphecyAstNode.class);
    }

    public void setArrayType(ClassSymbol arrayType) {
        if (!arrayType.isSameOrUnsubstitutedIsSameAs(BootstrapTypeSymbols.getInstance().getArrayClass()))
            throw new IllegalArgumentException("Array type must be an instance of the array class type: " + arrayType);

        this.evaluatedType = arrayType;
    }

    @Override
    public String toString() {
        var elementsText = getElements().stream()
            .map(Objects::toString)
            .collect(Collectors.joining(", "));

        return "[" + elementsText + "]";
    }
}
