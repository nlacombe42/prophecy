package net.nlacombe.prophecy.v1.analyser.symboltable;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.ast.ProphecyAstWalker;
import net.nlacombe.prophecy.v1.reporting.DefaultProphecyBuildReporter;
import net.nlacombe.prophecy.v1.reporting.ProphecyBuildListener;

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
