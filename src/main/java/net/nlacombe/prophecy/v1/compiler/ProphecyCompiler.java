package net.nlacombe.prophecy.v1.compiler;

import net.nlacombe.prophecy.v1.analyser.symboltable.SymbolTableBuilder;
import net.nlacombe.prophecy.v1.analyser.symboltable.scope.GlobalScope;
import net.nlacombe.prophecy.v1.analyser.type.TypeAnalyser;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.generator.ProphecyGenerator;
import net.nlacombe.prophecy.v1.generator.ProphecyGeneratorTarget;
import net.nlacombe.prophecy.v1.parser.ProphecyAstBuilder;
import net.nlacombe.prophecy.v1.parser.ProphecyParserException;

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

		System.out.println("Generating LLVM IR...");
		ProphecyGenerator generator = new ProphecyGenerator(globalScope);
		generator.generate(output, ProphecyGeneratorTarget.LLVM_IR);

	}
}
