package net.nlacombe.prophecy.util;

import java.util.Collection;
import java.util.List;

public abstract class AbstractVisitor<NodeType, ResultType> {

    public ResultType visitChildren(NodeType node) {
        return visitNodes(getChildren(node));
    }

    public ResultType visitNodes(Collection<NodeType> nodes) {
        return nodes.stream()
            .map(this::visit)
            .reduce(defaultResult(), this::aggregate);
    }

    protected ResultType aggregate(ResultType aggregate, ResultType result) {
        return result;
    }

    protected ResultType defaultResult() {
        return null;
    }

    protected boolean shouldVisit(NodeType node) {
        return true;
    }

    protected abstract List<NodeType> getChildren(NodeType node);

    public abstract ResultType visit(NodeType node);
}
