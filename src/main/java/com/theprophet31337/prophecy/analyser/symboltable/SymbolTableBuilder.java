package com.theprophet31337.prophecy.analyser.symboltable;

import com.theprophet31337.prophecy.analyser.symboltable.scope.GlobalScope;
import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstWalker;
import com.theprophet31337.prophecy.reporting.DefaultProphecyBuildReporter;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;

public class SymbolTableBuilder
{
	private ProphecyBuildListener buildListener;
	private ProphecyAstNode astRoot;

	public SymbolTableBuilder(ProphecyAstNode astRoot)
	{
		this.astRoot = astRoot;
		this.buildListener = new DefaultProphecyBuildReporter();
	}

	public SymbolTableBuilder(ProphecyAstNode astRoot, ProphecyBuildListener buildListener)
	{
		this.astRoot = astRoot;
		this.buildListener = buildListener;
	}

	public GlobalScope buildSymbolTable()
	{
		SymbolDefiner symbolTableBuilder = new SymbolDefiner(buildListener);
		ProphecyAstWalker walker = new ProphecyAstWalker();
		walker.addListener(symbolTableBuilder);

		walker.walk(astRoot);

		walker.removeAllListeners();
		walker.addListener(new SymbolResolver(buildListener));

		walker.walk(astRoot);

		return symbolTableBuilder.getGlobalScope();
	}
}
