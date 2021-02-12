// Generated from Prophecy.g4 by ANTLR 4.2.2
package net.nlacombe.prophecy.parser.antlr4;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ProphecyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface ProphecyVisitor<T> extends ParseTreeVisitor<T>
{
	/**
	 * Visit a parse tree produced by {@link ProphecyParser#assignmentExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpr(@NotNull ProphecyParser.AssignmentExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#primarySelExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimarySelExpr(@NotNull ProphecyParser.PrimarySelExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#prefixExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(@NotNull ProphecyParser.PrefixExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#bitOrExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitOrExpr(@NotNull ProphecyParser.BitOrExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#returnStmt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(@NotNull ProphecyParser.ReturnStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#mulExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(@NotNull ProphecyParser.MulExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#stringlitPrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringlitPrimary(@NotNull ProphecyParser.StringlitPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#type}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull ProphecyParser.TypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#lhs}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs(@NotNull ProphecyParser.LhsContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#defblock}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefblock(@NotNull ProphecyParser.DefblockContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#selectorExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectorExpr(@NotNull ProphecyParser.SelectorExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#file}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(@NotNull ProphecyParser.FileContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#memberAccessSelExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessSelExpr(@NotNull ProphecyParser.MemberAccessSelExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#ifStmt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(@NotNull ProphecyParser.IfStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#equalityExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(@NotNull ProphecyParser.EqualityExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#methoddef}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethoddef(@NotNull ProphecyParser.MethoddefContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#truePrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTruePrimary(@NotNull ProphecyParser.TruePrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#bitAndExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitAndExpr(@NotNull ProphecyParser.BitAndExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#logicAndExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicAndExpr(@NotNull ProphecyParser.LogicAndExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#modifier}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(@NotNull ProphecyParser.ModifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#fielddef}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFielddef(@NotNull ProphecyParser.FielddefContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#callSelExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallSelExpr(@NotNull ProphecyParser.CallSelExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#bracketPrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketPrimary(@NotNull ProphecyParser.BracketPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#postfixExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(@NotNull ProphecyParser.PostfixExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#regVardecl}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegVardecl(@NotNull ProphecyParser.RegVardeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#bitXorExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitXorExpr(@NotNull ProphecyParser.BitXorExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#call}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(@NotNull ProphecyParser.CallContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#varDeclStmt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclStmt(@NotNull ProphecyParser.VarDeclStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#castExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpr(@NotNull ProphecyParser.CastExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#param}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(@NotNull ProphecyParser.ParamContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#IdPrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdPrimary(@NotNull ProphecyParser.IdPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#block}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull ProphecyParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#paramlist}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamlist(@NotNull ProphecyParser.ParamlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#arrayVardecl}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayVardecl(@NotNull ProphecyParser.ArrayVardeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#addExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(@NotNull ProphecyParser.AddExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#floatlitPrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatlitPrimary(@NotNull ProphecyParser.FloatlitPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#exprlist}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprlist(@NotNull ProphecyParser.ExprlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#falsePrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalsePrimary(@NotNull ProphecyParser.FalsePrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#thisPrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisPrimary(@NotNull ProphecyParser.ThisPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#defstat}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefstat(@NotNull ProphecyParser.DefstatContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#shiftExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpr(@NotNull ProphecyParser.ShiftExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#whileStmt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(@NotNull ProphecyParser.WhileStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#classdef}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassdef(@NotNull ProphecyParser.ClassdefContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#relationExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationExpr(@NotNull ProphecyParser.RelationExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#primitiveType}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(@NotNull ProphecyParser.PrimitiveTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#callStmt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(@NotNull ProphecyParser.CallStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#blockStmt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(@NotNull ProphecyParser.BlockStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#assignmentStmt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmt(@NotNull ProphecyParser.AssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#arrayAccessSelExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessSelExpr(@NotNull ProphecyParser.ArrayAccessSelExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#charlitPrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharlitPrimary(@NotNull ProphecyParser.CharlitPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#intlitPrimary}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntlitPrimary(@NotNull ProphecyParser.IntlitPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link ProphecyParser#logicOrExpr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicOrExpr(@NotNull ProphecyParser.LogicOrExprContext ctx);
}
