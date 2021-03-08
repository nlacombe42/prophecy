package net.nlacombe.prophecy.ast.node;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.parser.StringLiteralUtil;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;

import java.util.List;

public class ProphecyV2StringLiteralAstNode extends AbstractProphecyV2ExpressionAstNode {

    private final String sourceText;

    public ProphecyV2StringLiteralAstNode(SourceCodeLocation definitionSourceCodeLocation, String sourceText) {
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
    public List<ProphecyV2AstNode> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return "\"" + getSourceText() + "\"";
    }
}
