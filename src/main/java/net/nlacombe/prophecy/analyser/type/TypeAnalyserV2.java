package net.nlacombe.prophecy.analyser.type;

import net.nlacombe.prophecy.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.reporting.BuildMessageService;

public class TypeAnalyserV2 {

    public void evaluateTypesAndResolveCalls(ProphecyV2AstNode astRoot, BuildMessageService buildMessageService) {
        new TypeAnalyserAstVisitor(buildMessageService).visit(astRoot);
    }
}
