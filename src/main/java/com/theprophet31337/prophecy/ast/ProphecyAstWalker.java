package com.theprophet31337.prophecy.ast;

import java.util.LinkedList;
import java.util.List;

public class ProphecyAstWalker
{
	private List<ProphecyAstListener> listeners;

	public ProphecyAstWalker()
	{
		listeners = new LinkedList<ProphecyAstListener>();
	}

	public void addListener(ProphecyAstListener listener)
	{
		listeners.add(listener);
	}

	public void removeAllListeners()
	{
		listeners.clear();
	}

	public void walk(ProphecyAstNode node)
	{
		callEnter(node);

		for (int i = 0; i < node.getChildCount(); i++)
			walk(node.getChild(i));

		callExit(node);
	}

	private void callEnter(ProphecyAstNode node)
	{
		for (ProphecyAstListener listener : listeners) {
			listener.enterAnyNode(node);

			switch (node.getType()) {
				case BLOCK:
					listener.enterBlock(node);
					break;

				case FILE:
					listener.enterFile(node);
					break;

				case CLASSDEF:
					listener.enterClassDef(node);
					break;

				case DEFBLOCK:
					listener.enterDefBlock(node);
					break;

				case MEMBER:
					listener.enterMember(node);
					break;

				case METHODDEF:
					listener.enterMethodDef(node);
					break;

				case ID:
					listener.enterId(node);
					break;

				case ADD:
					listener.enterAdd(node);
					break;

				case ASSIGMENT:
					listener.enterAssigment(node);
					break;

				case BITAND:
					listener.enterBitAnd(node);
					break;

				case BITOR:
					listener.enterBitOr(node);
					break;

				case BITXOR:
					listener.enterBitXor(node);
					break;

				case CALL:
					listener.enterCall(node);
					break;

				case CAST:
					listener.enterCast(node);
					break;

				case CHARLIT:
					listener.enterCharLit(node);
					break;

				case DIV:
					listener.enterDiv(node);
					break;

				case EOF:
					listener.enterEof(node);
					break;

				case EQ:
					listener.enterEq(node);
					break;

				case EXPRLIST:
					listener.enterExprList(node);
					break;

				case FALSELIT:
					listener.enterFalseLit(node);
					break;

				case FIELDDEF:
					listener.enterFieldDef(node);
					break;

				case FLOATLIT:
					listener.enterFloatLit(node);
					break;

				case GT:
					listener.enterGt(node);
					break;

				case GTE:
					listener.enterGte(node);
					break;

				case IF:
					listener.enterIf(node);
					break;

				case INDEX:
					listener.enterIndex(node);
					break;

				case INTLIT:
					listener.enterIntLit(node);
					break;

				case LOGICAND:
					listener.enterLogicAnd(node);
					break;

				case LOGICOR:
					listener.enterLogicOr(node);
					break;

				case LT:
					listener.enterLt(node);
					break;

				case LTE:
					listener.enterLte(node);
					break;

				case MODIFIER:
					listener.enterModifier(node);
					break;

				case MODIFIERLIST:
					listener.enterModifierList(node);
					break;

				case MUL:
					listener.enterMul(node);
					break;

				case NEQ:
					listener.enterNeq(node);
					break;

				case PARAM:
					listener.enterParam(node);
					break;

				case PARAMLIST:
					listener.enterParamList(node);
					break;

				case POSTDEC:
					listener.enterPostDec(node);
					break;

				case POSTINC:
					listener.enterPostInc(node);
					break;

				case PREDEC:
					listener.enterPreDec(node);
					break;

				case PREINC:
					listener.enterPreInc(node);
					break;

				case RETURN:
					listener.enterReturn(node);
					break;

				case SHIFTL:
					listener.enterShiftL(node);
					break;

				case SHIFTR:
					listener.enterShiftR(node);
					break;

				case STRINGLIT:
					listener.enterStringLit(node);
					break;

				case SUB:
					listener.enterSub(node);
					break;

				case TRUELIT:
					listener.enterTrueLit(node);
					break;

				case UBITNOT:
					listener.enterUBitNot(node);
					break;

				case ULOGICNOT:
					listener.enterULogicNot(node);
					break;

				case UMINUS:
					listener.enterUMinus(node);
					break;

				case UPLUS:
					listener.enterUPlus(node);
					break;

				case VARDECL:
					listener.enterVarDecl(node);
					break;

				case WHILE:
					listener.enterWhile(node);
					break;

				case NULL:
					break;

				default:
					throw new IllegalArgumentException("Unkown AST node type: " + node.getType());
			}
		}
	}

