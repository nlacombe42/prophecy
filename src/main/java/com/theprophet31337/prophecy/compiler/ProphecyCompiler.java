package com.theprophet31337.prophecy.compiler;

import com.theprophet31337.prophecy.analyser.symboltable.SymbolTableBuilder;
import com.theprophet31337.prophecy.analyser.symboltable.scope.GlobalScope;
import com.theprophet31337.prophecy.analyser.type.TypeAnalyser;
import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.generator.ProphecyGenerator;
import com.theprophet31337.prophecy.generator.ProphecyGeneratorTarget;
import com.theprophet31337.prophecy.parser.ProphecyAstBuilder;
import com.theprophet31337.prophecy.parser.ProphecyParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProphecyCompiler
{
	private InputStream input;
	private OutputStream output;

	public ProphecyCompiler(InputStream input, OutputStream output)
	{
		this.input = input;
		this.output = output;
	}

	public void compile() throws IOException
	{
		ProphecyAstBuilder parser = new ProphecyAstBuilder(input);
		ProphecyAstNode astRoot;

		try {
			System.out.println("Building AST...");
			astRoot = parser.parse();
			System.out.println(astRoot.toStringTree());
		} catch (ProphecyParserException e) {
			System.err.println(e.getMessage());
			return;
		}

		System.out.println("Building SymbolTable...");
		SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder(astRoot);
		GlobalScope globalScope = symbolTableBuilder.buildSymbolTable();
		System.out.println(globalScope.toString());

		System.out.println("Performing Type Analysis and Check...");
		TypeAnalyser typeAnalyser = new TypeAnalyser(astRoot);
		typeAnalyser.analyseAndAnotate();
		System.out.println(astRoot.toStringTree());

		System.out.println("Generating LLVM IR...");
		ProphecyGenerator generator = new ProphecyGenerator(globalScope);
		generator.generate(output, ProphecyGeneratorTarget.LLVM_IR);

	}
}
