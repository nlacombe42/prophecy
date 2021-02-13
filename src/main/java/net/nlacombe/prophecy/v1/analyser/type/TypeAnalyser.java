package net.nlacombe.prophecy.v1.analyser.type;

import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.ast.ProphecyAstWalker;
import net.nlacombe.prophecy.v1.reporting.DefaultProphecyBuildReporter;
import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;

public class TypeAnalyser
{
	private ProphecyBuildListener buildListener;
	private ProphecyAstNode astRoot;

	public TypeAnalyser(ProphecyAstNode astRoot)
	{
		this.astRoot = astRoot;
		this.buildListener = new DefaultProphecyBuildReporter();
	}

	public TypeAnalyser(ProphecyAstNode astRoot, ProphecyBuildListener buildListener)
	{
		this.astRoot = astRoot;
		this.buildListener = buildListener;
	}

	public void analyseAndAnotate()
	{
		TypeAnalyserListener typeAnaliserListener = new TypeAnalyserListener(buildListener);
		ProphecyAstWalker walker = new ProphecyAstWalker();
		walker.addListener(typeAnaliserListener);

		walker.walk(astRoot);
	}
}