	private void callExit(ProphecyAstNode node)
	{
		for (ProphecyAstListener listener : listeners) {
			switch (node.getType()) {
				case BLOCK:
					listener.exitBlock(node);
					break;

				case FILE:
					listener.exitFile(node);
					break;

				case CLASSDEF:
					listener.exitClassDef(node);
					break;

				case DEFBLOCK:
					listener.exitDefBlock(node);
					break;

				case MEMBER:
					listener.exitMember(node);
					break;

				case METHODDEF:
					listener.exitMethodDef(node);
					break;

				case ID:
					listener.exitId(node);
					break;

				case ADD:
					listener.exitAdd(node);
					break;

				case ASSIGMENT:
					listener.exitAssigment(node);
					break;

				case BITAND:
					listener.exitBitAnd(node);
					break;

				case BITOR:
					listener.exitBitOr(node);
					break;

				case BITXOR:
					listener.exitBitXor(node);
					break;

				case CALL:
					listener.exitCall(node);
					break;

				case CAST:
					listener.exitCast(node);
					break;

				case CHARLIT:
					listener.exitCharLit(node);
					break;

				case DIV:
					listener.exitDiv(node);
					break;

				case EOF:
					listener.exitEof(node);
					break;

				case EQ:
					listener.exitEq(node);
					break;

				case EXPRLIST:
					listener.exitExprList(node);
					break;

				case FALSELIT:
					listener.exitFalseLit(node);
					break;

				case FIELDDEF:
					listener.exitFieldDef(node);
					break;

				case FLOATLIT:
					listener.exitFloatLit(node);
					break;

				case GT:
					listener.exitGt(node);
					break;

				case GTE:
					listener.exitGte(node);
					break;

				case IF:
					listener.exitIf(node);
					break;

				case INDEX:
					listener.exitIndex(node);
					break;

				case INTLIT:
					listener.exitIntLit(node);
					break;

				case LOGICAND:
					listener.exitLogicAnd(node);
					break;

				case LOGICOR:
					listener.exitLogicOr(node);
					break;

				case LT:
					listener.exitLt(node);
					break;

				case LTE:
					listener.exitLte(node);
					break;

				case MODIFIER:
					listener.exitModifier(node);
					break;

				case MODIFIERLIST:
					listener.exitModifierList(node);
					break;

				case MUL:
					listener.exitMul(node);
					break;

				case NEQ:
					listener.exitNeq(node);
					break;

				case PARAM:
					listener.exitParam(node);
					break;

				case PARAMLIST:
					listener.exitParamList(node);
					break;

				case POSTDEC:
					listener.exitPostDec(node);
					break;

				case POSTINC:
					listener.exitPostInc(node);
					break;

				case PREDEC:
					listener.exitPreDec(node);
					break;

				case PREINC:
					listener.exitPreInc(node);
					break;

				case RETURN:
					listener.exitReturn(node);
					break;

				case SHIFTL:
					listener.exitShiftL(node);
					break;

				case SHIFTR:
					listener.exitShiftR(node);
					break;

				case STRINGLIT:
					listener.exitStringLit(node);
					break;

				case SUB:
					listener.exitSub(node);
					break;

				case TRUELIT:
					listener.exitTrueLit(node);
					break;

				case UBITNOT:
					listener.exitUBitNot(node);
					break;

				case ULOGICNOT:
					listener.exitULogicNot(node);
					break;

				case UMINUS:
					listener.exitUMinus(node);
					break;

				case UPLUS:
					listener.exitUPlus(node);
					break;

				case VARDECL:
					listener.exitVarDecl(node);
					break;

				case WHILE:
					listener.exitWhile(node);
					break;

				case NULL:
					break;

				default:
					throw new IllegalArgumentException("Unkown AST node type");
			}

			listener.exitAnyNode(node);
		}
	}
}
