package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.parser.StringLiteralUtil;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyStringLiteralAstNode extends AbstractProphecyExpressionAstNode {

    private final String sourceText;

    public ProphecyStringLiteralAstNode(SourceCodeLocation definitionSourceCodeLocation, String sourceText) {
        super(definitionSourceCodeLocation, BootstrapTypeSymbols.getInstance().getStringClass());

        this.sourceText = sourceText;
    }

    public String getStringValue() {
        return StringLiteralUtil.getStringValue(sourceText);
    }

    public String getSourceText() {
        return sourceText;
    }

    @Override
    public List<ProphecyAstNode> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return "\"" + getSourceText() + "\"";
    }
}
