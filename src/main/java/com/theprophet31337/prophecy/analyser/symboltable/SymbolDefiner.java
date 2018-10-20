package com.theprophet31337.prophecy.analyser.symboltable;

import com.theprophet31337.prophecy.analyser.symboltable.scope.GlobalScope;
import com.theprophet31337.prophecy.analyser.symboltable.scope.LocalScope;
import com.theprophet31337.prophecy.analyser.symboltable.scope.Scope;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.BuiltInTypeSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.ClassSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.MethodSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.Symbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.VariableSymbol;
import com.theprophet31337.prophecy.ast.ProphecyAstBaseListener;
import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstNodeType;
import com.theprophet31337.prophecy.ast.nodewrapper.AstClassDef;
import com.theprophet31337.prophecy.ast.nodewrapper.AstFieldDef;
import com.theprophet31337.prophecy.ast.nodewrapper.AstMethodDef;
import com.theprophet31337.prophecy.ast.nodewrapper.AstParam;
import com.theprophet31337.prophecy.ast.nodewrapper.AstVarDecl;
import com.theprophet31337.prophecy.constants.Constants;
import com.theprophet31337.prophecy.reporting.BuildMessageLevel;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;

public class SymbolDefiner extends ProphecyAstBaseListener
{
	private Scope currentScope;
	private MethodSymbol currentMethod;

	private GlobalScope globalScope;
	private ProphecyBuildListener buildListener;

	private ClassSymbol systemClass;
	private MethodSymbol system_println;

	public SymbolDefiner(ProphecyBuildListener buildListener)
	{
		this.buildListener = buildListener;
	}

	public GlobalScope getGlobalScope()
	{
		return globalScope;
	}

	private void duplicateSymbolError(ProphecyAstNode currentNode, Symbol previousSym)
	{
		int line = previousSym.getDefinition().getLineNumber();
		int column = previousSym.getDefinition().getColumn();

		buildListener.buildMessage(BuildMessageLevel.ERROR,
				currentNode.getLineNumber(), currentNode.getColumn(),
				"duplicate symbol \"" + previousSym.getName() + "\" previsouly defined at " +
						"line " + line + " column " + column);
	}

	@Override
	public void enterFile(ProphecyAstNode node)
	{
		globalScope = new GlobalScope();
		currentScope = globalScope;

		addBuiltInTypes();
		addBuiltInClasses();
		addBuiltInObjects();
	}

	private void addBuiltInClasses()
	{
		ClassSymbol stringClass = new ClassSymbol(Constants.STRING_CLASS_NAME, currentScope, null);
		stringClass.setSystem(true);
		currentScope.define(stringClass);

		systemClass = new ClassSymbol(Constants.SYSTEM_CLASS_NAME, currentScope, null);
		systemClass.setSystem(true);

		system_println = new MethodSymbol(Constants.SYSTEM_PRINTLN_METHODSIGNATURE.getName(), BuiltInTypeSymbol.tVoid, systemClass, new LocalScope(system_println));
		system_println.setStatic(true);
		system_println.putMember(new VariableSymbol(Constants.SYSTEM_PRINTLN_PARAM_NAME, BuiltInTypeSymbol.tInt));
		systemClass.putMember(system_println);

		currentScope.define(systemClass);
	}

	private void addBuiltInObjects()
	{
		currentScope.define(new VariableSymbol("system", systemClass));
	}

	private void addBuiltInTypes()
	{
		for (BuiltInTypeSymbol symbol : BuiltInTypeSymbol.BUILT_IN_TYPES)
			currentScope.define(symbol);
	}

	@Override
	public void enterReturn(ProphecyAstNode node)
	{
		node.setSymbol(currentMethod);
	}

	@Override
	public void enterId(ProphecyAstNode node)
	{
		if (node.getParent() != null) {
			/* If we are directly under a classdef then everything is already
			 * taken care of by enterClassDef()
			 */
			if (node.getParent().getType() == ProphecyAstNodeType.CLASSDEF)
				return;
		}

		node.setScope(currentScope);
	}

