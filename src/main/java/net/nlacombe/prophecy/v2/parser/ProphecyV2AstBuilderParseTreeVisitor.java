package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyV2BaseVisitor;
import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Parser;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2CallAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2ExpressionAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2FileAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2IntegerLiteralAstNode;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.v2.reporting.SourceCodeLocation;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ProphecyV2AstBuilderParseTreeVisitor extends ProphecyV2BaseVisitor<List<ProphecyV2AstNode>> {

    @Override
    public List<ProphecyV2AstNode> visitFile(ProphecyV2Parser.FileContext fileContext) {
        return List.of(new ProphecyV2FileAstNode(visitChildren(fileContext)));
    }

    @Override
    public List<ProphecyV2AstNode> visitCall(ProphecyV2Parser.CallContext callContext) {
        var sourceCodeLocation = getSourceCodeLocation(callContext);

        var argumentNodes = visitChildren(callContext);

        validateArgumentsAreExpressions(sourceCodeLocation, argumentNodes);

        var expressionArgumentNodes = argumentNodes.stream()
            .map(node -> (ProphecyV2ExpressionAstNode)node)
            .collect(Collectors.toList());

        return List.of(new ProphecyV2CallAstNode(sourceCodeLocation, callContext.methodName.getText(), expressionArgumentNodes));
    }

    @Override
    public List<ProphecyV2AstNode> visitIntegerLiteral(ProphecyV2Parser.IntegerLiteralContext integerLiteralContext) {
        var sourceCodeLocation = getSourceCodeLocation(integerLiteralContext);

        return List.of(new ProphecyV2IntegerLiteralAstNode(sourceCodeLocation, integerLiteralContext.getText()));
    }

    @Override
    protected List<ProphecyV2AstNode> aggregateResult(List<ProphecyV2AstNode> aggregate, List<ProphecyV2AstNode> nextResult) {
        return ListUtils.union(aggregate, nextResult);
    }

    @Override
    protected List<ProphecyV2AstNode> defaultResult() {
        return List.of();
    }

    private void validateArgumentsAreExpressions(SourceCodeLocation sourceCodeLocation, List<ProphecyV2AstNode> argumentNodes) {
        var nonExpressionArgumentNodes = argumentNodes.stream()
            .filter(argumentNode -> !(argumentNode instanceof ProphecyV2ExpressionAstNode))
            .collect(Collectors.toList());

        if (!nonExpressionArgumentNodes.isEmpty())
            throw new ProphecyCompilerException("Error: Call at " + sourceCodeLocation + " contains the following arguments which are not expressions: " + nonExpressionArgumentNodes);
    }

    private SourceCodeLocation getSourceCodeLocation(ParserRuleContext parserRuleContext) {
        var start = parserRuleContext.start;

        return new SourceCodeLocation(null, start.getLine(), start.getCharPositionInLine());
    }
}
