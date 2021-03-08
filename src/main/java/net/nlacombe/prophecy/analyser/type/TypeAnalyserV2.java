package net.nlacombe.prophecy.analyser.type;

import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.reporting.BuildMessageService;

public class TypeAnalyserV2 {

    public void evaluateTypesAndResolveCalls(ProphecyAstNode astRoot, BuildMessageService buildMessageService) {
        new TypeAnalyserAstVisitor(buildMessageService).visit(astRoot);
    }
}
