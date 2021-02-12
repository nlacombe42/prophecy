// Generated from Prophecy.g4 by ANTLR 4.2.2
package net.nlacombe.prophecy.parser.antlr4;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProphecyParser extends Parser
{
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	public static final int
			T__31 = 1, T__30 = 2, T__29 = 3, T__28 = 4, T__27 = 5, T__26 = 6, T__25 = 7, T__24 = 8,
			T__23 = 9, T__22 = 10, T__21 = 11, T__20 = 12, T__19 = 13, T__18 = 14, T__17 = 15, T__16 = 16,
			T__15 = 17, T__14 = 18, T__13 = 19, T__12 = 20, T__11 = 21, T__10 = 22, T__9 = 23, T__8 = 24,
			T__7 = 25, T__6 = 26, T__5 = 27, T__4 = 28, T__3 = 29, T__2 = 30, T__1 = 31, T__0 = 32,
			CLASS = 33, EXTENDS = 34, THIS = 35, STATIC = 36, IF = 37, WHILE = 38, ELSE = 39, RETURN = 40,
			TRUE = 41, FALSE = 42, FLOAT = 43, INT = 44, CHAR = 45, BOOL = 46, VOID = 47, ID = 48,
			STRINGLIT = 49, INTLIT = 50, FLOATLIT = 51, CHARLIT = 52, WS = 53, SL_COMMENT = 54,
			ML_COMMENT = 55;
	public static final String[] tokenNames = {
			"<INVALID>", "']'", "'&'", "','", "'*'", "'-'", "'['", "'('", "'<'", "'--'",
			"'!='", "'<='", "'<<'", "'{'", "'}'", "'++'", "'>>'", "'[]'", "'^'", "')'",
			"'.'", "'+'", "'='", "';'", "'&&'", "'||'", "'>'", "'/'", "'=='", "'~'",
			"'>='", "'|'", "'!'", "'class'", "'extends'", "'this'", "'static'", "'if'",
			"'while'", "'else'", "'return'", "'true'", "'false'", "'float'", "'int'",
			"'char'", "'bool'", "'void'", "ID", "STRINGLIT", "INTLIT", "FLOATLIT",
			"CHARLIT", "WS", "SL_COMMENT", "ML_COMMENT"
	};
	public static final int
			RULE_file = 0, RULE_classdef = 1, RULE_defblock = 2, RULE_defstat = 3,
			RULE_modifier = 4, RULE_fielddef = 5, RULE_methoddef = 6, RULE_paramlist = 7,
			RULE_param = 8, RULE_block = 9, RULE_stmt = 10, RULE_lhs = 11, RULE_vardecl = 12,
			RULE_exprlist = 13, RULE_expr = 14, RULE_selexpr = 15, RULE_call = 16,
			RULE_primary = 17, RULE_type = 18, RULE_primitiveType = 19;
	public static final String[] ruleNames = {
			"file", "classdef", "defblock", "defstat", "modifier", "fielddef", "methoddef",
			"paramlist", "param", "block", "stmt", "lhs", "vardecl", "exprlist", "expr",
			"selexpr", "call", "primary", "type", "primitiveType"
	};

	@Override
	public String getGrammarFileName()
	{
		return "Prophecy.g4";
	}

	@Override
	public String[] getTokenNames()
	{
		return tokenNames;
	}

	@Override
	public String[] getRuleNames()
	{
		return ruleNames;
	}

	@Override
	public String getSerializedATN()
	{
		return _serializedATN;
	}

	@Override
	public ATN getATN()
	{
		return _ATN;
	}

	public ProphecyParser(TokenStream input)
	{
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class FileContext extends ParserRuleContext
	{
		public TerminalNode EOF()
		{
			return getToken(ProphecyParser.EOF, 0);
		}

		public ClassdefContext classdef()
		{
			return getRuleContext(ClassdefContext.class, 0);
		}

		public FileContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_file;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterFile(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitFile(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException
	{
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(40);
				classdef();
				setState(41);
				match(EOF);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassdefContext extends ParserRuleContext
	{
		public Token name;
		public Token superClassName;

		public DefblockContext defblock()
		{
			return getRuleContext(DefblockContext.class, 0);
		}

		public List<TerminalNode> ID()
		{
			return getTokens(ProphecyParser.ID);
		}

		public TerminalNode EXTENDS()
		{
			return getToken(ProphecyParser.EXTENDS, 0);
		}

		public TerminalNode ID(int i)
		{
			return getToken(ProphecyParser.ID, i);
		}

		public TerminalNode CLASS()
		{
			return getToken(ProphecyParser.CLASS, 0);
		}

		public ClassdefContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_classdef;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterClassdef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitClassdef(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitClassdef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassdefContext classdef() throws RecognitionException
	{
		ClassdefContext _localctx = new ClassdefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classdef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(43);
				match(CLASS);
				setState(44);
				((ClassdefContext) _localctx).name = match(ID);
				setState(47);
				_la = _input.LA(1);
				if (_la == EXTENDS) {
					{
						setState(45);
						match(EXTENDS);
						setState(46);
						((ClassdefContext) _localctx).superClassName = match(ID);
					}
				}

				setState(49);
				defblock();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefblockContext extends ParserRuleContext
	{
		public List<DefstatContext> defstat()
		{
			return getRuleContexts(DefstatContext.class);
		}

		public DefstatContext defstat(int i)
		{
			return getRuleContext(DefstatContext.class, i);
		}

		public DefblockContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_defblock;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterDefblock(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitDefblock(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitDefblock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefblockContext defblock() throws RecognitionException
	{
		DefblockContext _localctx = new DefblockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_defblock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(51);
				match(13);
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(52);
							defstat();
						}
					}
					setState(55);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STATIC) | (1L << FLOAT) | (1L << INT) | (1L << CHAR) | (1L << BOOL) | (1L << VOID) | (1L << ID))) != 0));
				setState(57);
				match(14);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefstatContext extends ParserRuleContext
	{
		public MethoddefContext methoddef()
		{
			return getRuleContext(MethoddefContext.class, 0);
		}

		public ModifierContext modifier()
		{
			return getRuleContext(ModifierContext.class, 0);
		}

		public FielddefContext fielddef()
		{
			return getRuleContext(FielddefContext.class, 0);
		}

		public DefstatContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_defstat;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterDefstat(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitDefstat(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitDefstat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefstatContext defstat() throws RecognitionException
	{
		DefstatContext _localctx = new DefstatContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_defstat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(60);
				_la = _input.LA(1);
				if (_la == STATIC) {
					{
						setState(59);
						modifier();
					}
				}

				setState(64);
				switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
					case 1: {
						setState(62);
						fielddef();
					}
					break;

					case 2: {
						setState(63);
						methoddef();
					}
					break;
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifierContext extends ParserRuleContext
	{
		public Token static_;

		public TerminalNode STATIC()
		{
			return getToken(ProphecyParser.STATIC, 0);
		}

		public ModifierContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_modifier;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterModifier(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitModifier(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException
	{
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_modifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(66);
				((ModifierContext) _localctx).static_ = match(STATIC);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FielddefContext extends ParserRuleContext
	{
		public Token name;

		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public TypeContext type()
		{
			return getRuleContext(TypeContext.class, 0);
		}

		public FielddefContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_fielddef;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterFielddef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitFielddef(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitFielddef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FielddefContext fielddef() throws RecognitionException
	{
		FielddefContext _localctx = new FielddefContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_fielddef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(68);
				type();
				setState(69);
				((FielddefContext) _localctx).name = match(ID);
				setState(70);
				match(23);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethoddefContext extends ParserRuleContext
	{
		public Token name;

		public ParamlistContext paramlist()
		{
			return getRuleContext(ParamlistContext.class, 0);
		}

		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public TypeContext type()
		{
			return getRuleContext(TypeContext.class, 0);
		}

		public BlockContext block()
		{
			return getRuleContext(BlockContext.class, 0);
		}

		public MethoddefContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_methoddef;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterMethoddef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitMethoddef(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitMethoddef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethoddefContext methoddef() throws RecognitionException
	{
		MethoddefContext _localctx = new MethoddefContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_methoddef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(73);
				switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
					case 1: {
						setState(72);
						type();
					}
					break;
				}
				setState(75);
				((MethoddefContext) _localctx).name = match(ID);
				setState(76);
				match(7);
				setState(78);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FLOAT) | (1L << INT) | (1L << CHAR) | (1L << BOOL) | (1L << VOID) | (1L << ID))) != 0)) {
					{
						setState(77);
						paramlist();
					}
				}

				setState(80);
				match(19);
				setState(81);
				block();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamlistContext extends ParserRuleContext
	{
		public List<ParamContext> param()
		{
			return getRuleContexts(ParamContext.class);
		}

		public ParamContext param(int i)
		{
			return getRuleContext(ParamContext.class, i);
		}

		public ParamlistContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_paramlist;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterParamlist(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitParamlist(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitParamlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamlistContext paramlist() throws RecognitionException
	{
		ParamlistContext _localctx = new ParamlistContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_paramlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(83);
				param();
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == 3) {
					{
						{
							setState(84);
							match(3);
							setState(85);
							param();
						}
					}
					setState(90);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext
	{
		public Token name;

		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public TypeContext type()
		{
			return getRuleContext(TypeContext.class, 0);
		}

		public ParamContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_param;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterParam(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitParam(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException
	{
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(91);
				type();
				setState(92);
				((ParamContext) _localctx).name = match(ID);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext
	{
		public StmtContext stmt(int i)
		{
			return getRuleContext(StmtContext.class, i);
		}

		public List<StmtContext> stmt()
		{
			return getRuleContexts(StmtContext.class);
		}

		public BlockContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_block;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterBlock(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitBlock(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException
	{
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(94);
				match(13);
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 7) | (1L << 13) | (1L << THIS) | (1L << IF) | (1L << WHILE) | (1L << RETURN) | (1L << TRUE) | (1L << FALSE) | (1L << FLOAT) | (1L << INT) | (1L << CHAR) | (1L << BOOL) | (1L << VOID) | (1L << ID) | (1L << STRINGLIT) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << CHARLIT))) != 0)) {
					{
						{
							setState(95);
							stmt();
						}
					}
					setState(100);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(101);
				match(14);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext
	{
		public StmtContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_stmt;
		}

		public StmtContext()
		{
		}

		public void copyFrom(StmtContext ctx)
		{
			super.copyFrom(ctx);
		}
	}

	public static class WhileStmtContext extends StmtContext
	{
		public TerminalNode WHILE()
		{
			return getToken(ProphecyParser.WHILE, 0);
		}

		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public StmtContext stmt()
		{
			return getRuleContext(StmtContext.class, 0);
		}

		public WhileStmtContext(StmtContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterWhileStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitWhileStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class VarDeclStmtContext extends StmtContext
	{
		public VardeclContext vardecl()
		{
			return getRuleContext(VardeclContext.class, 0);
		}

		public VarDeclStmtContext(StmtContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterVarDeclStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitVarDeclStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitVarDeclStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class CallStmtContext extends StmtContext
	{
		public CallContext call()
		{
			return getRuleContext(CallContext.class, 0);
		}

		public CallStmtContext(StmtContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterCallStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitCallStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitCallStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class AssignmentStmtContext extends StmtContext
	{
		public ExprContext rhs;

		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public LhsContext lhs()
		{
			return getRuleContext(LhsContext.class, 0);
		}

		public AssignmentStmtContext(StmtContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterAssignmentStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitAssignmentStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitAssignmentStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BlockStmtContext extends StmtContext
	{
		public BlockContext block()
		{
			return getRuleContext(BlockContext.class, 0);
		}

		public BlockStmtContext(StmtContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterBlockStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitBlockStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitBlockStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ReturnStmtContext extends StmtContext
	{
		public ExprContext value;

		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public TerminalNode RETURN()
		{
			return getToken(ProphecyParser.RETURN, 0);
		}

		public ReturnStmtContext(StmtContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterReturnStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitReturnStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class IfStmtContext extends StmtContext
	{
		public ExprContext cond;

		public TerminalNode IF()
		{
			return getToken(ProphecyParser.IF, 0);
		}

		public StmtContext stmt(int i)
		{
			return getRuleContext(StmtContext.class, i);
		}

		public TerminalNode ELSE()
		{
			return getToken(ProphecyParser.ELSE, 0);
		}

		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public List<StmtContext> stmt()
		{
			return getRuleContexts(StmtContext.class);
		}

		public IfStmtContext(StmtContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterIfStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitIfStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException
	{
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_stmt);
		int _la;
		try {
			setState(133);
			switch (getInterpreter().adaptivePredict(_input, 10, _ctx)) {
				case 1:
					_localctx = new BlockStmtContext(_localctx);
					enterOuterAlt(_localctx, 1);
				{
					setState(103);
					block();
				}
				break;

				case 2:
					_localctx = new VarDeclStmtContext(_localctx);
					enterOuterAlt(_localctx, 2);
				{
					setState(104);
					vardecl();
				}
				break;

				case 3:
					_localctx = new IfStmtContext(_localctx);
					enterOuterAlt(_localctx, 3);
				{
					setState(105);
					match(IF);
					setState(106);
					match(7);
					setState(107);
					((IfStmtContext) _localctx).cond = expr(0);
					setState(108);
					match(19);
					setState(109);
					stmt();
					setState(112);
					switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
						case 1: {
							setState(110);
							match(ELSE);
							setState(111);
							stmt();
						}
						break;
					}
				}
				break;

				case 4:
					_localctx = new WhileStmtContext(_localctx);
					enterOuterAlt(_localctx, 4);
				{
					setState(114);
					match(WHILE);
					setState(115);
					match(7);
					setState(116);
					expr(0);
					setState(117);
					match(19);
					setState(118);
					stmt();
				}
				break;

				case 5:
					_localctx = new ReturnStmtContext(_localctx);
					enterOuterAlt(_localctx, 5);
				{
					setState(120);
					match(RETURN);
					setState(122);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 7) | (1L << 9) | (1L << 15) | (1L << 21) | (1L << 29) | (1L << 32) | (1L << THIS) | (1L << TRUE) | (1L << FALSE) | (1L << ID) | (1L << STRINGLIT) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << CHARLIT))) != 0)) {
						{
							setState(121);
							((ReturnStmtContext) _localctx).value = expr(0);
						}
					}

					setState(124);
					match(23);
				}
				break;

				case 6:
					_localctx = new AssignmentStmtContext(_localctx);
					enterOuterAlt(_localctx, 6);
				{
					setState(125);
					lhs();
					setState(126);
					match(22);
					setState(127);
					((AssignmentStmtContext) _localctx).rhs = expr(0);
					setState(128);
					match(23);
				}
				break;

				case 7:
					_localctx = new CallStmtContext(_localctx);
					enterOuterAlt(_localctx, 7);
				{
					setState(130);
					call();
					setState(131);
					match(23);
				}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LhsContext extends ParserRuleContext
	{
		public SelexprContext selexpr()
		{
			return getRuleContext(SelexprContext.class, 0);
		}

		public LhsContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_lhs;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterLhs(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitLhs(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitLhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LhsContext lhs() throws RecognitionException
	{
		LhsContext _localctx = new LhsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_lhs);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(135);
				selexpr(0);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VardeclContext extends ParserRuleContext
	{
		public VardeclContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_vardecl;
		}

		public VardeclContext()
		{
		}

		public void copyFrom(VardeclContext ctx)
		{
			super.copyFrom(ctx);
		}
	}

	public static class ArrayVardeclContext extends VardeclContext
	{
		public Token name;
		public ExprContext init;

		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public TypeContext type()
		{
			return getRuleContext(TypeContext.class, 0);
		}

		public ArrayVardeclContext(VardeclContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterArrayVardecl(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitArrayVardecl(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitArrayVardecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class RegVardeclContext extends VardeclContext
	{
		public Token name;
		public ExprContext init;

		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public TypeContext type()
		{
			return getRuleContext(TypeContext.class, 0);
		}

		public RegVardeclContext(VardeclContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterRegVardecl(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitRegVardecl(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitRegVardecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VardeclContext vardecl() throws RecognitionException
	{
		VardeclContext _localctx = new VardeclContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_vardecl);
		int _la;
		try {
			setState(154);
			switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
				case 1:
					_localctx = new RegVardeclContext(_localctx);
					enterOuterAlt(_localctx, 1);
				{
					setState(137);
					type();
					setState(138);
					((RegVardeclContext) _localctx).name = match(ID);
					setState(141);
					_la = _input.LA(1);
					if (_la == 22) {
						{
							setState(139);
							match(22);
							setState(140);
							((RegVardeclContext) _localctx).init = expr(0);
						}
					}

					setState(143);
					match(23);
				}
				break;

				case 2:
					_localctx = new ArrayVardeclContext(_localctx);
					enterOuterAlt(_localctx, 2);
				{
					setState(145);
					type();
					setState(146);
					match(17);
					setState(147);
					((ArrayVardeclContext) _localctx).name = match(ID);
					setState(150);
					_la = _input.LA(1);
					if (_la == 22) {
						{
							setState(148);
							match(22);
							setState(149);
							((ArrayVardeclContext) _localctx).init = expr(0);
						}
					}

					setState(152);
					match(23);
				}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprlistContext extends ParserRuleContext
	{
		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public ExprlistContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_exprlist;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterExprlist(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitExprlist(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitExprlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprlistContext exprlist() throws RecognitionException
	{
		ExprlistContext _localctx = new ExprlistContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_exprlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(156);
				expr(0);
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == 3) {
					{
						{
							setState(157);
							match(3);
							setState(158);
							expr(0);
						}
					}
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext
	{
		public ExprContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_expr;
		}

		public ExprContext()
		{
		}

		public void copyFrom(ExprContext ctx)
		{
			super.copyFrom(ctx);
		}
	}

	public static class AssignmentExprContext extends ExprContext
	{
		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public LhsContext lhs()
		{
			return getRuleContext(LhsContext.class, 0);
		}

		public AssignmentExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterAssignmentExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitAssignmentExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitAssignmentExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class SelectorExprContext extends ExprContext
	{
		public SelexprContext selexpr()
		{
			return getRuleContext(SelexprContext.class, 0);
		}

		public SelectorExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterSelectorExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitSelectorExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitSelectorExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class RelationExprContext extends ExprContext
	{
		public ExprContext left;
		public Token operator;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public RelationExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterRelationExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitRelationExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitRelationExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class CastExprContext extends ExprContext
	{
		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public TypeContext type()
		{
			return getRuleContext(TypeContext.class, 0);
		}

		public CastExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterCastExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitCastExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitCastExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class PrefixExprContext extends ExprContext
	{
		public Token operator;

		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public PrefixExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterPrefixExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitPrefixExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitPrefixExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BitAndExprContext extends ExprContext
	{
		public ExprContext left;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public BitAndExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterBitAndExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitBitAndExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitBitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BitOrExprContext extends ExprContext
	{
		public ExprContext left;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public BitOrExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterBitOrExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitBitOrExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitBitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class AddExprContext extends ExprContext
	{
		public ExprContext left;
		public Token operator;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public AddExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterAddExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitAddExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitAddExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class PostfixExprContext extends ExprContext
	{
		public Token operator;

		public SelexprContext selexpr()
		{
			return getRuleContext(SelexprContext.class, 0);
		}

		public PostfixExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterPostfixExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitPostfixExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitPostfixExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class MulExprContext extends ExprContext
	{
		public ExprContext left;
		public Token operator;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public MulExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterMulExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitMulExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitMulExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ShiftExprContext extends ExprContext
	{
		public ExprContext left;
		public Token operator;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public ShiftExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterShiftExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitShiftExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitShiftExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class LogicAndExprContext extends ExprContext
	{
		public ExprContext left;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public LogicAndExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterLogicAndExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitLogicAndExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitLogicAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class EqualityExprContext extends ExprContext
	{
		public ExprContext left;
		public Token operator;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public EqualityExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterEqualityExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitEqualityExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BitXorExprContext extends ExprContext
	{
		public ExprContext left;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public BitXorExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterBitXorExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitBitXorExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitBitXorExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class LogicOrExprContext extends ExprContext
	{
		public ExprContext left;
		public ExprContext right;

		public List<ExprContext> expr()
		{
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i)
		{
			return getRuleContext(ExprContext.class, i);
		}

		public LogicOrExprContext(ExprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterLogicOrExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitLogicOrExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitLogicOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException
	{
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException
	{
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(180);
				switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
					case 1: {
						_localctx = new AssignmentExprContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;

						setState(165);
						lhs();
						setState(166);
						match(22);
						setState(167);
						expr(15);
					}
					break;

					case 2: {
						_localctx = new CastExprContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(169);
						match(7);
						setState(170);
						type();
						setState(171);
						match(19);
						setState(172);
						expr(4);
					}
					break;

					case 3: {
						_localctx = new PrefixExprContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(174);
						((PrefixExprContext) _localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 9) | (1L << 15) | (1L << 21) | (1L << 29) | (1L << 32))) != 0))) {
							((PrefixExprContext) _localctx).operator = (Token) _errHandler.recoverInline(this);
						}
						consume();
						setState(175);
						expr(3);
					}
					break;

					case 4: {
						_localctx = new PostfixExprContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(176);
						selexpr(0);
						setState(177);
						((PostfixExprContext) _localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if (!(_la == 9 || _la == 15)) {
							((PostfixExprContext) _localctx).operator = (Token) _errHandler.recoverInline(this);
						}
						consume();
					}
					break;

					case 5: {
						_localctx = new SelectorExprContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(179);
						selexpr(0);
					}
					break;
				}
				_ctx.stop = _input.LT(-1);
				setState(214);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
				while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null) triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							setState(212);
							switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
								case 1: {
									_localctx = new LogicOrExprContext(new ExprContext(_parentctx, _parentState));
									((LogicOrExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(182);
									if (!(precpred(_ctx, 14)))
										throw new FailedPredicateException(this, "precpred(_ctx, 14)");
									setState(183);
									match(25);
									setState(184);
									((LogicOrExprContext) _localctx).right = expr(15);
								}
								break;

								case 2: {
									_localctx = new LogicAndExprContext(new ExprContext(_parentctx, _parentState));
									((LogicAndExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(185);
									if (!(precpred(_ctx, 13)))
										throw new FailedPredicateException(this, "precpred(_ctx, 13)");
									setState(186);
									match(24);
									setState(187);
									((LogicAndExprContext) _localctx).right = expr(14);
								}
								break;

								case 3: {
									_localctx = new BitOrExprContext(new ExprContext(_parentctx, _parentState));
									((BitOrExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(188);
									if (!(precpred(_ctx, 12)))
										throw new FailedPredicateException(this, "precpred(_ctx, 12)");
									setState(189);
									match(31);
									setState(190);
									((BitOrExprContext) _localctx).right = expr(13);
								}
								break;

								case 4: {
									_localctx = new BitXorExprContext(new ExprContext(_parentctx, _parentState));
									((BitXorExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(191);
									if (!(precpred(_ctx, 11)))
										throw new FailedPredicateException(this, "precpred(_ctx, 11)");
									setState(192);
									match(18);
									setState(193);
									((BitXorExprContext) _localctx).right = expr(12);
								}
								break;

								case 5: {
									_localctx = new BitAndExprContext(new ExprContext(_parentctx, _parentState));
									((BitAndExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(194);
									if (!(precpred(_ctx, 10)))
										throw new FailedPredicateException(this, "precpred(_ctx, 10)");
									setState(195);
									match(2);
									setState(196);
									((BitAndExprContext) _localctx).right = expr(11);
								}
								break;

								case 6: {
									_localctx = new EqualityExprContext(new ExprContext(_parentctx, _parentState));
									((EqualityExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(197);
									if (!(precpred(_ctx, 9)))
										throw new FailedPredicateException(this, "precpred(_ctx, 9)");
									setState(198);
									((EqualityExprContext) _localctx).operator = _input.LT(1);
									_la = _input.LA(1);
									if (!(_la == 10 || _la == 28)) {
										((EqualityExprContext) _localctx).operator = (Token) _errHandler.recoverInline(this);
									}
									consume();
									setState(199);
									((EqualityExprContext) _localctx).right = expr(10);
								}
								break;

								case 7: {
									_localctx = new RelationExprContext(new ExprContext(_parentctx, _parentState));
									((RelationExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(200);
									if (!(precpred(_ctx, 8)))
										throw new FailedPredicateException(this, "precpred(_ctx, 8)");
									setState(201);
									((RelationExprContext) _localctx).operator = _input.LT(1);
									_la = _input.LA(1);
									if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 8) | (1L << 11) | (1L << 26) | (1L << 30))) != 0))) {
										((RelationExprContext) _localctx).operator = (Token) _errHandler.recoverInline(this);
									}
									consume();
									setState(202);
									((RelationExprContext) _localctx).right = expr(9);
								}
								break;

								case 8: {
									_localctx = new ShiftExprContext(new ExprContext(_parentctx, _parentState));
									((ShiftExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(203);
									if (!(precpred(_ctx, 7)))
										throw new FailedPredicateException(this, "precpred(_ctx, 7)");
									setState(204);
									((ShiftExprContext) _localctx).operator = _input.LT(1);
									_la = _input.LA(1);
									if (!(_la == 12 || _la == 16)) {
										((ShiftExprContext) _localctx).operator = (Token) _errHandler.recoverInline(this);
									}
									consume();
									setState(205);
									((ShiftExprContext) _localctx).right = expr(8);
								}
								break;

								case 9: {
									_localctx = new MulExprContext(new ExprContext(_parentctx, _parentState));
									((MulExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(206);
									if (!(precpred(_ctx, 6)))
										throw new FailedPredicateException(this, "precpred(_ctx, 6)");
									setState(207);
									((MulExprContext) _localctx).operator = _input.LT(1);
									_la = _input.LA(1);
									if (!(_la == 4 || _la == 27)) {
										((MulExprContext) _localctx).operator = (Token) _errHandler.recoverInline(this);
									}
									consume();
									setState(208);
									((MulExprContext) _localctx).right = expr(7);
								}
								break;

								case 10: {
									_localctx = new AddExprContext(new ExprContext(_parentctx, _parentState));
									((AddExprContext) _localctx).left = _prevctx;
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(209);
									if (!(precpred(_ctx, 5)))
										throw new FailedPredicateException(this, "precpred(_ctx, 5)");
									setState(210);
									((AddExprContext) _localctx).operator = _input.LT(1);
									_la = _input.LA(1);
									if (!(_la == 5 || _la == 21)) {
										((AddExprContext) _localctx).operator = (Token) _errHandler.recoverInline(this);
									}
									consume();
									setState(211);
									((AddExprContext) _localctx).right = expr(6);
								}
								break;
							}
						}
					}
					setState(216);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SelexprContext extends ParserRuleContext
	{
		public SelexprContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_selexpr;
		}

		public SelexprContext()
		{
		}

		public void copyFrom(SelexprContext ctx)
		{
			super.copyFrom(ctx);
		}
	}

	public static class PrimarySelExprContext extends SelexprContext
	{
		public PrimaryContext primary()
		{
			return getRuleContext(PrimaryContext.class, 0);
		}

		public PrimarySelExprContext(SelexprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterPrimarySelExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitPrimarySelExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitPrimarySelExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class CallSelExprContext extends SelexprContext
	{
		public ExprlistContext exprlist()
		{
			return getRuleContext(ExprlistContext.class, 0);
		}

		public SelexprContext selexpr()
		{
			return getRuleContext(SelexprContext.class, 0);
		}

		public CallSelExprContext(SelexprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterCallSelExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitCallSelExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitCallSelExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ArrayAccessSelExprContext extends SelexprContext
	{
		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public SelexprContext selexpr()
		{
			return getRuleContext(SelexprContext.class, 0);
		}

		public ArrayAccessSelExprContext(SelexprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterArrayAccessSelExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitArrayAccessSelExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitArrayAccessSelExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class MemberAccessSelExprContext extends SelexprContext
	{
		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public SelexprContext selexpr()
		{
			return getRuleContext(SelexprContext.class, 0);
		}

		public MemberAccessSelExprContext(SelexprContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterMemberAccessSelExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitMemberAccessSelExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitMemberAccessSelExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelexprContext selexpr() throws RecognitionException
	{
		return selexpr(0);
	}

	private SelexprContext selexpr(int _p) throws RecognitionException
	{
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SelexprContext _localctx = new SelexprContext(_ctx, _parentState);
		SelexprContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_selexpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				{
					_localctx = new PrimarySelExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;

					setState(218);
					primary();
				}
				_ctx.stop = _input.LT(-1);
				setState(236);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
				while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null) triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							setState(234);
							switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
								case 1: {
									_localctx = new MemberAccessSelExprContext(new SelexprContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_selexpr);
									setState(220);
									if (!(precpred(_ctx, 4)))
										throw new FailedPredicateException(this, "precpred(_ctx, 4)");
									setState(221);
									match(20);
									setState(222);
									match(ID);
								}
								break;

								case 2: {
									_localctx = new ArrayAccessSelExprContext(new SelexprContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_selexpr);
									setState(223);
									if (!(precpred(_ctx, 3)))
										throw new FailedPredicateException(this, "precpred(_ctx, 3)");
									setState(224);
									match(6);
									setState(225);
									expr(0);
									setState(226);
									match(1);
								}
								break;

								case 3: {
									_localctx = new CallSelExprContext(new SelexprContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_selexpr);
									setState(228);
									if (!(precpred(_ctx, 2)))
										throw new FailedPredicateException(this, "precpred(_ctx, 2)");
									setState(229);
									match(7);
									setState(231);
									_la = _input.LA(1);
									if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 7) | (1L << 9) | (1L << 15) | (1L << 21) | (1L << 29) | (1L << 32) | (1L << THIS) | (1L << TRUE) | (1L << FALSE) | (1L << ID) | (1L << STRINGLIT) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << CHARLIT))) != 0)) {
										{
											setState(230);
											exprlist();
										}
									}

									setState(233);
									match(19);
								}
								break;
							}
						}
					}
					setState(238);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CallContext extends ParserRuleContext
	{
		public ExprlistContext exprlist()
		{
			return getRuleContext(ExprlistContext.class, 0);
		}

		public SelexprContext selexpr()
		{
			return getRuleContext(SelexprContext.class, 0);
		}

		public CallContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_call;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterCall(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitCall(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallContext call() throws RecognitionException
	{
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(239);
				selexpr(0);
				setState(240);
				match(7);
				setState(242);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 7) | (1L << 9) | (1L << 15) | (1L << 21) | (1L << 29) | (1L << 32) | (1L << THIS) | (1L << TRUE) | (1L << FALSE) | (1L << ID) | (1L << STRINGLIT) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << CHARLIT))) != 0)) {
					{
						setState(241);
						exprlist();
					}
				}

				setState(244);
				match(19);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext
	{
		public PrimaryContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_primary;
		}

		public PrimaryContext()
		{
		}

		public void copyFrom(PrimaryContext ctx)
		{
			super.copyFrom(ctx);
		}
	}

	public static class IdPrimaryContext extends PrimaryContext
	{
		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public IdPrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterIdPrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitIdPrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitIdPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BracketPrimaryContext extends PrimaryContext
	{
		public ExprContext expr()
		{
			return getRuleContext(ExprContext.class, 0);
		}

		public BracketPrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterBracketPrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitBracketPrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitBracketPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ThisPrimaryContext extends PrimaryContext
	{
		public TerminalNode THIS()
		{
			return getToken(ProphecyParser.THIS, 0);
		}

		public ThisPrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterThisPrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitThisPrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitThisPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class FloatlitPrimaryContext extends PrimaryContext
	{
		public TerminalNode FLOATLIT()
		{
			return getToken(ProphecyParser.FLOATLIT, 0);
		}

		public FloatlitPrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterFloatlitPrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitFloatlitPrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitFloatlitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class StringlitPrimaryContext extends PrimaryContext
	{
		public TerminalNode STRINGLIT()
		{
			return getToken(ProphecyParser.STRINGLIT, 0);
		}

		public StringlitPrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterStringlitPrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitStringlitPrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitStringlitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class CharlitPrimaryContext extends PrimaryContext
	{
		public TerminalNode CHARLIT()
		{
			return getToken(ProphecyParser.CHARLIT, 0);
		}

		public CharlitPrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterCharlitPrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitCharlitPrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitCharlitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TruePrimaryContext extends PrimaryContext
	{
		public TerminalNode TRUE()
		{
			return getToken(ProphecyParser.TRUE, 0);
		}

		public TruePrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterTruePrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitTruePrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitTruePrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class FalsePrimaryContext extends PrimaryContext
	{
		public TerminalNode FALSE()
		{
			return getToken(ProphecyParser.FALSE, 0);
		}

		public FalsePrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterFalsePrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitFalsePrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitFalsePrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class IntlitPrimaryContext extends PrimaryContext
	{
		public TerminalNode INTLIT()
		{
			return getToken(ProphecyParser.INTLIT, 0);
		}

		public IntlitPrimaryContext(PrimaryContext ctx)
		{
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterIntlitPrimary(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitIntlitPrimary(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitIntlitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException
	{
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_primary);
		try {
			setState(258);
			switch (_input.LA(1)) {
				case ID:
					_localctx = new IdPrimaryContext(_localctx);
					enterOuterAlt(_localctx, 1);
				{
					setState(246);
					match(ID);
				}
				break;
				case THIS:
					_localctx = new ThisPrimaryContext(_localctx);
					enterOuterAlt(_localctx, 2);
				{
					setState(247);
					match(THIS);
				}
				break;
				case INTLIT:
					_localctx = new IntlitPrimaryContext(_localctx);
					enterOuterAlt(_localctx, 3);
				{
					setState(248);
					match(INTLIT);
				}
				break;
				case FLOATLIT:
					_localctx = new FloatlitPrimaryContext(_localctx);
					enterOuterAlt(_localctx, 4);
				{
					setState(249);
					match(FLOATLIT);
				}
				break;
				case CHARLIT:
					_localctx = new CharlitPrimaryContext(_localctx);
					enterOuterAlt(_localctx, 5);
				{
					setState(250);
					match(CHARLIT);
				}
				break;
				case STRINGLIT:
					_localctx = new StringlitPrimaryContext(_localctx);
					enterOuterAlt(_localctx, 6);
				{
					setState(251);
					match(STRINGLIT);
				}
				break;
				case TRUE:
					_localctx = new TruePrimaryContext(_localctx);
					enterOuterAlt(_localctx, 7);
				{
					setState(252);
					match(TRUE);
				}
				break;
				case FALSE:
					_localctx = new FalsePrimaryContext(_localctx);
					enterOuterAlt(_localctx, 8);
				{
					setState(253);
					match(FALSE);
				}
				break;
				case 7:
					_localctx = new BracketPrimaryContext(_localctx);
					enterOuterAlt(_localctx, 9);
				{
					setState(254);
					match(7);
					setState(255);
					expr(0);
					setState(256);
					match(19);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext
	{
		public TerminalNode ID()
		{
			return getToken(ProphecyParser.ID, 0);
		}

		public PrimitiveTypeContext primitiveType()
		{
			return getRuleContext(PrimitiveTypeContext.class, 0);
		}

		public TypeContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_type;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitType(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor) return ((ProphecyVisitor<? extends T>) visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException
	{
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_type);
		try {
			setState(262);
			switch (_input.LA(1)) {
				case FLOAT:
				case INT:
				case CHAR:
				case BOOL:
				case VOID:
					enterOuterAlt(_localctx, 1);
				{
					setState(260);
					primitiveType();
				}
				break;
				case ID:
					enterOuterAlt(_localctx, 2);
				{
					setState(261);
					match(ID);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext
	{
		public TerminalNode BOOL()
		{
			return getToken(ProphecyParser.BOOL, 0);
		}

		public TerminalNode INT()
		{
			return getToken(ProphecyParser.INT, 0);
		}

		public TerminalNode FLOAT()
		{
			return getToken(ProphecyParser.FLOAT, 0);
		}

		public TerminalNode VOID()
		{
			return getToken(ProphecyParser.VOID, 0);
		}

		public TerminalNode CHAR()
		{
			return getToken(ProphecyParser.CHAR, 0);
		}

		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_primitiveType;
		}

		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).enterPrimitiveType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof ProphecyListener) ((ProphecyListener) listener).exitPrimitiveType(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof ProphecyVisitor)
				return ((ProphecyVisitor<? extends T>) visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException
	{
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(264);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FLOAT) | (1L << INT) | (1L << CHAR) | (1L << BOOL) | (1L << VOID))) != 0))) {
					_errHandler.recoverInline(this);
				}
				consume();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex)
	{
		switch (ruleIndex) {
			case 14:
				return expr_sempred((ExprContext) _localctx, predIndex);

			case 15:
				return selexpr_sempred((SelexprContext) _localctx, predIndex);
		}
		return true;
	}

	private boolean expr_sempred(ExprContext _localctx, int predIndex)
	{
		switch (predIndex) {
			case 0:
				return precpred(_ctx, 14);

			case 1:
				return precpred(_ctx, 13);

			case 2:
				return precpred(_ctx, 12);

			case 3:
				return precpred(_ctx, 11);

			case 4:
				return precpred(_ctx, 10);

			case 5:
				return precpred(_ctx, 9);

			case 6:
				return precpred(_ctx, 8);

			case 7:
				return precpred(_ctx, 7);

			case 8:
				return precpred(_ctx, 6);

			case 9:
				return precpred(_ctx, 5);
		}
		return true;
	}

	private boolean selexpr_sempred(SelexprContext _localctx, int predIndex)
	{
		switch (predIndex) {
			case 10:
				return precpred(_ctx, 4);

			case 11:
				return precpred(_ctx, 3);

			case 12:
				return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
			"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\39\u010d\4\2\t\2\4" +
					"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
					"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
					"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3\62\n\3" +
					"\3\3\3\3\3\4\3\4\6\48\n\4\r\4\16\49\3\4\3\4\3\5\5\5?\n\5\3\5\3\5\5\5C" +
					"\n\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\5\bL\n\b\3\b\3\b\3\b\5\bQ\n\b\3\b\3\b" +
					"\3\b\3\t\3\t\3\t\7\tY\n\t\f\t\16\t\\\13\t\3\n\3\n\3\n\3\13\3\13\7\13c" +
					"\n\13\f\13\16\13f\13\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f" +
					"\5\fs\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f}\n\f\3\f\3\f\3\f\3\f\3\f" +
					"\3\f\3\f\3\f\3\f\5\f\u0088\n\f\3\r\3\r\3\16\3\16\3\16\3\16\5\16\u0090" +
					"\n\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0099\n\16\3\16\3\16\5\16" +
					"\u009d\n\16\3\17\3\17\3\17\7\17\u00a2\n\17\f\17\16\17\u00a5\13\17\3\20" +
					"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20" +
					"\3\20\5\20\u00b7\n\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20" +
					"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20" +
					"\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u00d7\n\20\f\20\16\20\u00da\13\20" +
					"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21" +
					"\5\21\u00ea\n\21\3\21\7\21\u00ed\n\21\f\21\16\21\u00f0\13\21\3\22\3\22" +
					"\3\22\5\22\u00f5\n\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23" +
					"\3\23\3\23\3\23\3\23\5\23\u0105\n\23\3\24\3\24\5\24\u0109\n\24\3\25\3" +
					"\25\3\25\2\4\36 \26\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(\2\n\b" +
					"\2\7\7\13\13\21\21\27\27\37\37\"\"\4\2\13\13\21\21\4\2\f\f\36\36\6\2\n" +
					"\n\r\r\34\34  \4\2\16\16\22\22\4\2\6\6\35\35\4\2\7\7\27\27\3\2-\61\u0128" +
					"\2*\3\2\2\2\4-\3\2\2\2\6\65\3\2\2\2\b>\3\2\2\2\nD\3\2\2\2\fF\3\2\2\2\16" +
					"K\3\2\2\2\20U\3\2\2\2\22]\3\2\2\2\24`\3\2\2\2\26\u0087\3\2\2\2\30\u0089" +
					"\3\2\2\2\32\u009c\3\2\2\2\34\u009e\3\2\2\2\36\u00b6\3\2\2\2 \u00db\3\2" +
					"\2\2\"\u00f1\3\2\2\2$\u0104\3\2\2\2&\u0108\3\2\2\2(\u010a\3\2\2\2*+\5" +
					"\4\3\2+,\7\2\2\3,\3\3\2\2\2-.\7#\2\2.\61\7\62\2\2/\60\7$\2\2\60\62\7\62" +
					"\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\63\3\2\2\2\63\64\5\6\4\2\64\5\3\2\2" +
					"\2\65\67\7\17\2\2\668\5\b\5\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:\3" +
					"\2\2\2:;\3\2\2\2;<\7\20\2\2<\7\3\2\2\2=?\5\n\6\2>=\3\2\2\2>?\3\2\2\2?" +
					"B\3\2\2\2@C\5\f\7\2AC\5\16\b\2B@\3\2\2\2BA\3\2\2\2C\t\3\2\2\2DE\7&\2\2" +
					"E\13\3\2\2\2FG\5&\24\2GH\7\62\2\2HI\7\31\2\2I\r\3\2\2\2JL\5&\24\2KJ\3" +
					"\2\2\2KL\3\2\2\2LM\3\2\2\2MN\7\62\2\2NP\7\t\2\2OQ\5\20\t\2PO\3\2\2\2P" +
					"Q\3\2\2\2QR\3\2\2\2RS\7\25\2\2ST\5\24\13\2T\17\3\2\2\2UZ\5\22\n\2VW\7" +
					"\5\2\2WY\5\22\n\2XV\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\21\3\2\2\2" +
					"\\Z\3\2\2\2]^\5&\24\2^_\7\62\2\2_\23\3\2\2\2`d\7\17\2\2ac\5\26\f\2ba\3" +
					"\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2eg\3\2\2\2fd\3\2\2\2gh\7\20\2\2h\25" +
					"\3\2\2\2i\u0088\5\24\13\2j\u0088\5\32\16\2kl\7\'\2\2lm\7\t\2\2mn\5\36" +
					"\20\2no\7\25\2\2or\5\26\f\2pq\7)\2\2qs\5\26\f\2rp\3\2\2\2rs\3\2\2\2s\u0088" +
					"\3\2\2\2tu\7(\2\2uv\7\t\2\2vw\5\36\20\2wx\7\25\2\2xy\5\26\f\2y\u0088\3" +
					"\2\2\2z|\7*\2\2{}\5\36\20\2|{\3\2\2\2|}\3\2\2\2}~\3\2\2\2~\u0088\7\31" +
					"\2\2\177\u0080\5\30\r\2\u0080\u0081\7\30\2\2\u0081\u0082\5\36\20\2\u0082" +
					"\u0083\7\31\2\2\u0083\u0088\3\2\2\2\u0084\u0085\5\"\22\2\u0085\u0086\7" +
					"\31\2\2\u0086\u0088\3\2\2\2\u0087i\3\2\2\2\u0087j\3\2\2\2\u0087k\3\2\2" +
					"\2\u0087t\3\2\2\2\u0087z\3\2\2\2\u0087\177\3\2\2\2\u0087\u0084\3\2\2\2" +
					"\u0088\27\3\2\2\2\u0089\u008a\5 \21\2\u008a\31\3\2\2\2\u008b\u008c\5&" +
					"\24\2\u008c\u008f\7\62\2\2\u008d\u008e\7\30\2\2\u008e\u0090\5\36\20\2" +
					"\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092" +
					"\7\31\2\2\u0092\u009d\3\2\2\2\u0093\u0094\5&\24\2\u0094\u0095\7\23\2\2" +
					"\u0095\u0098\7\62\2\2\u0096\u0097\7\30\2\2\u0097\u0099\5\36\20\2\u0098" +
					"\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\7\31" +
					"\2\2\u009b\u009d\3\2\2\2\u009c\u008b\3\2\2\2\u009c\u0093\3\2\2\2\u009d" +
					"\33\3\2\2\2\u009e\u00a3\5\36\20\2\u009f\u00a0\7\5\2\2\u00a0\u00a2\5\36" +
					"\20\2\u00a1\u009f\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3" +
					"\u00a4\3\2\2\2\u00a4\35\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00a7\b\20\1" +
					"\2\u00a7\u00a8\5\30\r\2\u00a8\u00a9\7\30\2\2\u00a9\u00aa\5\36\20\21\u00aa" +
					"\u00b7\3\2\2\2\u00ab\u00ac\7\t\2\2\u00ac\u00ad\5&\24\2\u00ad\u00ae\7\25" +
					"\2\2\u00ae\u00af\5\36\20\6\u00af\u00b7\3\2\2\2\u00b0\u00b1\t\2\2\2\u00b1" +
					"\u00b7\5\36\20\5\u00b2\u00b3\5 \21\2\u00b3\u00b4\t\3\2\2\u00b4\u00b7\3" +
					"\2\2\2\u00b5\u00b7\5 \21\2\u00b6\u00a6\3\2\2\2\u00b6\u00ab\3\2\2\2\u00b6" +
					"\u00b0\3\2\2\2\u00b6\u00b2\3\2\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00d8\3\2" +
					"\2\2\u00b8\u00b9\f\20\2\2\u00b9\u00ba\7\33\2\2\u00ba\u00d7\5\36\20\21" +
					"\u00bb\u00bc\f\17\2\2\u00bc\u00bd\7\32\2\2\u00bd\u00d7\5\36\20\20\u00be" +
					"\u00bf\f\16\2\2\u00bf\u00c0\7!\2\2\u00c0\u00d7\5\36\20\17\u00c1\u00c2" +
					"\f\r\2\2\u00c2\u00c3\7\24\2\2\u00c3\u00d7\5\36\20\16\u00c4\u00c5\f\f\2" +
					"\2\u00c5\u00c6\7\4\2\2\u00c6\u00d7\5\36\20\r\u00c7\u00c8\f\13\2\2\u00c8" +
					"\u00c9\t\4\2\2\u00c9\u00d7\5\36\20\f\u00ca\u00cb\f\n\2\2\u00cb\u00cc\t" +
					"\5\2\2\u00cc\u00d7\5\36\20\13\u00cd\u00ce\f\t\2\2\u00ce\u00cf\t\6\2\2" +
					"\u00cf\u00d7\5\36\20\n\u00d0\u00d1\f\b\2\2\u00d1\u00d2\t\7\2\2\u00d2\u00d7" +
					"\5\36\20\t\u00d3\u00d4\f\7\2\2\u00d4\u00d5\t\b\2\2\u00d5\u00d7\5\36\20" +
					"\b\u00d6\u00b8\3\2\2\2\u00d6\u00bb\3\2\2\2\u00d6\u00be\3\2\2\2\u00d6\u00c1" +
					"\3\2\2\2\u00d6\u00c4\3\2\2\2\u00d6\u00c7\3\2\2\2\u00d6\u00ca\3\2\2\2\u00d6" +
					"\u00cd\3\2\2\2\u00d6\u00d0\3\2\2\2\u00d6\u00d3\3\2\2\2\u00d7\u00da\3\2" +
					"\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\37\3\2\2\2\u00da\u00d8" +
					"\3\2\2\2\u00db\u00dc\b\21\1\2\u00dc\u00dd\5$\23\2\u00dd\u00ee\3\2\2\2" +
					"\u00de\u00df\f\6\2\2\u00df\u00e0\7\26\2\2\u00e0\u00ed\7\62\2\2\u00e1\u00e2" +
					"\f\5\2\2\u00e2\u00e3\7\b\2\2\u00e3\u00e4\5\36\20\2\u00e4\u00e5\7\3\2\2" +
					"\u00e5\u00ed\3\2\2\2\u00e6\u00e7\f\4\2\2\u00e7\u00e9\7\t\2\2\u00e8\u00ea" +
					"\5\34\17\2\u00e9\u00e8\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00eb\3\2\2\2" +
					"\u00eb\u00ed\7\25\2\2\u00ec\u00de\3\2\2\2\u00ec\u00e1\3\2\2\2\u00ec\u00e6" +
					"\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef" +
					"!\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f1\u00f2\5 \21\2\u00f2\u00f4\7\t\2\2" +
					"\u00f3\u00f5\5\34\17\2\u00f4\u00f3\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f6" +
					"\3\2\2\2\u00f6\u00f7\7\25\2\2\u00f7#\3\2\2\2\u00f8\u0105\7\62\2\2\u00f9" +
					"\u0105\7%\2\2\u00fa\u0105\7\64\2\2\u00fb\u0105\7\65\2\2\u00fc\u0105\7" +
					"\66\2\2\u00fd\u0105\7\63\2\2\u00fe\u0105\7+\2\2\u00ff\u0105\7,\2\2\u0100" +
					"\u0101\7\t\2\2\u0101\u0102\5\36\20\2\u0102\u0103\7\25\2\2\u0103\u0105" +
					"\3\2\2\2\u0104\u00f8\3\2\2\2\u0104\u00f9\3\2\2\2\u0104\u00fa\3\2\2\2\u0104" +
					"\u00fb\3\2\2\2\u0104\u00fc\3\2\2\2\u0104\u00fd\3\2\2\2\u0104\u00fe\3\2" +
					"\2\2\u0104\u00ff\3\2\2\2\u0104\u0100\3\2\2\2\u0105%\3\2\2\2\u0106\u0109" +
					"\5(\25\2\u0107\u0109\7\62\2\2\u0108\u0106\3\2\2\2\u0108\u0107\3\2\2\2" +
					"\u0109\'\3\2\2\2\u010a\u010b\t\t\2\2\u010b)\3\2\2\2\32\619>BKPZdr|\u0087" +
					"\u008f\u0098\u009c\u00a3\u00b6\u00d6\u00d8\u00e9\u00ec\u00ee\u00f4\u0104" +
					"\u0108";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());

	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
