package com.theprophet31337.prophecy.analyser.type;

import com.theprophet31337.prophecy.analyser.symboltable.MethodSignature;
import com.theprophet31337.prophecy.analyser.symboltable.SymbolSignature;
import com.theprophet31337.prophecy.analyser.symboltable.Type;
import com.theprophet31337.prophecy.analyser.symboltable.scope.Scope;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.BuiltInTypeSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.ClassSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.MethodSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.Symbol;
import com.theprophet31337.prophecy.ast.ProphecyAstBaseListener;
import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstNodeType;
import com.theprophet31337.prophecy.ast.ProphecyAstUtil;
import com.theprophet31337.prophecy.ast.nodewrapper.AstCall;
import com.theprophet31337.prophecy.ast.nodewrapper.AstCast;
import com.theprophet31337.prophecy.ast.nodewrapper.AstMember;
import com.theprophet31337.prophecy.constants.Constants;
import com.theprophet31337.prophecy.exception.ProphecyAmbiguousCallException;
import com.theprophet31337.prophecy.exception.ProphecyMethodNotFoundException;
import com.theprophet31337.prophecy.reporting.BuildMessageLevel;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;

/**
 * Calculate promotion, calculate eval type and perform static type checking.
 * Do all of those operations on exit of the AST node.
 */
public class TypeAnalyserListener extends ProphecyAstBaseListener
{
	private ProphecyBuildListener buildListener;

	public TypeAnalyserListener(ProphecyBuildListener buildListener)
	{
		this.buildListener = buildListener;
	}

	private void invalidOperandType(int line, int column, Type lhs, Type rhs, String operation)
	{
		buildListener.buildMessage(BuildMessageLevel.ERROR, line, column,
				"Invalid operands type: " + lhs + " and " + rhs + " for binary operation " + operation);
	}

	private void invalidOperandType(int line, int column, Type expr, String operation)
	{
		buildListener.buildMessage(BuildMessageLevel.ERROR, line, column,
				"Invalid operand type: " + expr + " for unary operation " + operation);
	}

	private void typeMismatch(int line, int column, Type expected, Type type)
	{
		buildListener.buildMessage(BuildMessageLevel.ERROR, line, column,
				"Type mismatch, cannot convert " + type.getName() + " to " + expected.getName());
	}

	private void invalidCast(int line, int column, Type from, Type to)
	{
		buildListener.buildMessage(BuildMessageLevel.ERROR, line, column,
				"Cannot cast from " + from.getName() + " to " + to.getName());
	}

	private void methodNotFound(int line, int column, ClassSymbol classSymbol, MethodSignature signature)
	{
		buildListener.buildMessage(BuildMessageLevel.ERROR, line, column,
				"Method with signature " + signature + " not found in " + classSymbol.getName());
	}

	private void ambiguousCall(int line, int column, ClassSymbol classSymbol, MethodSignature signature)
	{
		buildListener.buildMessage(BuildMessageLevel.ERROR, line, column,
				"Ambiguous call for " + signature + " in " + classSymbol.getName());
	}

	private void overloadinResolutionWarning(int line, int column, ClassSymbol classSymbol,
											 MethodSignature callSignature, MethodSignature choosenSignature)
	{
		String className = classSymbol.getName();

		buildListener.buildMessage(BuildMessageLevel.WARNING, line, column,
				className + "." + callSignature + " will call " + className + "." + choosenSignature);
	}

	private void arithmeticBitOperation(ProphecyAstNode node, String operationName)
	{
		ProphecyAstNode lhsNode = node.getChild(0);
		ProphecyAstNode rhsNode = node.getChild(1);

		Type lhsType = lhsNode.getEvalType();
		Type rhsType = rhsNode.getEvalType();

		if (!BuiltInTypeSymbol.isIntegerType(lhsType) || !BuiltInTypeSymbol.isIntegerType(rhsType)) {
			invalidOperandType(node.getLineNumber(), node.getColumn(), lhsType, rhsType, operationName);
		}

		node.setEvalType(lhsType);
	}

	private void binaryArithmeticOperation(ProphecyAstNode node, String operationName)
	{
		ProphecyAstNode lhsNode = node.getChild(0);
		ProphecyAstNode rhsNode = node.getChild(1);

		Type lhs = lhsNode.getEvalType();
		Type rhs = rhsNode.getEvalType();

		lhsNode.setPromoteToType(TypeCalculations.promoteToType(lhs, rhs));
		rhsNode.setPromoteToType(TypeCalculations.promoteToType(rhs, lhs));

		Type evalType = TypeCalculations.getArithmeticResultType(lhs, rhs);

		if (evalType == BuiltInTypeSymbol.tVoid) {
			invalidOperandType(node.getLineNumber(), node.getColumn(), lhs, rhs, operationName);
		}

		node.setEvalType(evalType);
	}

