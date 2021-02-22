package net.nlacombe.prophecy.v1.ast;

public enum ProphecyAstNodeType
{
	NULL, EOF, ADD, ASSIGMENT, BITAND, BITOR, BITXOR, BLOCK, CALL, CAST, CLASSDEF, DEFBLOCK, DIV, EQ, EXPRLIST, FIELDDEF, FILE, GT, GTE, IF, INDEX, LOGICAND, LOGICOR, LT, LTE, MEMBER, METHODDEF, MODIFIER, MODIFIERLIST, MUL, NEQ, PARAM, PARAMLIST, POSTDEC, POSTINC, PREDEC, PREINC, RETURN, SHIFTL, SHIFTR, SUB, UBITNOT, ULOGICNOT, UMINUS, UPLUS, VARDECL, WHILE,

	ID, INTLIT, CHARLIT, FLOATLIT, STRINGLIT, TRUELIT, FALSELIT;

	public ProphecyAstNode getAst()
	{
		return new ProphecyAstNode(this);
	}

	public ProphecyAstNode getAst(int lineNumber, int charPositionInLine)
	{
		return new ProphecyAstNode(this, lineNumber, charPositionInLine);
	}

	public ProphecyAstNode getAst(String text)
	{
		return new ProphecyAstNode(this, text);
	}

	public ProphecyAstNode getAst(String text, int lineNumber, int charPositionInLine)
	{
		return new ProphecyAstNode(this, text, lineNumber, charPositionInLine);
	}
}
