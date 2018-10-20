package com.theprophet31337.prophecy.generator;

import com.theprophet31337.prophecy.analyser.symboltable.scope.GlobalScope;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.BuiltInTypeSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.ClassSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.MethodSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.Symbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.VariableSymbol;
import com.theprophet31337.prophecy.ast.nodewrapper.AstMethodDef;
import com.theprophet31337.prophecy.constants.Constants;
import com.theprophet31337.prophecy.generator.flatmodel.FlatGlobalScope;
import com.theprophet31337.prophecy.generator.flatmodel.FlatSymbol;
import com.theprophet31337.prophecy.generator.flatmodel.Function;
import com.theprophet31337.prophecy.generator.flatmodel.Structure;
import org.stringtemplate.v4.STGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * This class generates a flat model (class-less model) from a class-based model.
 * <p>
 * More specifically, this class transform a Prophecy Symbol table into a flat DOM model
 * that can be directly translated to class-less languages like C or LLVM IR.
 */
public class FlatModelTranslator
{
	private GeneratorTargetSpecifics targetSpecifics;
	private SystemCodeGenerator systemCodeGenerator;

	public FlatModelTranslator(ProphecyGeneratorTarget target, STGroup templates)
	{
		targetSpecifics = target.getTargetSpecifics();

		systemCodeGenerator = new SystemCodeGenerator(templates, target);
	}

	public FlatGlobalScope translate(GlobalScope globalScope)
	{
		FlatGlobalScope flatGlobalScope = new FlatGlobalScope();
		ClassSymbol classSymbol;

		for (Symbol symbol : globalScope.getSymbols()) {
			if (symbol instanceof ClassSymbol) {
				classSymbol = (ClassSymbol) symbol;

				if (classSymbol.isSystem()) {
					systemCodeGenerator.generateSystemClass(flatGlobalScope, classSymbol);
				} else {
					translateClass(classSymbol, flatGlobalScope);
				}
			}
		}

		return flatGlobalScope;
	}

	private void translateClass(ClassSymbol classSymbol, FlatGlobalScope flatGlobalScope)
	{
		Structure classStructure = toStructure(classSymbol);

		flatGlobalScope.addStructure(classStructure);

		for (MethodSymbol method : classSymbol.getMethods())
			flatGlobalScope.addFunction(translateMethod(classSymbol, method));
	}

	private Function translateMethod(ClassSymbol classSymbol, MethodSymbol method)
	{
		String name;

		if (method.getName().equals(Constants.MAIN_METHOD_NAME)) {
			name = targetSpecifics.getMainMethodName();
		} else {
			name = targetSpecifics.getMethodName(method);
		}

		String returnType = targetSpecifics.getTypeName(method.getType());

		Function function = new Function(name, returnType);

		String className = targetSpecifics.getPointerTypeName(classSymbol.getName());
		function.addParameter(new FlatSymbol("this", className));

		for (Symbol parameter : method.getParameters())
			function.addParameter(translateSymbol(parameter));

		AstMethodDef methodDef = new AstMethodDef(method.getDefinition());

		function.setMethodBodyBlockNode(methodDef.getBlockNode());

		return function;
	}

	private FlatSymbol translateSymbol(Symbol symbol)
	{
		String name = targetSpecifics.getIdentifierName(symbol.getName());
		String type = targetSpecifics.getTypeName(symbol.getType());

		return new FlatSymbol(name, type);
	}

	/**
	 * Translate the class (including super class chain) to one structure.
	 */
	private Structure toStructure(ClassSymbol classSymbol)
	{
		Structure classStructure = new Structure(targetSpecifics.getIdentifierName(classSymbol.getName()));

		//If there is a super class, get its structure members and add them first.
		if (classSymbol.getSuperClass() != null)
			classStructure.addMembers(toStructure(classSymbol.getSuperClass()).getMembers());

		//Add the structure members of the direct member of this class (not superclass members).
		classStructure.addMembers(directMemberToStructure(classSymbol));

		return classStructure;
	}

	/**
	 * Only translates members of the provided class, not of the super class chain.
	 */
	private List<FlatSymbol> directMemberToStructure(ClassSymbol classSymbol)
	{
		List<FlatSymbol> members = new LinkedList<FlatSymbol>();
		VariableSymbol variable;
		String name;
		String type;

		for (Symbol symbol : classSymbol.getMembers()) {
			if (symbol instanceof VariableSymbol) {
				variable = (VariableSymbol) symbol;

				name = targetSpecifics.getIdentifierName(classSymbol.getName(), variable.getName());

				if (variable.getType() instanceof BuiltInTypeSymbol)
					type = targetSpecifics.getBuiltInTypeName((BuiltInTypeSymbol) variable.getType());
				else
					type = targetSpecifics.getPointerTypeName(variable.getType().getName());

				members.add(new FlatSymbol(name, type));
			}
		}

		return members;
	}
}
