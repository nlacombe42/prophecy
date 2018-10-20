package com.theprophet31337.prophecy.analyser.type;

import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstWalker;
import com.theprophet31337.prophecy.reporting.DefaultProphecyBuildReporter;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;

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
