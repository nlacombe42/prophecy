package net.nlacombe.prophecy.v2.analyser.type;

import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.v2.reporting.BuildMessageService;

public class TypeAnalyserV2 {

    public void evaluateTypesAndResolveCalls(ProphecyV2AstNode astRoot, BuildMessageService buildMessageService) {
        new TypeAnalyserAstVisitor(buildMessageService).visit(astRoot);
    }
}
