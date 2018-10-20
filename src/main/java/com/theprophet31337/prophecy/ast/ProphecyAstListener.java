package com.theprophet31337.prophecy.ast;

public interface ProphecyAstListener
{
	public void enterAnyNode(ProphecyAstNode node);

	public void exitAnyNode(ProphecyAstNode node);

	public void enterFile(ProphecyAstNode node);

	public void exitFile(ProphecyAstNode node);

	public void enterClassDef(ProphecyAstNode node);

	public void exitClassDef(ProphecyAstNode node);

	public void enterBlock(ProphecyAstNode node);

	public void exitBlock(ProphecyAstNode node);

	public void enterDefBlock(ProphecyAstNode node);

	public void exitDefBlock(ProphecyAstNode node);

	public void enterMember(ProphecyAstNode node);

	public void exitMember(ProphecyAstNode node);

	public void enterMethodDef(ProphecyAstNode node);

	public void exitMethodDef(ProphecyAstNode node);

	public void enterId(ProphecyAstNode node);

	public void exitId(ProphecyAstNode node);

	public void enterVarDecl(ProphecyAstNode node);

	public void exitVarDecl(ProphecyAstNode node);

	public void enterParam(ProphecyAstNode node);

	public void exitParam(ProphecyAstNode node);

	public void enterFieldDef(ProphecyAstNode node);

	public void exitFieldDef(ProphecyAstNode node);

	public void enterReturn(ProphecyAstNode node);

	public void exitReturn(ProphecyAstNode node);

	public void enterAdd(ProphecyAstNode node);

	public void exitAdd(ProphecyAstNode node);

	public void enterAssigment(ProphecyAstNode node);

	public void exitAssigment(ProphecyAstNode node);

	public void enterBitAnd(ProphecyAstNode node);

	public void exitBitAnd(ProphecyAstNode node);

	public void enterBitOr(ProphecyAstNode node);

	public void exitBitOr(ProphecyAstNode node);

	public void enterBitXor(ProphecyAstNode node);

	public void exitBitXor(ProphecyAstNode node);

	public void enterCall(ProphecyAstNode node);

	public void exitCall(ProphecyAstNode node);

	public void enterCast(ProphecyAstNode node);

	public void exitCast(ProphecyAstNode node);

	public void enterCharLit(ProphecyAstNode node);

	public void exitCharLit(ProphecyAstNode node);

	public void enterDiv(ProphecyAstNode node);

	public void exitDiv(ProphecyAstNode node);

	public void enterEof(ProphecyAstNode node);

	public void exitEof(ProphecyAstNode node);

	public void enterEq(ProphecyAstNode node);

	public void exitEq(ProphecyAstNode node);

	public void enterExprList(ProphecyAstNode node);

	public void exitExprList(ProphecyAstNode node);

	public void enterFalseLit(ProphecyAstNode node);

	public void exitFalseLit(ProphecyAstNode node);

	public void enterFloatLit(ProphecyAstNode node);

	public void exitFloatLit(ProphecyAstNode node);

	public void enterGt(ProphecyAstNode node);

	public void exitGt(ProphecyAstNode node);

	public void enterGte(ProphecyAstNode node);

	public void exitGte(ProphecyAstNode node);

	public void enterIf(ProphecyAstNode node);

	public void exitIf(ProphecyAstNode node);

	public void enterIndex(ProphecyAstNode node);

	public void exitIndex(ProphecyAstNode node);

	public void enterIntLit(ProphecyAstNode node);

	public void exitIntLit(ProphecyAstNode node);

	public void enterLogicAnd(ProphecyAstNode node);

	public void exitLogicAnd(ProphecyAstNode node);

	public void enterLogicOr(ProphecyAstNode node);

	public void exitLogicOr(ProphecyAstNode node);

	public void enterLt(ProphecyAstNode node);

	public void exitLt(ProphecyAstNode node);

	public void enterLte(ProphecyAstNode node);

	public void exitLte(ProphecyAstNode node);

	public void enterModifier(ProphecyAstNode node);

	public void exitModifier(ProphecyAstNode node);

	public void enterModifierList(ProphecyAstNode node);

	public void exitModifierList(ProphecyAstNode node);

	public void enterMul(ProphecyAstNode node);

	public void exitMul(ProphecyAstNode node);

	public void enterNeq(ProphecyAstNode node);

	public void exitNeq(ProphecyAstNode node);

	public void enterParamList(ProphecyAstNode node);

	public void exitParamList(ProphecyAstNode node);

	public void enterPostDec(ProphecyAstNode node);

	public void exitPostDec(ProphecyAstNode node);

	public void enterPostInc(ProphecyAstNode node);

	public void exitPostInc(ProphecyAstNode node);

	public void enterPreDec(ProphecyAstNode node);

	public void exitPreDec(ProphecyAstNode node);

	public void enterPreInc(ProphecyAstNode node);

	public void exitPreInc(ProphecyAstNode node);

	public void enterShiftL(ProphecyAstNode node);

	public void exitShiftL(ProphecyAstNode node);

	public void enterShiftR(ProphecyAstNode node);

	public void exitShiftR(ProphecyAstNode node);

	public void enterStringLit(ProphecyAstNode node);

	public void exitStringLit(ProphecyAstNode node);

	public void enterSub(ProphecyAstNode node);

	public void exitSub(ProphecyAstNode node);

	public void enterTrueLit(ProphecyAstNode node);

	public void exitTrueLit(ProphecyAstNode node);

	public void enterUBitNot(ProphecyAstNode node);

	public void exitUBitNot(ProphecyAstNode node);

	public void enterULogicNot(ProphecyAstNode node);

	public void exitULogicNot(ProphecyAstNode node);

	public void enterUMinus(ProphecyAstNode node);

	public void exitUMinus(ProphecyAstNode node);

	public void enterUPlus(ProphecyAstNode node);

	public void exitUPlus(ProphecyAstNode node);

	public void enterWhile(ProphecyAstNode node);

	public void exitWhile(ProphecyAstNode node);
}
