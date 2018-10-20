package com.theprophet31337.prophecy.analyser.symboltable;

import com.theprophet31337.prophecy.analyser.symboltable.symbol.ArraySymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.BuiltInTypeSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.ClassSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.MethodSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.Symbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.VariableSymbol;
import com.theprophet31337.prophecy.ast.ProphecyAstBaseListener;
import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstNodeType;
import com.theprophet31337.prophecy.ast.ProphecyAstUtil;
import com.theprophet31337.prophecy.ast.nodewrapper.AstCast;
import com.theprophet31337.prophecy.ast.nodewrapper.AstClassDef;
import com.theprophet31337.prophecy.ast.nodewrapper.AstFieldDef;
import com.theprophet31337.prophecy.ast.nodewrapper.AstMember;
import com.theprophet31337.prophecy.ast.nodewrapper.AstMethodDef;
import com.theprophet31337.prophecy.ast.nodewrapper.AstParam;
import com.theprophet31337.prophecy.ast.nodewrapper.AstVarDecl;
import com.theprophet31337.prophecy.reporting.BuildMessageLevel;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;

public class SymbolResolver extends ProphecyAstBaseListener
{
	private ProphecyBuildListener buildListener;

	public SymbolResolver(ProphecyBuildListener buildListener)
	{
		this.buildListener = buildListener;
	}

	@Override
	public void enterClassDef(ProphecyAstNode node)
	{
		AstClassDef classdef = new AstClassDef(node);
		ClassSymbol classSym = (ClassSymbol) node.getSymbol();

		ProphecyAstNode superClassDef = classdef.getSuperclassNode();

		if (superClassDef.getType() != ProphecyAstNodeType.NULL) {
			SymbolSignature superClassSignature = new SymbolSignature(superClassDef.getText());
			ClassSymbol superClassSym = (ClassSymbol) superClassDef.getScope().resolve(superClassSignature);

			if (superClassSym == null) {
				buildListener.buildMessage(BuildMessageLevel.ERROR,
						superClassDef.getLineNumber(), superClassDef.getColumn(),
						"unresolved superclass type: \"" + superClassDef.getText() + "\"");
				return;
			} else if (superClassSym == classSym) {
				buildListener.buildMessage(BuildMessageLevel.ERROR,
						superClassDef.getLineNumber(), superClassDef.getColumn(),
						"a class cannot be its own superclass.");
				return;
			}

			superClassDef.setSymbol(superClassSym);

			classSym.setSuperClass(superClassSym);
		}
	}

	@Override
	public void enterMethodDef(ProphecyAstNode node)
	{
		AstMethodDef methdef = new AstMethodDef(node);
		MethodSymbol methSym = (MethodSymbol) node.getSymbol();

		methSym.setType(getTypeSymbol(methdef.getTypeNode()));

		//Resolve type for all parameters
		AstParam paramDef;

		for (ProphecyAstNode paramNode : methdef.getParameterNodes()) {
			paramDef = new AstParam(paramNode);

			Type type = getTypeSymbol(paramDef.getTypeNode());

			if (type == BuiltInTypeSymbol.tVoid) {
				buildListener.buildMessage(BuildMessageLevel.ERROR,
						paramDef.getTypeNode().getLineNumber(), paramDef.getTypeNode().getColumn(),
						"parameter declaration cannot be of type void");
				type = null;
			}

			SymbolSignature signature = new SymbolSignature(paramDef.getNameText());
			methSym.getMember(signature).setType(type);
		}
	}

	@Override
	public void enterVarDecl(ProphecyAstNode node)
	{
		AstVarDecl varDef = new AstVarDecl(node);
		VariableSymbol varSym = (VariableSymbol) node.getSymbol();

		Type type = getTypeSymbol(varDef.getTypeNode());

		if (type == BuiltInTypeSymbol.tVoid) {
			buildListener.buildMessage(BuildMessageLevel.ERROR,
					varDef.getTypeNode().getLineNumber(), varDef.getTypeNode().getColumn(),
					"variable declaration cannot be of type void");
			type = null;
		}

		varSym.setType(type);
	}

	@Override
	public void enterFieldDef(ProphecyAstNode node)
	{
		AstFieldDef fieldDef = new AstFieldDef(node);
		VariableSymbol fieldSym = (VariableSymbol) node.getSymbol();

		fieldSym.setType(getTypeSymbol(fieldDef.getTypeNode()));
	}

