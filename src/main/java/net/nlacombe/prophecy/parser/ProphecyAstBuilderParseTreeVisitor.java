package net.nlacombe.prophecy.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyBaseVisitor;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIntegerLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyStringLiteralAstNode;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.collections4.ListUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ProphecyAstBuilderParseTreeVisitor extends ProphecyBaseVisitor<List<ProphecyAstNode>> {

    private final Path filePath;
    private final BuildMessageService buildMessageService;

    public ProphecyAstBuilderParseTreeVisitor(Path filePath, BuildMessageService buildMessageService) {
        this.filePath = filePath;
        this.buildMessageService = buildMessageService;
    }

    @Override
    public List<ProphecyAstNode> visitFile(ProphecyParser.FileContext fileContext) {
        return List.of(new ProphecyFileAstNode(getSourceCodeLocation(fileContext), visitChildren(fileContext)));
    }

    @Override
    public List<ProphecyAstNode> visitCall(ProphecyParser.CallContext callContext) {
        var sourceCodeLocation = getSourceCodeLocation(callContext);

        var argumentNodes = visitChildren(callContext);

        validateArgumentsAreExpressions(sourceCodeLocation, argumentNodes);

        var expressionArgumentNodes = argumentNodes.stream()
            .map(node -> (ProphecyExpressionAstNode) node)
            .collect(Collectors.toList());

        return List.of(new ProphecyCallAstNode(sourceCodeLocation, callContext.methodName.getText(), expressionArgumentNodes));
    }

    @Override
    public List<ProphecyAstNode> visitIntegerLiteral(ProphecyParser.IntegerLiteralContext integerLiteralContext) {
        var sourceCodeLocation = getSourceCodeLocation(integerLiteralContext);
        var literalValue = Integer.parseInt(integerLiteralContext.getText());

        if (literalValue < 0 || literalValue > 255)
            buildMessageService.error(sourceCodeLocation, "integer literal out of bounds, must be [0-255]");

        return List.of(new ProphecyIntegerLiteralAstNode(sourceCodeLocation, literalValue));
    }

    @Override
    public List<ProphecyAstNode> visitStringLiteral(ProphecyParser.StringLiteralContext stringLiteralContext) {
        var sourceCodeLocation = getSourceCodeLocation(stringLiteralContext);
        var stringSourceText = stringLiteralContext.getText().substring(1, stringLiteralContext.getText().length() - 1);

        if (stringSourceText.contains("${"))
            buildMessageService.error(sourceCodeLocation, "string interpolation not supported yet");

        try {
            StringLiteralUtil.getStringValue(stringSourceText);
        } catch (IllegalArgumentException e) {
            buildMessageService.error(sourceCodeLocation, e.getMessage());
        }

        return List.of(new ProphecyStringLiteralAstNode(sourceCodeLocation, stringSourceText));
    }

    @Override
    protected List<ProphecyAstNode> aggregateResult(List<ProphecyAstNode> aggregate, List<ProphecyAstNode> nextResult) {
        return ListUtils.union(aggregate, nextResult);
    }

    @Override
    protected List<ProphecyAstNode> defaultResult() {
        return List.of();
    }

    private void validateArgumentsAreExpressions(SourceCodeLocation sourceCodeLocation, List<ProphecyAstNode> argumentNodes) {
        var nonExpressionArgumentNodes = argumentNodes.stream()
            .filter(argumentNode -> !(argumentNode instanceof ProphecyExpressionAstNode))
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
