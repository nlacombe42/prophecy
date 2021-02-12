// Generated from Prophecy.g4 by ANTLR 4.2.2
package net.nlacombe.prophecy.parser.antlr4;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ProphecyParser}.
 */
public interface ProphecyListener extends ParseTreeListener
{
	/**
	 * Enter a parse tree produced by {@link ProphecyParser#assignmentExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpr(@NotNull ProphecyParser.AssignmentExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#assignmentExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpr(@NotNull ProphecyParser.AssignmentExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#primarySelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPrimarySelExpr(@NotNull ProphecyParser.PrimarySelExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#primarySelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPrimarySelExpr(@NotNull ProphecyParser.PrimarySelExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#prefixExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(@NotNull ProphecyParser.PrefixExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#prefixExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(@NotNull ProphecyParser.PrefixExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#bitOrExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBitOrExpr(@NotNull ProphecyParser.BitOrExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#bitOrExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBitOrExpr(@NotNull ProphecyParser.BitOrExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#returnStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(@NotNull ProphecyParser.ReturnStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#returnStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(@NotNull ProphecyParser.ReturnStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#mulExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMulExpr(@NotNull ProphecyParser.MulExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#mulExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMulExpr(@NotNull ProphecyParser.MulExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#stringlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterStringlitPrimary(@NotNull ProphecyParser.StringlitPrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#stringlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitStringlitPrimary(@NotNull ProphecyParser.StringlitPrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#type}.
	 *
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull ProphecyParser.TypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#type}.
	 *
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull ProphecyParser.TypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#lhs}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLhs(@NotNull ProphecyParser.LhsContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#lhs}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLhs(@NotNull ProphecyParser.LhsContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#defblock}.
	 *
	 * @param ctx the parse tree
	 */
	void enterDefblock(@NotNull ProphecyParser.DefblockContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#defblock}.
	 *
	 * @param ctx the parse tree
	 */
	void exitDefblock(@NotNull ProphecyParser.DefblockContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#selectorExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterSelectorExpr(@NotNull ProphecyParser.SelectorExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#selectorExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitSelectorExpr(@NotNull ProphecyParser.SelectorExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#file}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFile(@NotNull ProphecyParser.FileContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#file}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFile(@NotNull ProphecyParser.FileContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#memberAccessSelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMemberAccessSelExpr(@NotNull ProphecyParser.MemberAccessSelExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#memberAccessSelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMemberAccessSelExpr(@NotNull ProphecyParser.MemberAccessSelExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#ifStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterIfStmt(@NotNull ProphecyParser.IfStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#ifStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitIfStmt(@NotNull ProphecyParser.IfStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#equalityExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(@NotNull ProphecyParser.EqualityExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#equalityExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(@NotNull ProphecyParser.EqualityExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#methoddef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMethoddef(@NotNull ProphecyParser.MethoddefContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#methoddef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMethoddef(@NotNull ProphecyParser.MethoddefContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#truePrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterTruePrimary(@NotNull ProphecyParser.TruePrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#truePrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitTruePrimary(@NotNull ProphecyParser.TruePrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#bitAndExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBitAndExpr(@NotNull ProphecyParser.BitAndExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#bitAndExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBitAndExpr(@NotNull ProphecyParser.BitAndExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#logicAndExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLogicAndExpr(@NotNull ProphecyParser.LogicAndExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#logicAndExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLogicAndExpr(@NotNull ProphecyParser.LogicAndExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#modifier}.
	 *
	 * @param ctx the parse tree
	 */
	void enterModifier(@NotNull ProphecyParser.ModifierContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#modifier}.
	 *
	 * @param ctx the parse tree
	 */
	void exitModifier(@NotNull ProphecyParser.ModifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#fielddef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFielddef(@NotNull ProphecyParser.FielddefContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#fielddef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFielddef(@NotNull ProphecyParser.FielddefContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#callSelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterCallSelExpr(@NotNull ProphecyParser.CallSelExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#callSelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitCallSelExpr(@NotNull ProphecyParser.CallSelExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#bracketPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBracketPrimary(@NotNull ProphecyParser.BracketPrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#bracketPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBracketPrimary(@NotNull ProphecyParser.BracketPrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#postfixExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(@NotNull ProphecyParser.PostfixExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#postfixExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(@NotNull ProphecyParser.PostfixExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#regVardecl}.
	 *
	 * @param ctx the parse tree
	 */
	void enterRegVardecl(@NotNull ProphecyParser.RegVardeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#regVardecl}.
	 *
	 * @param ctx the parse tree
	 */
	void exitRegVardecl(@NotNull ProphecyParser.RegVardeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#bitXorExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBitXorExpr(@NotNull ProphecyParser.BitXorExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#bitXorExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBitXorExpr(@NotNull ProphecyParser.BitXorExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#call}.
	 *
	 * @param ctx the parse tree
	 */
	void enterCall(@NotNull ProphecyParser.CallContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#call}.
	 *
	 * @param ctx the parse tree
	 */
	void exitCall(@NotNull ProphecyParser.CallContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#varDeclStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterVarDeclStmt(@NotNull ProphecyParser.VarDeclStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#varDeclStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitVarDeclStmt(@NotNull ProphecyParser.VarDeclStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#castExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterCastExpr(@NotNull ProphecyParser.CastExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#castExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitCastExpr(@NotNull ProphecyParser.CastExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#param}.
	 *
	 * @param ctx the parse tree
	 */
	void enterParam(@NotNull ProphecyParser.ParamContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#param}.
	 *
	 * @param ctx the parse tree
	 */
	void exitParam(@NotNull ProphecyParser.ParamContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#IdPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterIdPrimary(@NotNull ProphecyParser.IdPrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#IdPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitIdPrimary(@NotNull ProphecyParser.IdPrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#block}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull ProphecyParser.BlockContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#block}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBlock(@NotNull ProphecyParser.BlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#paramlist}.
	 *
	 * @param ctx the parse tree
	 */
	void enterParamlist(@NotNull ProphecyParser.ParamlistContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#paramlist}.
	 *
	 * @param ctx the parse tree
	 */
	void exitParamlist(@NotNull ProphecyParser.ParamlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#arrayVardecl}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrayVardecl(@NotNull ProphecyParser.ArrayVardeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#arrayVardecl}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrayVardecl(@NotNull ProphecyParser.ArrayVardeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#addExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAddExpr(@NotNull ProphecyParser.AddExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#addExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAddExpr(@NotNull ProphecyParser.AddExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#floatlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFloatlitPrimary(@NotNull ProphecyParser.FloatlitPrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#floatlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFloatlitPrimary(@NotNull ProphecyParser.FloatlitPrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#exprlist}.
	 *
	 * @param ctx the parse tree
	 */
	void enterExprlist(@NotNull ProphecyParser.ExprlistContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#exprlist}.
	 *
	 * @param ctx the parse tree
	 */
	void exitExprlist(@NotNull ProphecyParser.ExprlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#falsePrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFalsePrimary(@NotNull ProphecyParser.FalsePrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#falsePrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFalsePrimary(@NotNull ProphecyParser.FalsePrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#thisPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterThisPrimary(@NotNull ProphecyParser.ThisPrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#thisPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitThisPrimary(@NotNull ProphecyParser.ThisPrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#defstat}.
	 *
	 * @param ctx the parse tree
	 */
	void enterDefstat(@NotNull ProphecyParser.DefstatContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#defstat}.
	 *
	 * @param ctx the parse tree
	 */
	void exitDefstat(@NotNull ProphecyParser.DefstatContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#shiftExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterShiftExpr(@NotNull ProphecyParser.ShiftExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#shiftExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitShiftExpr(@NotNull ProphecyParser.ShiftExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#whileStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(@NotNull ProphecyParser.WhileStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#whileStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(@NotNull ProphecyParser.WhileStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#classdef}.
	 *
	 * @param ctx the parse tree
	 */
	void enterClassdef(@NotNull ProphecyParser.ClassdefContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#classdef}.
	 *
	 * @param ctx the parse tree
	 */
	void exitClassdef(@NotNull ProphecyParser.ClassdefContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#relationExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterRelationExpr(@NotNull ProphecyParser.RelationExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#relationExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitRelationExpr(@NotNull ProphecyParser.RelationExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#primitiveType}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(@NotNull ProphecyParser.PrimitiveTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#primitiveType}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(@NotNull ProphecyParser.PrimitiveTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#callStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterCallStmt(@NotNull ProphecyParser.CallStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#callStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitCallStmt(@NotNull ProphecyParser.CallStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#blockStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(@NotNull ProphecyParser.BlockStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#blockStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(@NotNull ProphecyParser.BlockStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#assignmentStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAssignmentStmt(@NotNull ProphecyParser.AssignmentStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#assignmentStmt}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAssignmentStmt(@NotNull ProphecyParser.AssignmentStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#arrayAccessSelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterArrayAccessSelExpr(@NotNull ProphecyParser.ArrayAccessSelExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#arrayAccessSelExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitArrayAccessSelExpr(@NotNull ProphecyParser.ArrayAccessSelExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#charlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterCharlitPrimary(@NotNull ProphecyParser.CharlitPrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#charlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitCharlitPrimary(@NotNull ProphecyParser.CharlitPrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#intlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void enterIntlitPrimary(@NotNull ProphecyParser.IntlitPrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#intlitPrimary}.
	 *
	 * @param ctx the parse tree
	 */
	void exitIntlitPrimary(@NotNull ProphecyParser.IntlitPrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link ProphecyParser#logicOrExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLogicOrExpr(@NotNull ProphecyParser.LogicOrExprContext ctx);

	/**
	 * Exit a parse tree produced by {@link ProphecyParser#logicOrExpr}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLogicOrExpr(@NotNull ProphecyParser.LogicOrExprContext ctx);
}