	/**
	 * Eg.: x++ x-- ++x --x.
	 */
	private void unnaryArithmeticOperation(ProphecyAstNode node, String operationName)
	{
		Type exprType = node.getChild(0).getEvalType();
		Type impliedOperandType = BuiltInTypeSymbol.tInt;

		node.setPromoteToType(TypeCalculations.promoteToType(exprType, impliedOperandType));

		Type evalType = TypeCalculations.getArithmeticResultType(exprType, impliedOperandType);

		if (evalType == BuiltInTypeSymbol.tVoid) {
			invalidOperandType(node.getLineNumber(), node.getColumn(), exprType, impliedOperandType, operationName);
		}

		node.setEvalType(evalType);
	}

	private void relationalOperation(ProphecyAstNode node, String operationName)
	{
		ProphecyAstNode lhsNode = node.getChild(0);
		ProphecyAstNode rhsNode = node.getChild(1);

		Type lhs = lhsNode.getEvalType();
		Type rhs = rhsNode.getEvalType();

		lhsNode.setPromoteToType(TypeCalculations.promoteToType(lhs, rhs));
		rhsNode.setPromoteToType(TypeCalculations.promoteToType(rhs, lhs));

		Type evalType = TypeCalculations.getRelationalResultType(lhs, rhs);

		if (evalType == BuiltInTypeSymbol.tVoid) {
			invalidOperandType(node.getLineNumber(), node.getColumn(), lhs, rhs, operationName);
		}

		node.setEvalType(evalType);
	}

	private void logicalOperation(ProphecyAstNode node, String operationName)
	{
		ProphecyAstNode lhsNode = node.getChild(0);
		ProphecyAstNode rhsNode = node.getChild(1);

		Type lhsType = lhsNode.getEvalType();
		Type rhsType = rhsNode.getEvalType();

		if (!BuiltInTypeSymbol.tBool.equals(lhsType) || !BuiltInTypeSymbol.tBool.equals(rhsType)) {
			invalidOperandType(node.getLineNumber(), node.getColumn(), lhsType, rhsType, operationName);
		}

		node.setEvalType(BuiltInTypeSymbol.tBool);
	}

	private void equalityOperation(ProphecyAstNode node, String operationName)
	{
		ProphecyAstNode lhsNode = node.getChild(0);
		ProphecyAstNode rhsNode = node.getChild(1);

		Type lhs = lhsNode.getEvalType();
		Type rhs = rhsNode.getEvalType();

		lhsNode.setPromoteToType(TypeCalculations.promoteToType(lhs, rhs));
		rhsNode.setPromoteToType(TypeCalculations.promoteToType(rhs, lhs));

		Type evalType = TypeCalculations.getEqualityResultType(lhs, rhs);

		if (evalType == BuiltInTypeSymbol.tVoid) {
			invalidOperandType(node.getLineNumber(), node.getColumn(), lhs, rhs, operationName);
		}

		node.setEvalType(evalType);
	}

	/**
	 * Check for type compatibility and promote <code>exprNode</code> if needed.
	 */
	private void assigment(Type lhsType, ProphecyAstNode exprNode)
	{
		Type exprType = exprNode.getEvalType();

		exprNode.setPromoteToType(TypeCalculations.promoteToType(exprType, lhsType));

		if (!exprType.equals(lhsType) && (exprNode.getPromoteToType() == null || !exprNode.getPromoteToType().equals(lhsType))) {
			typeMismatch(exprNode.getLineNumber(), exprNode.getColumn(), lhsType, exprType);
		}
	}

	@Override
	public void exitId(ProphecyAstNode node)
	{
		if (ProphecyAstUtil.isDefinition(node.getParent()))
			return;

		Symbol symbol = node.getSymbol();

		if (symbol == null) {
			//unresolved symbol. This means an error in the resolve step (or no resolve step was taken). 
			return;
		}

		node.setEvalType(symbol.getType());
	}

	@Override
	public void exitAdd(ProphecyAstNode node)
	{
		binaryArithmeticOperation(node, "+");
	}

	@Override
	public void exitAssigment(ProphecyAstNode node)
	{
		Type lhsType = node.getChild(0).getEvalType();

		assigment(lhsType, node.getChild(1));

		node.setEvalType(lhsType);
	}

	@Override
	public void exitVarDecl(ProphecyAstNode node)
	{
		Type lhsType = node.getSymbol().getType();

		//If there is an initial value to the variable declaration.
		if (node.getChildCount() > 2)
			assigment(lhsType, node.getChild(2));
	}

	@Override
	public void exitBitAnd(ProphecyAstNode node)
	{
		arithmeticBitOperation(node, "&");
	}

	@Override
	public void exitBitOr(ProphecyAstNode node)
	{
		arithmeticBitOperation(node, "|");
	}

