package net.nlacombe.prophecy.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyV2BaseVisitor;
import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Parser;
import net.nlacombe.prophecy.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.ast.node.ProphecyV2CallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyV2ExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyV2FileAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyV2IntegerLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyV2StringLiteralAstNode;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.collections4.ListUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ProphecyV2AstBuilderParseTreeVisitor extends ProphecyV2BaseVisitor<List<ProphecyV2AstNode>> {

    private final Path filePath;
    private final BuildMessageService buildMessageService;

    public ProphecyV2AstBuilderParseTreeVisitor(Path filePath, BuildMessageService buildMessageService) {
        this.filePath = filePath;
        this.buildMessageService = buildMessageService;
    }

    @Override
    public List<ProphecyV2AstNode> visitFile(ProphecyV2Parser.FileContext fileContext) {
        return List.of(new ProphecyV2FileAstNode(getSourceCodeLocation(fileContext), visitChildren(fileContext)));
    }

    @Override
    public List<ProphecyV2AstNode> visitCall(ProphecyV2Parser.CallContext callContext) {
        var sourceCodeLocation = getSourceCodeLocation(callContext);

        var argumentNodes = visitChildren(callContext);

        validateArgumentsAreExpressions(sourceCodeLocation, argumentNodes);

        var expressionArgumentNodes = argumentNodes.stream()
            .map(node -> (ProphecyV2ExpressionAstNode) node)
            .collect(Collectors.toList());

        return List.of(new ProphecyV2CallAstNode(sourceCodeLocation, callContext.methodName.getText(), expressionArgumentNodes));
    }

    @Override
    public List<ProphecyV2AstNode> visitIntegerLiteral(ProphecyV2Parser.IntegerLiteralContext integerLiteralContext) {
        var sourceCodeLocation = getSourceCodeLocation(integerLiteralContext);
        var literalValue = Integer.parseInt(integerLiteralContext.getText());

        if (literalValue < 0 || literalValue > 255)
            buildMessageService.error(sourceCodeLocation, "integer literal out of bounds, must be [0-255]");

        return List.of(new ProphecyV2IntegerLiteralAstNode(sourceCodeLocation, literalValue));
    }

    @Override
    public List<ProphecyV2AstNode> visitStringLiteral(ProphecyV2Parser.StringLiteralContext stringLiteralContext) {
        var sourceCodeLocation = getSourceCodeLocation(stringLiteralContext);
        var stringSourceText = stringLiteralContext.getText().substring(1, stringLiteralContext.getText().length() - 1);

        if (stringSourceText.contains("${"))
            buildMessageService.error(sourceCodeLocation, "string interpolation not supported yet");

        try {
            StringLiteralUtil.getStringValue(stringSourceText);
        } catch (IllegalArgumentException e) {
            buildMessageService.error(sourceCodeLocation, e.getMessage());
        }

        return List.of(new ProphecyV2StringLiteralAstNode(sourceCodeLocation, stringSourceText));
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
        var firstToken = parserRuleContext.start;
        var lastToken = parserRuleContext.stop;

        return SourceCodeLocation.fromRange(filePath,
            firstToken.getLine(), firstToken.getCharPositionInLine() + 1,
            lastToken.getLine(), lastToken.getCharPositionInLine() + lastToken.getText().length());
    }
}
