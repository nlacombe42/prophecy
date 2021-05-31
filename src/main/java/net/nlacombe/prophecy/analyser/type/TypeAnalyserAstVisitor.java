package net.nlacombe.prophecy.analyser.type;

import net.nlacombe.prophecy.ast.ProphecyAstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyArrayLiteralAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyBinaryOperatorArithmeticAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyCallAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyForeachAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyIdentifierExpressionAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyVariableDeclarationAstNode;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.signature.MethodSignature;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TypeAnalyserAstVisitor extends ProphecyAstVisitor<Type> {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
    private static final ProphecySpecialTypeSymbols specialTypeSymbols = ProphecySpecialTypeSymbols.getInstance();
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
            if (buildMessageService.hasErrorBuildMessage())
                return null;
            else
                throw new ProphecyCompilerException("missing type analysis or error logging");
        }

        var methodSignature = new MethodSignature(node.getMethodName(), parameterTypes);
        var expressionNode = node.getExpression();
        var expressionType = expressionNode.getEvaluatedType();

        if (expressionType == null) {
            if (buildMessageService.hasErrorBuildMessage())
                return null;
            else
                throw new ProphecyCompilerException("missing type analysis or error logging");
        }

        if (!(expressionType instanceof ClassSymbol)) {
            var sourceCodeLocation = node.getDefinitionSourceCodeLocation();

            buildMessageService.error(sourceCodeLocation, "Cannot call method on non class expression: " + expressionType);

            return null;
        }

        var expressionTypeClass = (ClassSymbol) expressionType;
        var symbol = expressionTypeClass.resolve(methodSignature);

        if (symbol == null) {
            var sourceCodeLocation = node.getDefinitionSourceCodeLocation();

            buildMessageService.error(sourceCodeLocation, "No method with signature found: " + methodSignature);

            return null;
        }

        if (!(symbol instanceof MethodSymbol)) {
            var sourceCodeLocation = node.getDefinitionSourceCodeLocation();

            buildMessageService.error(sourceCodeLocation, "Cannot call something which is not a method: " + symbol);

            return null;
        }

        var methodSymbol = (MethodSymbol) symbol;

        if (!methodSymbol.isStatic() && expressionNode instanceof ProphecyIdentifierExpressionAstNode) {
            var identifierNode = ((ProphecyIdentifierExpressionAstNode) expressionNode);

            if (identifierNode.getSymbol() instanceof ClassSymbol) {
                var sourceCodeLocation = node.getDefinitionSourceCodeLocation();

                buildMessageService.error(sourceCodeLocation, "Cannot call an instance method without an instance: " + identifierNode.getSymbol());

                return null;
            }
        }

        var returnType = symbol.getType();

        node.setMethodSymbol((MethodSymbol) symbol);
        node.setEvaluatedType(returnType);
        node.setEvaluatedType(returnType);

        return symbol.getType();
    }

    @Override
    protected Type visitProphecyBinaryOperatorArithmeticAstNode(ProphecyBinaryOperatorArithmeticAstNode node) {
        visitChildren(node);

        var uInt8Class = bootstrapTypeSymbols.getUInt8Class();
        var leftExpressionNode = node.getLeft();
        var rightExpressionNode = node.getRight();
        var leftType = leftExpressionNode.getEvaluatedType();
        var rightType = rightExpressionNode.getEvaluatedType();

        if (leftType == null || rightType == null) {
            if (buildMessageService.hasErrorBuildMessage())
                return null;
            else
                throw new ProphecyCompilerException("missing type analysis or error logging");
        }


        if (!Type.sameType(leftType, uInt8Class)) {
            buildMessageService.error(leftExpressionNode.getDefinitionSourceCodeLocation(), "binary operations can only be done on UInt8");

            return null;
        }

        if (!Type.sameType(rightType, uInt8Class)) {
            buildMessageService.error(rightExpressionNode.getDefinitionSourceCodeLocation(), "binary operations can only be done on UInt8");

            return null;
        }

        node.setEvaluatedType(uInt8Class);

        return node.getEvaluatedType();
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

        return substitutedArrayClass;
    }

    @Override
    protected Type visitVariableDeclarationAstNode(ProphecyVariableDeclarationAstNode node) {
        visitChildren(node);

        var initializerNode = node.getInitializer();
        var initializerType = initializerNode.getEvaluatedType();

        if (initializerType == null) {
            if (buildMessageService.hasErrorBuildMessage())
                return null;
            else
                throw new ProphecyCompilerException("missing type analysis or error logging");
        }

        if (initializerType.equals(voidClass)) {
            buildMessageService.error(initializerNode.getDefinitionSourceCodeLocation(), "'val' variable declaration cannot have an initializer of type Void");

            return null;
        }

        if (node.getVariableSymbol() == null) {
            if (buildMessageService.hasErrorBuildMessage())
                return null;
            else
                throw new ProphecyCompilerException("missing type analysis or error logging");
        }

        node.getVariableSymbol().setType(initializerType);

        return null;
    }

    @Override
    protected Type visitProphecyForeachAstNode(ProphecyForeachAstNode node) {
        visit(node.getExpression());

        var expressionType = node.getExpression().getEvaluatedType();

        if (expressionType == null) {
            if (buildMessageService.hasErrorBuildMessage())
                return null;
            else
                throw new ProphecyCompilerException("missing type analysis or error logging");
        }

        if (!Type.sameType(expressionType, specialTypeSymbols.getUInt8Array())) {
            buildMessageService.error(node.getExpression().getDefinitionSourceCodeLocation(), "only Array<UInt8> expression are implemented for foreach expressions: " + expressionType);

            return null;
        }

        var expressionClass = (ClassSymbol) expressionType;
        var arrayElementType = expressionClass.getSubstitutedParameterTypes().get(0);

        var variableSymbol = node.getVariableSymbol();

        if (variableSymbol == null) {
            if (buildMessageService.hasErrorBuildMessage())
                return null;
            else
                throw new ProphecyCompilerException("missing type analysis or error logging");
        }

        variableSymbol.setType(arrayElementType);

        visitNodes(node.getBlock());

        return null;
    }

    @Override
    protected Type defaultForNonImplementedNodeTypes(ProphecyAstNode node) {
        return node instanceof ProphecyExpressionAstNode ? ((ProphecyExpressionAstNode) node).getEvaluatedType() : null;
    }

}