	@Override
	public void exitBitXor(ProphecyAstNode node)
	{
		arithmeticBitOperation(node, "^");
	}

	@Override
	public void exitCall(ProphecyAstNode node)
	{
		AstCall call = new AstCall(node);
		ClassSymbol object;
		String methodName;

		if (call.getMethodNode().getType() == ProphecyAstNodeType.MEMBER) {
			AstMember member = new AstMember(call.getMethodNode());

			methodName = member.getMemberNode().getText();

			if (member.getObjectNode().getEvalType() == null)
				return;

			object = (ClassSymbol) member.getObjectNode().getEvalType();
		} else {
			methodName = call.getMethodNode().getText();
			Scope scope = node.getScope();

			/* If we are calling a method then we must call it from a method which is inside a class.
			 * Furthermore, the method we are calling must be from this class' context.
			 */

			while (!(scope instanceof ClassSymbol))
				scope = scope.getParentScope();

			object = (ClassSymbol) scope;
		}

		MethodSignature callSignature = new MethodSignature(methodName);

		if (call.getArgumentsNode() != null) {
			Type argType;

			for (ProphecyAstNode argNode : call.getArgumentsNode().getChildrenAsArray()) {
				argType = argNode.getEvalType();

				//If argType is null then there was an error.
				if (argType == null)
					return;

				callSignature.addParameter(argType);
			}
		}

		OverloadResolution.Result result;

		try {
			result = OverloadResolution.resolve(object, callSignature);

			if (result.isRatingBased())
				overloadinResolutionWarning(node.getLineNumber(), node.getColumn(), object,
						callSignature, result.getMethod().getSignature());
		} catch (ProphecyMethodNotFoundException e) {
			methodNotFound(node.getLineNumber(), node.getColumn(), object, callSignature);
			return;
		} catch (ProphecyAmbiguousCallException e) {
			ambiguousCall(node.getLineNumber(), node.getColumn(), object, callSignature);
			return;
		}

		node.setSymbol(result.getMethod());

		node.setEvalType(result.getMethod().getType());
	}

	@Override
	public void exitCast(ProphecyAstNode node)
	{
		AstCast cast = new AstCast(node);

		ProphecyAstNode typeNode = cast.getTypeNode();
		Symbol typeSymbol = typeNode.getSymbol();

		if (typeSymbol == null)
			return;

		if (!(typeSymbol instanceof Type)) {
			buildListener.buildMessage(BuildMessageLevel.ERROR, typeNode.getLineNumber(), typeNode.getColumn(),
					"\"" + typeNode.getText() + "\" is not a type.");
			return;
		}

		typeNode.setEvalType((Type) typeSymbol);

		Type to = cast.getTypeNode().getEvalType();
		Type from = cast.getExpressionNode().getEvalType();

		//You can only cast compatible types
		if (!from.canAssignTo(to) && !to.canAssignTo(from)) {
			invalidCast(node.getLineNumber(), node.getColumn(), from, to);
			return;
		}

		node.setEvalType(to);
	}

	@Override
	public void exitCharLit(ProphecyAstNode node)
	{
		node.setEvalType(BuiltInTypeSymbol.tChar);
	}

	@Override
	public void exitDiv(ProphecyAstNode node)
	{
		binaryArithmeticOperation(node, "/");
	}

	@Override
	public void exitEq(ProphecyAstNode node)
	{
		equalityOperation(node, "==");
	}

	@Override
	public void exitFalseLit(ProphecyAstNode node)
	{
		node.setEvalType(BuiltInTypeSymbol.tBool);
	}

	@Override
	public void exitFloatLit(ProphecyAstNode node)
	{
		node.setEvalType(BuiltInTypeSymbol.tFloat);
	}

	@Override
	public void exitGt(ProphecyAstNode node)
	{
		relationalOperation(node, ">");
	}

	@Override
	public void exitGte(ProphecyAstNode node)
	{
		relationalOperation(node, ">=");
	}

	@Override
	public void exitIndex(ProphecyAstNode node)
	{
		ProphecyAstNode arrayNode = node.getChild(0);
		ProphecyAstNode exprNode = node.getChild(1);

		Type arrayType = arrayNode.getEvalType();
		Type exprType = exprNode.getEvalType();

		exprNode.setPromoteToType(TypeCalculations.promoteToType(exprType, BuiltInTypeSymbol.tInt));

		if (!(exprType instanceof BuiltInTypeSymbol) || BuiltInTypeSymbol.isIntegerType((BuiltInTypeSymbol) exprType)) {
			invalidOperandType(exprNode.getLineNumber(), exprNode.getColumn(), exprType, "arr");
		}

		node.setEvalType(arrayType);
	}

	@Override
	public void exitIntLit(ProphecyAstNode node)
	{
		node.setEvalType(BuiltInTypeSymbol.tInt);
	}

