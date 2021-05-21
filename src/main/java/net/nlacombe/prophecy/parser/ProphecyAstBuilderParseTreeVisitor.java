package net.nlacombe.prophecy.parser;

import net.nlacombe.prophecy.ast.node.ProphecyArrayLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallSelectionExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIdentifierExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIntegerLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyStringLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyVariableDeclarationAstNode;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.parser.antlr4.ProphecyBaseVisitor;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.reporting.SourceCodeLocation;
import net.nlacombe.prophecy.util.CollectionUtil;
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
    public List<ProphecyAstNode> visitSelectionCallExpression(ProphecyParser.SelectionCallExpressionContext selectionCallExpressionContext) {
        var sourceCodeLocation = getSourceCodeLocation(selectionCallExpressionContext);
        var expressionChildren = visit(selectionCallExpressionContext.expression());
        var expression = (ProphecyExpressionAstNode) expressionChildren.get(0);
        var call = (ProphecyCallAstNode) visitCall(selectionCallExpressionContext.call()).get(0);

        return List.of(new ProphecyCallSelectionExpressionAstNode(sourceCodeLocation, expression, call));
    }

    @Override
    public List<ProphecyAstNode> visitIdentifierExpression(ProphecyParser.IdentifierExpressionContext identifierExpressionContext) {
        var sourceCodeLocation = getSourceCodeLocation(identifierExpressionContext);

        return List.of(new ProphecyIdentifierExpressionAstNode(sourceCodeLocation, identifierExpressionContext.identifier.getText()));
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
    public List<ProphecyAstNode> visitArrayLiteral(ProphecyParser.ArrayLiteralContext arrayLiteralContext) {
        var sourceCodeLocation = getSourceCodeLocation(arrayLiteralContext);

        var elementNodes = visitChildren(arrayLiteralContext);

        validateArrayElementsAreExpressions(sourceCodeLocation, elementNodes);

        var expressionElementsNodes = CollectionUtil.castToSpecificWarn(elementNodes, ProphecyExpressionAstNode.class);

        return List.of(new ProphecyArrayLiteralAstNode(sourceCodeLocation, expressionElementsNodes));
    }

    @Override
    public List<ProphecyAstNode> visitVariableDeclaration(ProphecyParser.VariableDeclarationContext variableDeclarationContext) {
        var sourceCodeLocation = getSourceCodeLocation(variableDeclarationContext);

        var initializerNode = visit(variableDeclarationContext.initializer);
        var initializerExpression = getOneExpressionNode(initializerNode);

        return List.of(new ProphecyVariableDeclarationAstNode(sourceCodeLocation, variableDeclarationContext.variableName.getText(), initializerExpression));
    }

    @Override
    protected List<ProphecyAstNode> aggregateResult(List<ProphecyAstNode> aggregate, List<ProphecyAstNode> nextResult) {
        return ListUtils.union(aggregate, nextResult);
    }

    @Override
    protected List<ProphecyAstNode> defaultResult() {
        return List.of();
    }

    private ProphecyExpressionAstNode getOneExpressionNode(List<ProphecyAstNode> nodes) {
        if (nodes.size() != 1 || !(nodes.get(0) instanceof ProphecyExpressionAstNode))
            throw new ProphecyCompilerException("expected exactly 1 expression node: " + nodes);

        return (ProphecyExpressionAstNode) nodes.get(0);
    }

    private void validateArrayElementsAreExpressions(SourceCodeLocation sourceCodeLocation, List<ProphecyAstNode> nodes) {
        var nonExpressionNodes = getNonExpressionNodes(nodes);

        if (!nonExpressionNodes.isEmpty())
            throw new ProphecyCompilerException("Error: Array literal at " + sourceCodeLocation + " contains the following elements which are not expressions: " + nonExpressionNodes);
    }

    private void validateArgumentsAreExpressions(SourceCodeLocation sourceCodeLocation, List<ProphecyAstNode> nodes) {
        var nonExpressionNodes = getNonExpressionNodes(nodes);

        if (!nonExpressionNodes.isEmpty())
            throw new ProphecyCompilerException("Error: Call at " + sourceCodeLocation + " contains the following arguments which are not expressions: " + nonExpressionNodes);
    }

    private List<ProphecyAstNode> getNonExpressionNodes(List<ProphecyAstNode> nodes) {
        return nodes.stream()
            .filter(node -> !(node instanceof ProphecyExpressionAstNode))
            .collect(Collectors.toList());
    }

    private SourceCodeLocation getSourceCodeLocation(ParserRuleContext parserRuleContext) {
        var firstToken = parserRuleContext.start;
        var lastToken = parserRuleContext.stop;

        if (lastToken == null)
            return SourceCodeLocation.fromPosition(filePath, firstToken.getLine(), firstToken.getCharPositionInLine() + 1);
        else
            return SourceCodeLocation.fromRange(filePath,
                firstToken.getLine(), firstToken.getCharPositionInLine() + 1,
                lastToken.getLine(), lastToken.getCharPositionInLine() + lastToken.getText().length());
    }
}
