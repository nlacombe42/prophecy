package net.nlacombe.prophecy.analyser.type;

import net.nlacombe.prophecy.ast.ProphecyAstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyArrayLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallSelectionExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyVariableDeclarationAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TypeAnalyserAstVisitor extends ProphecyAstVisitor<Type> {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
    private static final ClassSymbol voidClass = bootstrapTypeSymbols.getVoidClass();

    private final BuildMessageService buildMessageService;

    public TypeAnalyserAstVisitor(BuildMessageService buildMessageService) {
        this.buildMessageService = buildMessageService;
    }

    @Override
    protected Type visitCallAstNode(ProphecyCallAstNode node) {
        visitChildren(node);

        var parameterTypes = node.getArguments().stream()
            .map(ProphecyExpressionAstNode::getEvaluatedType)
            .collect(Collectors.toList());

        if (parameterTypes.stream().anyMatch(Objects::isNull)) {
            return null;
        }

        var methodSignature = new MethodSignature(node.getMethodName(), parameterTypes);
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
    protected Type visitCallSelectionExpressionAstNode(ProphecyCallSelectionExpressionAstNode node) {
        visit(node.getSelectionExpression());

        var selectionExpressionNode = node.getSelectionExpression();

        if (selectionExpressionNode.getEvaluatedType() == null)
            return null;

        var selectionExpressionClass = (ClassSymbol) selectionExpressionNode.getEvaluatedType();
        var callNode = node.getCall();
        var parameterTypes = callNode.getArguments().stream()
            .map(ProphecyExpressionAstNode::getEvaluatedType)
            .collect(Collectors.toList());
        var methodSignature = new MethodSignature(callNode.getMethodName(), parameterTypes);
        var methodCalled = selectionExpressionClass.resolve(methodSignature);

        if (methodCalled == null) {
            var sourceCodeLocation = node.getDefinitionSourceCodeLocation();

            var message = "No method found on type \"$class\" that has this signature: $signature"
                .replace("$class", selectionExpressionClass.getName())
                .replace("$signature", methodSignature.toString());
            buildMessageService.error(sourceCodeLocation, message);

            return null;
        }

        var returnType = methodCalled.getType();

        callNode.setMethodSymbol((MethodSymbol) methodCalled);
        callNode.setEvaluatedType(returnType);
        node.setEvaluatedType(returnType);

        return methodCalled.getType();
    }

    @Override
    protected Type visitArrayLiteralAstNode(ProphecyArrayLiteralAstNode node) {
        visitChildren(node);

        var voidTypeElements = node.getElements().stream()
            .filter(element -> element.getEvaluatedType().equals(voidClass))
            .collect(Collectors.toList());

        if (!voidTypeElements.isEmpty()) {
            voidTypeElements.forEach(voidTypeElement ->
                buildMessageService.error(voidTypeElement.getDefinitionSourceCodeLocation(), "Array literal cannot contain elements of void type: " + voidTypeElement));

            return null;
        }

        if (node.getElements().isEmpty()) {
            buildMessageService.error(node.getDefinitionSourceCodeLocation(), "cannot declare empty array literal (because type inference not possible in this case)");

            return null;
        }

        var arrayParameterTypeSubstitution = node.getElements().stream()
            .map(ProphecyExpressionAstNode::getEvaluatedType)
            .reduce(Type::getMostSpecificCommonType)
            .orElseThrow();

        var arrayClass = bootstrapTypeSymbols.getArrayClass();
        var substitutedArrayClass = arrayClass.substitute(Map.of(arrayClass.getParameterTypes().get(0), arrayParameterTypeSubstitution));

        node.setArrayType(substitutedArrayClass);

        return arrayClass;
    }

    @Override
    protected Type visitVariableDeclarationAstNode(ProphecyVariableDeclarationAstNode node) {
        visitChildren(node);

        var initializerNode = node.getInitializer();
        var initializerType = initializerNode.getEvaluatedType();

        if (initializerType == null)
            return null;

        if (initializerType.equals(voidClass)) {
            buildMessageService.error(initializerNode.getDefinitionSourceCodeLocation(), "'val' variable declaration cannot have an initializer of type Void");

            return null;
        }

        if (node.getVariableSymbol() == null)
            return null;

        node.getVariableSymbol().setType(initializerType);

        return null;
    }

    @Override
    protected Type defaultForNonImplementedNodeTypes(ProphecyAstNode node) {
        return null;
    }

}
