package net.nlacombe.prophecy.v1.analyser.symboltable;

import net.nlacombe.prophecy.v1.analyser.symboltable.scope.GlobalScope;
import net.nlacombe.prophecy.v1.analyser.symboltable.scope.LocalScope;
import net.nlacombe.prophecy.v1.analyser.symboltable.scope.Scope;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.ClassSymbol;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.MethodSymbol;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.Symbol;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.VariableSymbol;
import net.nlacombe.prophecy.v1.ast.ProphecyAstBaseListener;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNode;
import net.nlacombe.prophecy.v1.ast.ProphecyAstNodeType;
import net.nlacombe.prophecy.v1.ast.nodewrapper.AstClassDef;
import net.nlacombe.prophecy.v1.ast.nodewrapper.AstFieldDef;
import net.nlacombe.prophecy.v1.ast.nodewrapper.AstMethodDef;
import net.nlacombe.prophecy.v1.ast.nodewrapper.AstParam;
import net.nlacombe.prophecy.v1.ast.nodewrapper.AstVarDecl;
import net.nlacombe.prophecy.v1.constants.Constants;
import net.nlacombe.prophecy.shared.reporting.BuildMessageLevel;
import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;

public class SymbolDefiner extends ProphecyAstBaseListener
{
    private final ProphecyBuildListener buildListener;

	private Scope currentScope;
	private MethodSymbol currentMethod;
	private GlobalScope globalScope;

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
		globalScope = getGlobalScopeWithBuiltInSymbols();
		currentScope = globalScope;
	}

    public static GlobalScope getGlobalScopeWithBuiltInSymbols() {
        var globalScope = new GlobalScope();

        BuiltInTypeSymbol.BUILT_IN_TYPES.forEach(globalScope::define);

        var stringClass = new ClassSymbol(Constants.STRING_CLASS_NAME, globalScope, null, true);
        globalScope.define(stringClass);

        var systemClass = getSystemClass(globalScope);
        globalScope.define(stringClass);

        var systemObject = new VariableSymbol("system", systemClass);
        globalScope.define(systemObject);

        return globalScope;
    }

    private static ClassSymbol getSystemClass(GlobalScope globalScope) {
        var systemClass = new ClassSymbol(Constants.SYSTEM_CLASS_NAME, globalScope, null);
        systemClass.setSystem(true);

        var system_println = new MethodSymbol(Constants.SYSTEM_PRINTLN_METHODSIGNATURE.getName(), BuiltInTypeSymbol.tVoid, systemClass, new LocalScope(systemClass));
        system_println.setStatic(true);
        system_println.putMember(new VariableSymbol(Constants.SYSTEM_PRINTLN_PARAM_NAME, BuiltInTypeSymbol.tInt));
        systemClass.putMember(system_println);

        return systemClass;
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