	@Override
	public void exitMember(ProphecyAstNode node)
	{
		AstMember memberOpNode = new AstMember(node);

		ProphecyAstNode objectNode = memberOpNode.getObjectNode();
		ProphecyAstNode memberNode = memberOpNode.getMemberNode();

		if (objectNode.getSymbol() == null) {
			//if the object node was not resolved, resolve it.

			if (objectNode.getType() != ProphecyAstNodeType.ID) {
				//if the object node is not an ID node it means there was an error resolving it.
				return;
			}

			resolveIdNode(objectNode);
		}

		Symbol objectSymbol = objectNode.getSymbol();

		if (objectSymbol == null)
			return;

		//check to make sure the object symbol has a Class type (can contain members).
		if (!(objectSymbol.getType() instanceof ClassSymbol)) {
			buildListener.buildMessage(BuildMessageLevel.ERROR,
					objectNode.getLineNumber(), objectNode.getColumn(),
					"identifier \"" + objectNode.getText() + "\" is not a class type.");

			objectNode.setSymbol(null);
			return;
		}

		ClassSymbol objectSymbolType = (ClassSymbol) objectSymbol.getType();

		//Resolve member

		if (objectSymbolType.resolveMethods(memberNode.getText()).size() > 0) {
			//let the parent node (CALL) take care of the resolution.
			return;
		}

		SymbolSignature signature = new SymbolSignature(memberNode.getText());
		Symbol memberSymbol = objectSymbolType.resolveMember(signature);

		if (memberSymbol == null) {
			buildListener.buildMessage(BuildMessageLevel.ERROR,
					memberNode.getLineNumber(), memberNode.getColumn(),
					"object \"" + objectNode.getText() + "\" does not contain field \"" + memberNode.getText() + "\"");
		}

		memberNode.setSymbol(memberSymbol);
	}

	/**
	 * If the ID is a reference, resolve the reference.
	 */
	@Override
	public void exitId(ProphecyAstNode node)
	{
		//If the parent is a MEMBER ast it is taken care of by exitMember()
		//If the parent is a CALL ast it is taken care of by exitCall()
		if (ProphecyAstUtil.isDefinition(node.getParent()) ||
				node.getParent().getType() == ProphecyAstNodeType.MEMBER ||
				node.getParent().getType() == ProphecyAstNodeType.CALL)
			return;

		resolveIdNode(node);
	}

	@Override
	public void exitCast(ProphecyAstNode node)
	{
		AstCast cast = new AstCast(node);

		ProphecyAstNode typeNode = cast.getTypeNode();

		SymbolSignature signature = new SymbolSignature(typeNode.getText());
		Symbol symbol = typeNode.getScope().resolve(signature);

		if (symbol == null || !(symbol instanceof Type)) {
			buildListener.buildMessage(BuildMessageLevel.ERROR,
					node.getLineNumber(), node.getColumn(),
					"unknown type \"" + node.getText() + "\"");
		}

		typeNode.setEvalType((Type) symbol);
	}

	private Symbol resolveIdNode(ProphecyAstNode node)
	{
		if (node.getText().equals("test")) {
			System.out.println();
		}

		SymbolSignature signature = new SymbolSignature(node.getText());
		Symbol symbol = node.getScope().resolve(signature);

		if (symbol == null) {
			buildListener.buildMessage(BuildMessageLevel.ERROR,
					node.getLineNumber(), node.getColumn(),
					"unknown identifier \"" + node.getText() + "\"");
		}

		node.setSymbol(symbol);

		return symbol;
	}

	private Type getTypeSymbol(ProphecyAstNode node)
	{
		if (node.getText().endsWith("[]")) {
			String elementName = node.getText().substring(0, node.getText().length() - 2);

			ArraySignature signature = new ArraySignature(elementName);
			Type elementType = (Type) node.getScope().resolve(signature);

			if (elementType == null) {
				buildListener.buildMessage(BuildMessageLevel.ERROR,
						node.getLineNumber(), node.getColumn(),
						"unresolved type: \"" + node.getText() + "\"");
			}

			return new ArraySymbol(node.getText(), elementType);
		} else {
			SymbolSignature signature = new SymbolSignature(node.getText());
			Type type = (Type) node.getScope().resolve(signature);

			if (type == null) {
				buildListener.buildMessage(BuildMessageLevel.ERROR,
						node.getLineNumber(), node.getColumn(),
						"unresolved type: \"" + node.getText() + "\"");
			}

			return type;
		}
	}
}
