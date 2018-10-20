package com.theprophet31337.prophecy.ast;

public class ProphecyAstVisitorDispatcher
{
	public static <T> T dispatchVisitor(ProphecyAstVisitor<T> visitor, ProphecyAstNode node)
	{
		switch (node.getType()) {
			case BLOCK:
				return visitor.visitBlock(node);

			case FILE:
				return visitor.visitFile(node);

			case CLASSDEF:
				return visitor.visitClassDef(node);

			case DEFBLOCK:
				return visitor.visitDefBlock(node);

			case MEMBER:
				return visitor.visitMember(node);

			case METHODDEF:
				return visitor.visitMethodDef(node);

			case ID:
				return visitor.visitId(node);

			case ADD:
				return visitor.visitAdd(node);

			case ASSIGMENT:
				return visitor.visitAssigment(node);

			case BITAND:
				return visitor.visitBitAnd(node);

			case BITOR:
				return visitor.visitBitOr(node);

			case BITXOR:
				return visitor.visitBitXor(node);

			case CALL:
				return visitor.visitCall(node);

			case CAST:
				return visitor.visitCast(node);

			case CHARLIT:
				return visitor.visitCharLit(node);

			case DIV:
				return visitor.visitDiv(node);

			case EOF:
				return visitor.visitEof(node);

			case EQ:
				return visitor.visitEq(node);

			case EXPRLIST:
				return visitor.visitExprList(node);

			case FALSELIT:
				return visitor.visitFalseLit(node);

			case FIELDDEF:
				return visitor.visitFieldDef(node);

			case FLOATLIT:
				return visitor.visitFloatLit(node);

			case GT:
				return visitor.visitGt(node);

			case GTE:
				return visitor.visitGte(node);

			case IF:
				return visitor.visitIf(node);

			case INDEX:
				return visitor.visitIndex(node);

			case INTLIT:
				return visitor.visitIntLit(node);

			case LOGICAND:
				return visitor.visitLogicAnd(node);

			case LOGICOR:
				return visitor.visitLogicOr(node);

			case LT:
				return visitor.visitLt(node);

			case LTE:
				return visitor.visitLte(node);

			case MODIFIER:
				return visitor.visitModifier(node);

			case MODIFIERLIST:
				return visitor.visitModifierList(node);

			case MUL:
				return visitor.visitMul(node);

			case NEQ:
				return visitor.visitNeq(node);

			case PARAM:
				return visitor.visitParam(node);

			case PARAMLIST:
				return visitor.visitParamList(node);

			case POSTDEC:
				return visitor.visitPostDec(node);

			case POSTINC:
				return visitor.visitPostInc(node);

			case PREDEC:
				return visitor.visitPreDec(node);

			case PREINC:
				return visitor.visitPreInc(node);

			case RETURN:
				return visitor.visitReturn(node);

			case SHIFTL:
				return visitor.visitShiftL(node);

			case SHIFTR:
				return visitor.visitShiftR(node);

			case STRINGLIT:
				return visitor.visitStringLit(node);

			case SUB:
				return visitor.visitSub(node);

			case TRUELIT:
				return visitor.visitTrueLit(node);

			case UBITNOT:
				return visitor.visitUBitNot(node);

			case ULOGICNOT:
				return visitor.visitULogicNot(node);

			case UMINUS:
				return visitor.visitUMinus(node);

			case UPLUS:
				return visitor.visitUPlus(node);

			case VARDECL:
				return visitor.visitVarDecl(node);

			case WHILE:
				return visitor.visitWhile(node);

			case NULL:

			default:
				throw new IllegalArgumentException("Unkown AST node type: " + node.getType());
		}
	}
}
