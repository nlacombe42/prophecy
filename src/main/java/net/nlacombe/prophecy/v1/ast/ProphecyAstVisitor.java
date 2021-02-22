package net.nlacombe.prophecy.v1.ast;

public interface ProphecyAstVisitor<T>
{
	public T visitAnyNode(ProphecyAstNode node);

	public T visitFile(ProphecyAstNode node);

	public T visitClassDef(ProphecyAstNode node);

	public T visitBlock(ProphecyAstNode node);

	public T visitDefBlock(ProphecyAstNode node);

	public T visitMember(ProphecyAstNode node);

	public T visitMethodDef(ProphecyAstNode node);

	public T visitId(ProphecyAstNode node);

	public T visitVarDecl(ProphecyAstNode node);

	public T visitParam(ProphecyAstNode node);

	public T visitFieldDef(ProphecyAstNode node);

	public T visitReturn(ProphecyAstNode node);

	public T visitAdd(ProphecyAstNode node);

	public T visitAssigment(ProphecyAstNode node);

	public T visitBitAnd(ProphecyAstNode node);

	public T visitBitOr(ProphecyAstNode node);

	public T visitBitXor(ProphecyAstNode node);

	public T visitCall(ProphecyAstNode node);

	public T visitCast(ProphecyAstNode node);

	public T visitCharLit(ProphecyAstNode node);

	public T visitDiv(ProphecyAstNode node);

	public T visitEof(ProphecyAstNode node);

	public T visitEq(ProphecyAstNode node);

	public T visitExprList(ProphecyAstNode node);

	public T visitFalseLit(ProphecyAstNode node);

	public T visitFloatLit(ProphecyAstNode node);

	public T visitGt(ProphecyAstNode node);

	public T visitGte(ProphecyAstNode node);

	public T visitIf(ProphecyAstNode node);

	public T visitIndex(ProphecyAstNode node);

	public T visitIntLit(ProphecyAstNode node);

	public T visitLogicAnd(ProphecyAstNode node);

	public T visitLogicOr(ProphecyAstNode node);

	public T visitLt(ProphecyAstNode node);

	public T visitLte(ProphecyAstNode node);

	public T visitModifier(ProphecyAstNode node);

	public T visitModifierList(ProphecyAstNode node);

	public T visitMul(ProphecyAstNode node);

	public T visitNeq(ProphecyAstNode node);

	public T visitParamList(ProphecyAstNode node);

	public T visitPostDec(ProphecyAstNode node);

	public T visitPostInc(ProphecyAstNode node);

	public T visitPreDec(ProphecyAstNode node);

	public T visitPreInc(ProphecyAstNode node);

	public T visitShiftL(ProphecyAstNode node);

	public T visitShiftR(ProphecyAstNode node);

	public T visitStringLit(ProphecyAstNode node);

	public T visitSub(ProphecyAstNode node);

	public T visitTrueLit(ProphecyAstNode node);

	public T visitUBitNot(ProphecyAstNode node);

	public T visitULogicNot(ProphecyAstNode node);

	public T visitUMinus(ProphecyAstNode node);

	public T visitUPlus(ProphecyAstNode node);

	public T visitWhile(ProphecyAstNode node);

}
