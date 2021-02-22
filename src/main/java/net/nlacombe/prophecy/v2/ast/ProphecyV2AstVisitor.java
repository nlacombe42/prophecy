package net.nlacombe.prophecy.v2.ast;

import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2CallAstNode;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2FileAstNode;
import net.nlacombe.prophecy.v2.util.AbstractVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class ProphecyV2AstVisitor<ResultType> extends AbstractVisitor<ProphecyV2AstNode, ResultType> {

    private final Map<Class<? extends ProphecyV2AstNode>, Function<ProphecyV2AstNode, ResultType>> astHandlersByNodeType;

    public ProphecyV2AstVisitor() {
        astHandlersByNodeType = getAstHandlersByNodeType();
    }

    @Override
    public ResultType visit(ProphecyV2AstNode node) {
        enterEveryAstNode(node);

        var handler = astHandlersByNodeType.get(node.getClass());

        return handler != null ? handler.apply(node) : defaultForNonImplementedNodeTypes(node);
    }

    @Override
    protected List<ProphecyV2AstNode> getChildren(ProphecyV2AstNode node) {
        return node.getChildren();
    }

    protected abstract ResultType defaultForNonImplementedNodeTypes(ProphecyV2AstNode node);

    private Map<Class<? extends ProphecyV2AstNode>, Function<ProphecyV2AstNode, ResultType>> getAstHandlersByNodeType() {
        var handlersMap = new HashMap<Class<? extends ProphecyV2AstNode>, Function<ProphecyV2AstNode, ResultType>>();

        handlersMap.put(ProphecyV2CallAstNode.class, node -> visitCallAstNode((ProphecyV2CallAstNode) node));
        handlersMap.put(ProphecyV2FileAstNode.class, node -> visitFileAstNode((ProphecyV2FileAstNode) node));

        return handlersMap;
    }

    protected void enterEveryAstNode(ProphecyV2AstNode node) {
    }

    protected ResultType visitCallAstNode(ProphecyV2CallAstNode node) {
        return visitChildren(node);
    }

    protected ResultType visitFileAstNode(ProphecyV2FileAstNode node) {
        return visitChildren(node);
    }
}
