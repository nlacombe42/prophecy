package net.nlacombe.prophecy.v2.analyser.type;

import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;

public class TypeAnalyserV2 {

    public void evaluateTypesAndResolveCalls(ProphecyV2AstNode astRoot, ProphecyBuildListener buildListener) {
        new TypeAnalyserAstVisitor(buildListener).visit(astRoot);
    }
}