	@Override
	public void enterStringLit(ProphecyAstNode node)
	{
		node.setScope(currentScope);
	}

	@Override
	public void enterCall(ProphecyAstNode node)
	{
		node.setScope(currentScope);
	}

	@Override
	public void enterVarDecl(ProphecyAstNode node)
	{
		AstVarDecl vardecl = new AstVarDecl(node);

		VariableSymbol var = new VariableSymbol(vardecl.getNameText(), null);
		var.setDefinition(node);
		node.setSymbol(var);
		vardecl.getTypeNode().setScope(currentScope);

		Symbol previousSym = currentScope.define(var);

		if (previousSym != null) {
			duplicateSymbolError(node, previousSym);
		}
	}

	@Override
	public void enterFieldDef(ProphecyAstNode node)
	{
		AstFieldDef fielddef = new AstFieldDef(node);

		VariableSymbol field = new VariableSymbol(fielddef.getNameText(), null);
		field.setDefinition(node);
		node.setSymbol(field);
		fielddef.getTypeNode().setScope(currentScope);

		Symbol previousSym;
		ClassSymbol currentClass = (ClassSymbol) currentScope;

		//If this field is static
		if (fielddef.getModifiers().contains(Modifiers.STATIC.getName())) {
			//Define this field in the static class context.
			previousSym = currentClass.defineStatic(field);
		} else {
			previousSym = currentClass.define(field);
		}

		if (previousSym != null) {
			duplicateSymbolError(node, previousSym);
		}
	}

	@Override
	public void enterParam(ProphecyAstNode node)
	{
		AstParam paramdecl = new AstParam(node);

		VariableSymbol param = new VariableSymbol(paramdecl.getNameText(), null);
		param.setDefinition(node);
		node.setSymbol(param);
		paramdecl.getTypeNode().setScope(currentScope);

		Symbol previousSym = currentScope.define(param);

		if (previousSym != null) {
			duplicateSymbolError(node, previousSym);
		}
	}

	@Override
	public void enterBlock(ProphecyAstNode node)
	{
		if (currentMethod != null) {
			//Define symbol here since we now know the method signature.
			Symbol previousSym;

			if (currentMethod.isStatic()) {
				//If the method is static, define the method in the static scope.
				previousSym = currentMethod.getParentClass().defineStatic(currentMethod);
			} else {
				previousSym = currentMethod.getParentClass().define(currentMethod);
			}

			if (previousSym != null) {
				duplicateSymbolError(currentMethod.getDefinition(), previousSym);
			}
		}

		currentScope = new LocalScope(currentScope);
	}

	@Override
	public void exitBlock(ProphecyAstNode node)
	{
		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void enterClassDef(ProphecyAstNode node)
	{
		AstClassDef classdef = new AstClassDef(node);

		if (classdef.getSuperclassNode() != null) {
			classdef.getSuperclassNode().setScope(currentScope);
		}

		ClassSymbol classSym = new ClassSymbol(classdef.getNameText(), currentScope, null);
		classSym.setDefinition(node);
		node.setSymbol(classSym);

		Symbol previousSym = currentScope.define(classSym);

		if (previousSym != null) {
			duplicateSymbolError(node, previousSym);
		}

		currentScope = classSym;
	}

	@Override
	public void exitClassDef(ProphecyAstNode node)
	{
		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void enterMethodDef(ProphecyAstNode node)
	{
		AstMethodDef methdef = new AstMethodDef(node);

		methdef.getTypeNode().setScope(currentScope);

		MethodSymbol methodSym = new MethodSymbol(methdef.getNameText(), null, (ClassSymbol) currentScope);

		if (methdef.getModifiers().contains(Modifiers.STATIC.getName()))
			methodSym.setStatic(true);

		methodSym.setDefinition(node);
		node.setSymbol(methodSym);

		currentScope = methodSym;
		currentMethod = methodSym;
	}

	@Override
	public void exitMethodDef(ProphecyAstNode node)
	{
		currentScope = currentScope.getEnclosingScope();
		currentMethod = null;
	}
}
