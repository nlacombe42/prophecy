package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Lexer;
import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Parser;
import net.nlacombe.prophecy.shared.parser.ProphecyBuildListenerConsoleErrorListener;
import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;
import net.nlacombe.prophecy.v2.Slf4jProphecyBuildListener;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2BinaryOperatorTreeNode;
import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2CallTreeNode;
import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2GenericTreeNode;
import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2IntegerLiteralTreeNode;
import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2TreeNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

public class ProphecyV2TreeBuilder {

    private final InputStream input;
    private final ProphecyBuildListener listener;

    public ProphecyV2TreeBuilder(InputStream inputStream, ProphecyBuildListener prophecyBuildListener) {
        this.input = inputStream;
        this.listener = prophecyBuildListener;
    }

    public ProphecyV2TreeBuilder(InputStream inputStream) {
        this(inputStream, new Slf4jProphecyBuildListener());
    }

    public ProphecyV2TreeNode parse() {
        var parser = getParser(input, listener);

        var fileContext = parser.file();

        if (parser.getNumberOfSyntaxErrors() > 0)
            throw new ProphecyCompilerException("Syntax errors found; aborting build.");

        return getNode(fileContext);
    }

    private ProphecyV2Parser getParser(InputStream input, ProphecyBuildListener listener) {
        try {
            var charStreams = CharStreams.fromStream(input);
            var lexer = new ProphecyV2Lexer(charStreams);
            var tokens = new CommonTokenStream(lexer);

            var parser = new ProphecyV2Parser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new ProphecyBuildListenerConsoleErrorListener(listener));

            return parser;
        } catch (IOException e) {
            throw new ProphecyCompilerException(e);
        }
    }

    private ProphecyV2GenericTreeNode getNode(ProphecyV2Parser.FileContext fileContext) {
        var children = fileContext.statement().stream()
            .map(this::getNode)
            .collect(Collectors.toList());

        return new ProphecyV2GenericTreeNode(fileContext.getClass().getName(), children);
    }

    private ProphecyV2TreeNode getNode(ProphecyV2Parser.StatementContext statementContext) {
        return getNode(statementContext.call());
    }

    private ProphecyV2CallTreeNode getNode(ProphecyV2Parser.CallContext callContext) {
        var arguments = callContext.arguments.expression().stream()
            .map(this::getNode)
            .collect(Collectors.toList());

        return new ProphecyV2CallTreeNode(callContext.methodName.getText(), arguments);
    }

    private ProphecyV2TreeNode getNode(ProphecyV2Parser.ExpressionContext expressionContext) {
        if (expressionContext instanceof ProphecyV2Parser.MultiplicationExpressionContext) {
            return getNode((ProphecyV2Parser.MultiplicationExpressionContext) expressionContext);
        } else if (expressionContext instanceof ProphecyV2Parser.AdditionExpressionContext) {
            return getNode((ProphecyV2Parser.AdditionExpressionContext) expressionContext);
        } else if (expressionContext instanceof ProphecyV2Parser.LiteralExpressionContext) {
            return getNode((ProphecyV2Parser.LiteralExpressionContext) expressionContext);
        } else {
            throw new RuntimeException("Unimplemented expression type: " + expressionContext.getClass().getSimpleName());
        }
    }

    private ProphecyV2BinaryOperatorTreeNode getNode(ProphecyV2Parser.MultiplicationExpressionContext multiplicationExpressionContext) {
        var operationType = StringUtils.equals(multiplicationExpressionContext.operator.getText(), "*") ?
            ProphecyV2BinaryOperatorTreeNode.OperationType.MULTIPLICATION
            :
            ProphecyV2BinaryOperatorTreeNode.OperationType.DIVISION;


        return new ProphecyV2BinaryOperatorTreeNode(
            operationType,
            getNode(multiplicationExpressionContext.left),
            getNode(multiplicationExpressionContext.right)
        );
    }

    private ProphecyV2BinaryOperatorTreeNode getNode(ProphecyV2Parser.AdditionExpressionContext additionExpressionContext) {
        var operationType = StringUtils.equals(additionExpressionContext.operator.getText(), "+") ?
            ProphecyV2BinaryOperatorTreeNode.OperationType.ADDITION
            :
            ProphecyV2BinaryOperatorTreeNode.OperationType.SUBTRACTION;


        return new ProphecyV2BinaryOperatorTreeNode(
            operationType,
            getNode(additionExpressionContext.left),
            getNode(additionExpressionContext.right)
        );
    }

    private ProphecyV2TreeNode getNode(ProphecyV2Parser.LiteralExpressionContext literalExpressionContext) {
        return new ProphecyV2IntegerLiteralTreeNode(literalExpressionContext.literal().getText());
    }
}
