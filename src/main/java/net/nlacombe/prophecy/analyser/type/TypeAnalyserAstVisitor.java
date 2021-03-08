package net.nlacombe.prophecy.analyser.type;

import net.nlacombe.prophecy.symboltable.domain.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.ast.ProphecyV2AstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyV2AstNode;
import net.nlacombe.prophecy.ast.node.ProphecyV2CallAstNode;
import net.nlacombe.prophecy.reporting.BuildMessageService;

public class TypeAnalyserAstVisitor extends ProphecyV2AstVisitor<Type> {

    private final BuildMessageService buildMessageService;

    public TypeAnalyserAstVisitor(BuildMessageService buildMessageService) {
        this.buildMessageService = buildMessageService;
    }

    @Override
    protected Type visitCallAstNode(ProphecyV2CallAstNode node) {
        visitChildren(node);

        var methodSignature = new MethodSignature(node.getMethodName());
        node.getArguments().forEach(argumentNode -> methodSignature.addParameter(argumentNode.getEvaluatedType()));

        var methodCalled = node.getEnclosingScope().resolve(methodSignature);

        if (methodCalled == null) {
            var sourceCodeLocation = node.getDefinitionSourceCodeLocation();

            buildMessageService.error(sourceCodeLocation, "No method with signature found: " + methodSignature);

            return null;
        }

        node.setMethodSymbol((MethodSymbol) methodCalled);

        node.setEvaluatedType(methodCalled.getType());

        return methodCalled.getType();
    }

    @Override
    protected Type defaultForNonImplementedNodeTypes(ProphecyV2AstNode node) {
        return null;
    }
}