	@Override
	public void exitLogicAnd(ProphecyAstNode node)
	{
		logicalOperation(node, "&&");
	}

	@Override
	public void exitLogicOr(ProphecyAstNode node)
	{
		logicalOperation(node, "||");
	}

	@Override
	public void exitLt(ProphecyAstNode node)
	{
		relationalOperation(node, "<");
	}

	@Override
	public void exitLte(ProphecyAstNode node)
	{
		relationalOperation(node, "<=");
	}

	@Override
	public void exitMul(ProphecyAstNode node)
	{
		binaryArithmeticOperation(node, "*");
	}

	@Override
	public void exitNeq(ProphecyAstNode node)
	{
		equalityOperation(node, "!=");
	}

	@Override
	public void exitReturn(ProphecyAstNode node)
	{
		//Validate returned type is same as method return type

		MethodSymbol method = (MethodSymbol) node.getSymbol();
		ProphecyAstNode exprNode = node.getChild(0);

		Type returnType = method.getType();
		Type exprType = exprNode.getEvalType();

		exprNode.setPromoteToType(TypeCalculations.promoteToType(exprType, returnType));

		if (!exprType.equals(returnType) && (exprNode.getPromoteToType() == null || !exprNode.getPromoteToType().equals(exprType))) {
			typeMismatch(exprNode.getLineNumber(), exprNode.getColumn(), returnType, exprType);
		}
	}

	@Override
	public void exitPostDec(ProphecyAstNode node)
	{
		unnaryArithmeticOperation(node, "post decrement (eg.: x--)");
	}

	@Override
	public void exitPostInc(ProphecyAstNode node)
	{
		unnaryArithmeticOperation(node, "post increment (eg.: x++)");
	}

	@Override
	public void exitPreDec(ProphecyAstNode node)
	{
		unnaryArithmeticOperation(node, "pre decrement (eg.: --x)");
	}

	@Override
	public void exitPreInc(ProphecyAstNode node)
	{
		unnaryArithmeticOperation(node, "pre increment (eg.: ++x)");
	}

	@Override
	public void exitShiftL(ProphecyAstNode node)
	{
		arithmeticBitOperation(node, "<<");
	}

	@Override
	public void exitShiftR(ProphecyAstNode node)
	{
		arithmeticBitOperation(node, ">>");
	}

	@Override
	public void exitStringLit(ProphecyAstNode node)
	{
		Scope scope = node.getScope();

		Type stringType = (Type) scope.resolve(new SymbolSignature(Constants.STRING_CLASS_NAME));

		node.setEvalType(stringType);
	}

	@Override
	public void exitSub(ProphecyAstNode node)
	{
		binaryArithmeticOperation(node, "-");
	}

	@Override
	public void exitTrueLit(ProphecyAstNode node)
	{
		node.setEvalType(BuiltInTypeSymbol.tBool);
	}

	@Override
	public void exitUBitNot(ProphecyAstNode node)
	{
		ProphecyAstNode exprNode = node.getChild(0);

		Type exprType = exprNode.getEvalType();

		if (!(exprType instanceof BuiltInTypeSymbol) || BuiltInTypeSymbol.isIntegerType((BuiltInTypeSymbol) exprType)) {
			invalidOperandType(exprNode.getLineNumber(), exprNode.getColumn(), exprType, "~");
		}

		node.setEvalType(exprType);
	}

	@Override
	public void exitULogicNot(ProphecyAstNode node)
	{
		ProphecyAstNode exprNode = node.getChild(0);

		Type exprType = exprNode.getEvalType();

		if (exprType != BuiltInTypeSymbol.tBool) {
			invalidOperandType(exprNode.getLineNumber(), exprNode.getColumn(), exprType, "!");
		}

		node.setEvalType(BuiltInTypeSymbol.tBool);
	}

	@Override
	public void exitUMinus(ProphecyAstNode node)
	{
		ProphecyAstNode exprNode = node.getChild(0);

		Type exprType = exprNode.getEvalType();

		if (!(exprType instanceof BuiltInTypeSymbol) || BuiltInTypeSymbol.isNumeric((BuiltInTypeSymbol) exprType)) {
			invalidOperandType(exprNode.getLineNumber(), exprNode.getColumn(), exprType, "-");
		}

		node.setEvalType(exprType);
	}

	@Override
	public void exitUPlus(ProphecyAstNode node)
	{
		ProphecyAstNode exprNode = node.getChild(0);

		Type exprType = exprNode.getEvalType();

		if (!(exprType instanceof BuiltInTypeSymbol) || BuiltInTypeSymbol.isNumeric((BuiltInTypeSymbol) exprType)) {
			invalidOperandType(exprNode.getLineNumber(), exprNode.getColumn(), exprType, "+");
		}

		node.setEvalType(exprType);
	}
}
