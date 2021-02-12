package net.nlacombe.prophecy.parser;

import net.nlacombe.prophecy.ast.ProphecyAstNode;
import net.nlacombe.prophecy.ast.ProphecyAstNodeType;
import net.nlacombe.prophecy.parser.antlr4.ProphecyBaseListener;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.AddExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ArrayAccessSelExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ArrayVardeclContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.AssignmentExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.AssignmentStmtContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.BitAndExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.BitOrExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.BitXorExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.BlockContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.CallContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.CallSelExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.CastExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.CharlitPrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ClassdefContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.DefblockContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.DefstatContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.EqualityExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ExprlistContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.FalsePrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.FielddefContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.FileContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.FloatlitPrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.IdPrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.IfStmtContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.IntlitPrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.LogicAndExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.LogicOrExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.MemberAccessSelExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.MethoddefContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.MulExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ParamContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ParamlistContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.PostfixExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.PrefixExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.RegVardeclContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.RelationExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ReturnStmtContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ShiftExprContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.StringlitPrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.ThisPrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.TruePrimaryContext;
import net.nlacombe.prophecy.parser.antlr4.ProphecyParser.WhileStmtContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class AstBuilder extends ProphecyBaseListener
{
	private ProphecyAstNode root;
	private ProphecyAstNode parent;

	private Map<Class<? extends ParserRuleContext>, ProphecyAstNodeType> rootClasses;

	public AstBuilder()
	{
		rootClasses = new HashMap<Class<? extends ParserRuleContext>, ProphecyAstNodeType>();

		buildRootMap();
	}

	public ProphecyAstNode getTreeRoot()
	{
		return root;
	}

	private ProphecyAstNode createAStNode(ProphecyAstNodeType type, ParserRuleContext ctx)
	{
		return type.getAst(ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
	}

	private ProphecyAstNode createAStNodeWithText(ProphecyAstNodeType type, ParserRuleContext ctx)
	{
		return type.getAst(ctx.getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
	}

	private ProphecyAstNode createAStNodeWithText(ProphecyAstNodeType type, ParserRuleContext ctx, String text)
	{
		return type.getAst(text, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
	}

	private ProphecyAstNode createAStNodeWithText(ProphecyAstNodeType type, Token token)
	{
		return type.getAst(token.getText(), token.getLine(), token.getCharPositionInLine() + 1);
	}

	private ProphecyAstNode createAStNodeWithText(ProphecyAstNodeType type, TerminalNode terminal)
	{
		return type.getAst(terminal.getText(), terminal.getSymbol().getLine(), terminal.getSymbol().getCharPositionInLine() + 1);
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx)
	{
		ProphecyAstNodeType nodeType = rootClasses.get(ctx.getClass());

		if (nodeType != null) {
			ProphecyAstNode node = createAStNode(nodeType, ctx);

			addContextChildrenOnEnter(node, ctx);

			if (parent != null)
				parent.addChild(node);

			parent = node;

			if (root == null)
				root = parent;
		}
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx)
	{
		addContextChildrenOnExit(parent, ctx); //parent is the current node

		if (rootClasses.containsKey(ctx.getClass())) {
			parent = parent.getParent();
		}
	}

	private void addContextChildrenOnEnter(ProphecyAstNode node, ParserRuleContext ctx)
	{
		if (ctx instanceof ClassdefContext) {
			ClassdefContext classCtx = (ClassdefContext) ctx;

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, classCtx.name));

			if (classCtx.superClassName != null) {
				node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, classCtx.superClassName));
			} else {
				node.addChild(ProphecyAstNodeType.NULL.getAst());
			}
		} else if (ctx instanceof FielddefContext) {
			FielddefContext fieldCtx = (FielddefContext) ctx;
			DefstatContext defStatCtx = (DefstatContext) ctx.getParent();

			node.addChild(getModiferList(defStatCtx));
			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, fieldCtx.type()));
			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, fieldCtx.name));
		} else if (ctx instanceof MethoddefContext) {
			MethoddefContext methodCtx = (MethoddefContext) ctx;
			DefstatContext defStatCtx = (DefstatContext) ctx.getParent();

			node.addChild(getModiferList(defStatCtx));

			if (methodCtx.type() == null)
				node.addChild(ProphecyAstNodeType.ID.getAst("void"));
			else
				node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, methodCtx.type()));

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, methodCtx.name));

			if (methodCtx.paramlist() == null) {
				node.addChild(ProphecyAstNodeType.PARAMLIST.getAst());
			}
		} else if (ctx instanceof ParamContext) {
			ParamContext paramCtx = (ParamContext) ctx;

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, paramCtx.type()));
			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, paramCtx.name));
		} else if (ctx instanceof FielddefContext) {
			FielddefContext fieldDefCtx = (FielddefContext) ctx;

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, fieldDefCtx.type()));
			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, fieldDefCtx.name));
		} else if (ctx instanceof RegVardeclContext) {
			RegVardeclContext regVardeclCtx = (RegVardeclContext) ctx;

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, regVardeclCtx.type()));
			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, regVardeclCtx.name));
		} else if (ctx instanceof ArrayVardeclContext) {
			ArrayVardeclContext arrayVardeclCtx = (ArrayVardeclContext) ctx;

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, arrayVardeclCtx.type(), arrayVardeclCtx.type().getText() + "[]"));
			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, arrayVardeclCtx.name));
		} else if (ctx instanceof IntlitPrimaryContext) {
			IntlitPrimaryContext intCtx = (IntlitPrimaryContext) ctx;

			node.setText(intCtx.getText());
		} else if (ctx instanceof CharlitPrimaryContext) {
			CharlitPrimaryContext charCtx = (CharlitPrimaryContext) ctx;

			node.setText(charCtx.getText());
		} else if (ctx instanceof FloatlitPrimaryContext) {
			FloatlitPrimaryContext floatCtx = (FloatlitPrimaryContext) ctx;

			node.setText(floatCtx.getText());
		} else if (ctx instanceof StringlitPrimaryContext) {
			StringlitPrimaryContext stringCtx = (StringlitPrimaryContext) ctx;

			node.setText(stringCtx.getText());
		} else if (ctx instanceof IdPrimaryContext) {
			IdPrimaryContext idCtx = (IdPrimaryContext) ctx;

			node.setText(idCtx.getText());
		} else if (ctx instanceof ThisPrimaryContext) {
			ThisPrimaryContext thisCtx = (ThisPrimaryContext) ctx;

			node.setText(thisCtx.getText());
		} else if (ctx instanceof AddExprContext) {
			AddExprContext addCtx = (AddExprContext) ctx;

			if (addCtx.operator.getText().equals("+"))
				node.setTypeAndText(ProphecyAstNodeType.ADD);
			else
				node.setTypeAndText(ProphecyAstNodeType.SUB);
		} else if (ctx instanceof MulExprContext) {
			MulExprContext mulCtx = (MulExprContext) ctx;

			if (mulCtx.operator.getText().equals("*"))
				node.setTypeAndText(ProphecyAstNodeType.MUL);
			else
				node.setTypeAndText(ProphecyAstNodeType.DIV);
		} else if (ctx instanceof EqualityExprContext) {
			EqualityExprContext eqCtx = (EqualityExprContext) ctx;

			if (eqCtx.operator.getText().equals("=="))
				node.setTypeAndText(ProphecyAstNodeType.EQ);
			else
				node.setTypeAndText(ProphecyAstNodeType.NEQ);
		} else if (ctx instanceof PostfixExprContext) {
			PostfixExprContext postCtx = (PostfixExprContext) ctx;

			if (postCtx.operator.getText().equals("++"))
				node.setTypeAndText(ProphecyAstNodeType.POSTINC);
			else
				node.setTypeAndText(ProphecyAstNodeType.POSTDEC);
		} else if (ctx instanceof PrefixExprContext) {
			PrefixExprContext preCtx = (PrefixExprContext) ctx;

			switch (preCtx.operator.getText()) {
				case "++":
					node.setTypeAndText(ProphecyAstNodeType.PREINC);
					break;

				case "--":
					node.setTypeAndText(ProphecyAstNodeType.PREDEC);
					break;

				case "+":
					node.setTypeAndText(ProphecyAstNodeType.UPLUS);
					break;

				case "-":
					node.setTypeAndText(ProphecyAstNodeType.UMINUS);
					break;

				case "!":
					node.setTypeAndText(ProphecyAstNodeType.ULOGICNOT);
					break;

				case "~":
					node.setTypeAndText(ProphecyAstNodeType.UBITNOT);
					break;
			}
		} else if (ctx instanceof RelationExprContext) {
			RelationExprContext relCtx = (RelationExprContext) ctx;

			switch (relCtx.operator.getText()) {
				case ">":
					node.setTypeAndText(ProphecyAstNodeType.GT);
					break;

				case "<":
					node.setTypeAndText(ProphecyAstNodeType.LT);
					break;

				case ">=":
					node.setTypeAndText(ProphecyAstNodeType.GTE);
					break;

				case "<=":
					node.setTypeAndText(ProphecyAstNodeType.GTE);
					break;
			}
		} else if (ctx instanceof ShiftExprContext) {
			ShiftExprContext shiftCtx = (ShiftExprContext) ctx;

			if (shiftCtx.operator.getText().equals("<<"))
				node.setTypeAndText(ProphecyAstNodeType.SHIFTL);
			else
				node.setTypeAndText(ProphecyAstNodeType.SHIFTR);
		} else if (ctx instanceof CastExprContext) {
			CastExprContext castCtx = (CastExprContext) ctx;

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, castCtx.type()));
		}
	}

	private void addContextChildrenOnExit(ProphecyAstNode node, ParserRuleContext ctx)
	{
		if (ctx instanceof MemberAccessSelExprContext) {
			MemberAccessSelExprContext memberCtx = (MemberAccessSelExprContext) ctx;

			node.addChild(createAStNodeWithText(ProphecyAstNodeType.ID, memberCtx.ID()));
		}
	}

	private ProphecyAstNode getModiferList(DefstatContext ctx)
	{
		ProphecyAstNode modifierList = ProphecyAstNodeType.MODIFIERLIST.getAst();

		if (ctx.modifier() == null)
			return modifierList;

		if (ctx.modifier().static_ != null)
			modifierList.addChild(createAStNodeWithText(ProphecyAstNodeType.MODIFIER, ctx.modifier().static_));

		return modifierList;
	}

	private void buildRootMap()
	{
		rootClasses.put(FileContext.class, ProphecyAstNodeType.FILE);
		rootClasses.put(ClassdefContext.class, ProphecyAstNodeType.CLASSDEF);
		rootClasses.put(DefblockContext.class, ProphecyAstNodeType.DEFBLOCK);
		rootClasses.put(FielddefContext.class, ProphecyAstNodeType.FIELDDEF);
		rootClasses.put(MethoddefContext.class, ProphecyAstNodeType.METHODDEF);
		rootClasses.put(ParamlistContext.class, ProphecyAstNodeType.PARAMLIST);
		rootClasses.put(ParamContext.class, ProphecyAstNodeType.PARAM);
		rootClasses.put(ExprlistContext.class, ProphecyAstNodeType.EXPRLIST);
		rootClasses.put(BlockContext.class, ProphecyAstNodeType.BLOCK);
		rootClasses.put(RegVardeclContext.class, ProphecyAstNodeType.VARDECL);
		rootClasses.put(ArrayVardeclContext.class, ProphecyAstNodeType.VARDECL);
		rootClasses.put(IfStmtContext.class, ProphecyAstNodeType.IF);
		rootClasses.put(WhileStmtContext.class, ProphecyAstNodeType.WHILE);
		rootClasses.put(ReturnStmtContext.class, ProphecyAstNodeType.RETURN);
		rootClasses.put(AssignmentStmtContext.class, ProphecyAstNodeType.ASSIGMENT);
		rootClasses.put(AssignmentExprContext.class, ProphecyAstNodeType.ASSIGMENT);
		rootClasses.put(AddExprContext.class, ProphecyAstNodeType.ADD);
		rootClasses.put(MulExprContext.class, ProphecyAstNodeType.MUL);
		rootClasses.put(EqualityExprContext.class, ProphecyAstNodeType.EQ);
		rootClasses.put(PostfixExprContext.class, ProphecyAstNodeType.POSTINC);
		rootClasses.put(PrefixExprContext.class, ProphecyAstNodeType.PREINC);
		rootClasses.put(LogicOrExprContext.class, ProphecyAstNodeType.LOGICOR);
		rootClasses.put(LogicAndExprContext.class, ProphecyAstNodeType.LOGICAND);
		rootClasses.put(BitOrExprContext.class, ProphecyAstNodeType.BITOR);
		rootClasses.put(BitXorExprContext.class, ProphecyAstNodeType.BITXOR);
		rootClasses.put(BitAndExprContext.class, ProphecyAstNodeType.BITAND);
		rootClasses.put(RelationExprContext.class, ProphecyAstNodeType.GT);
		rootClasses.put(ShiftExprContext.class, ProphecyAstNodeType.SHIFTL);
		rootClasses.put(CastExprContext.class, ProphecyAstNodeType.CAST);

		rootClasses.put(MemberAccessSelExprContext.class, ProphecyAstNodeType.MEMBER);
		rootClasses.put(ArrayAccessSelExprContext.class, ProphecyAstNodeType.INDEX);
		rootClasses.put(CallSelExprContext.class, ProphecyAstNodeType.CALL);
		rootClasses.put(CallContext.class, ProphecyAstNodeType.CALL);

		rootClasses.put(IdPrimaryContext.class, ProphecyAstNodeType.ID);
		rootClasses.put(ThisPrimaryContext.class, ProphecyAstNodeType.ID);
		rootClasses.put(CharlitPrimaryContext.class, ProphecyAstNodeType.CHARLIT);
		rootClasses.put(IntlitPrimaryContext.class, ProphecyAstNodeType.INTLIT);
		rootClasses.put(FloatlitPrimaryContext.class, ProphecyAstNodeType.FLOATLIT);
		rootClasses.put(StringlitPrimaryContext.class, ProphecyAstNodeType.STRINGLIT);
		rootClasses.put(TruePrimaryContext.class, ProphecyAstNodeType.TRUELIT);
		rootClasses.put(FalsePrimaryContext.class, ProphecyAstNodeType.FALSELIT);
	}
}
