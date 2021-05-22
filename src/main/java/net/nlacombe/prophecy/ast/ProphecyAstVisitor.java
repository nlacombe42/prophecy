package net.nlacombe.prophecy.ast;

import net.nlacombe.prophecy.ast.node.ProphecyArrayLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIdentifierExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyVariableDeclarationAstNode;
import net.nlacombe.prophecy.util.AbstractVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class ProphecyAstVisitor<ResultType> extends AbstractVisitor<ProphecyAstNode, ResultType> {

    private final Map<Class<? extends ProphecyAstNode>, Function<ProphecyAstNode, ResultType>> astHandlersByNodeType;

    public ProphecyAstVisitor() {
        astHandlersByNodeType = getAstHandlersByNodeType();
    }

    @Override
    public ResultType visit(ProphecyAstNode node) {
        enterEveryAstNode(node);

        var handler = astHandlersByNodeType.get(node.getClass());

        return handler != null ? handler.apply(node) : defaultForNonImplementedNodeTypes(node);
    }

    @Override
    protected List<ProphecyAstNode> getChildren(ProphecyAstNode node) {
        return node.getChildren();
    }

    protected abstract ResultType defaultForNonImplementedNodeTypes(ProphecyAstNode node);

    private Map<Class<? extends ProphecyAstNode>, Function<ProphecyAstNode, ResultType>> getAstHandlersByNodeType() {
        var handlersMap = new HashMap<Class<? extends ProphecyAstNode>, Function<ProphecyAstNode, ResultType>>();

        handlersMap.put(ProphecyCallAstNode.class, node -> visitCallAstNode((ProphecyCallAstNode) node));
        handlersMap.put(ProphecyFileAstNode.class, node -> visitFileAstNode((ProphecyFileAstNode) node));
        handlersMap.put(ProphecyArrayLiteralAstNode.class, node -> visitArrayLiteralAstNode((ProphecyArrayLiteralAstNode) node));
        handlersMap.put(ProphecyVariableDeclarationAstNode.class, node -> visitVariableDeclarationAstNode((ProphecyVariableDeclarationAstNode) node));
        handlersMap.put(ProphecyIdentifierExpressionAstNode.class, node -> visitProphecyIdentifierExpressionAstNode((ProphecyIdentifierExpressionAstNode) node));

        return handlersMap;
    }

    protected void enterEveryAstNode(ProphecyAstNode node) {
    }

    protected ResultType visitCallAstNode(ProphecyCallAstNode node) {
        return visitChildren(node);
    }

    protected ResultType visitFileAstNode(ProphecyFileAstNode node) {
        return visitChildren(node);
    }

    protected ResultType visitArrayLiteralAstNode(ProphecyArrayLiteralAstNode node) {
        return visitChildren(node);
    }

    protected ResultType visitVariableDeclarationAstNode(ProphecyVariableDeclarationAstNode node) {
        return visitChildren(node);
    }

    protected ResultType visitProphecyIdentifierExpressionAstNode(ProphecyIdentifierExpressionAstNode node) {
        return visitChildren(node);
    }
}
