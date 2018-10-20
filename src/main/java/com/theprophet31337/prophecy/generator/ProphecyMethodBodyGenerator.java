package com.theprophet31337.prophecy.generator;

import com.theprophet31337.prophecy.analyser.symboltable.Type;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.BuiltInTypeSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.MethodSymbol;
import com.theprophet31337.prophecy.ast.ProphecyAstBaseVisitor;
import com.theprophet31337.prophecy.ast.ProphecyAstNode;
import com.theprophet31337.prophecy.ast.ProphecyAstVisitorDispatcher;
import com.theprophet31337.prophecy.ast.nodewrapper.AstCall;
import com.theprophet31337.prophecy.ast.nodewrapper.AstVarDecl;
import com.theprophet31337.prophecy.generator.bean.Argument;
import com.theprophet31337.prophecy.generator.flatmodel.Function;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import java.util.LinkedList;
import java.util.List;

public class ProphecyMethodBodyGenerator extends ProphecyAstBaseVisitor<String>
{
	private ProphecyBuildListener buildListener;
	private STGroup templates;
	private GeneratorTargetSpecifics targetSpecifics;
	private StringBuilder output;
	private int lastUniqueId = 0;
	private boolean generatedReturn = false;

	public ProphecyMethodBodyGenerator(STGroup templates, GeneratorTargetSpecifics targetSpecifics, ProphecyBuildListener buildListener)
	{
		this.templates = templates;
		this.targetSpecifics = targetSpecifics;
		this.buildListener = buildListener;

		output = new StringBuilder();
	}

	public String getOutput()
	{
		return output.toString();
	}

	private String getNewUniqueIdentifierName()
	{
		lastUniqueId++;

		return "_t" + lastUniqueId;
	}

	private void generateAssignment(String lhsName, String lhsType, String rhsName, String rhsType)
	{
		ST template = templates.getInstanceOf(GeneratorTemplateConstants.ASSIGNMENT_TEMPLATE_NAME);

		template.add(GeneratorTemplateConstants.ASSIGNMENT_ARGUMENT_LHSNAME, lhsName);
		template.add(GeneratorTemplateConstants.ASSIGNMENT_ARGUMENT_LHSTYPE, lhsType);
		template.add(GeneratorTemplateConstants.ASSIGNMENT_ARGUMENT_RHSNAME, rhsName);
		template.add(GeneratorTemplateConstants.ASSIGNMENT_ARGUMENT_RHSTYPE, rhsType);
		template.add(GeneratorTemplateConstants.ASSIGNMENT_ARGUMENT_TMPNAME, getNewUniqueIdentifierName());

		output.append(template.render());
	}

	public void generateMethodBody(Function function)
	{
		visitBlock(function.getMethodBodyBlockNode());

		if (!generatedReturn)
			generateReturnVoid();
	}

	@Override
	public String visitBlock(ProphecyAstNode node)
	{
		for (ProphecyAstNode child : node.getChildrenAsArray())
			ProphecyAstVisitorDispatcher.dispatchVisitor(this, child);

		return null;
	}

	@Override
	public String visitReturn(ProphecyAstNode node)
	{
		ST template = templates.getInstanceOf(GeneratorTemplateConstants.RETURN_TEMPLATE_NAME);

		String valueId = ProphecyAstVisitorDispatcher.dispatchVisitor(this, node.getChild(0));
		Type returnType = node.getChild(0).getEvalType();
		String type = targetSpecifics.getTypeName(returnType);

		if (!returnType.equals(BuiltInTypeSymbol.tVoid))
			template.add(GeneratorTemplateConstants.RETURN_ARGUMENT_NAME, valueId);

		template.add(GeneratorTemplateConstants.RETURN_ARGUMENT_TYPE, type);

		output.append(template.render());

		generatedReturn = true;

		return null;
	}

	@Override
	public String visitVarDecl(ProphecyAstNode node)
	{
		//Generate declaration
		ST template = templates.getInstanceOf(GeneratorTemplateConstants.VARDECL_TEMPLATE_NAME);

		String varName = targetSpecifics.getIdentifierName(node.getSymbol().getName());
		String varType = targetSpecifics.getTypeName(node.getSymbol().getType());

		template.add(GeneratorTemplateConstants.VARDECL_ARGUMENT_NAME, varName);
		template.add(GeneratorTemplateConstants.VARDECL_ARGUMENT_TYPE, varType);

		output.append(template.render());

		//Generate initialization
		AstVarDecl vardecl = new AstVarDecl(node);

		if (vardecl.getInitNode() != null) {
			String initValueId = ProphecyAstVisitorDispatcher.dispatchVisitor(this, vardecl.getInitNode());
			String initType = targetSpecifics.getTypeName(vardecl.getInitNode().getEvalType());

			generateAssignment(varName, varType, initValueId, initType);
		}

		return null;
	}

	@Override
	public String visitIntLit(ProphecyAstNode node)
	{
		//Unfortunatelly this is LLVM specific. 

		String tmpName = targetSpecifics.getIdentifierName(getNewUniqueIdentifierName());
		String outname = targetSpecifics.getIdentifierName(getNewUniqueIdentifierName());

		String outputCode = "%" + outname + " = alloca i32\n" +
				"%" + tmpName + " = add i32 0, " + node.getText() + "\n" +
				"store i32 %" + tmpName + ", i32* %" + outname + "\n";

		output.append(outputCode);

		return outname;
	}

	@Override
	public String visitAdd(ProphecyAstNode node)
	{
		ST template = templates.getInstanceOf(GeneratorTemplateConstants.ADDOP_TEMPLATE_NAME);

		String valueId1 = ProphecyAstVisitorDispatcher.dispatchVisitor(this, node.getChild(0));
		String valueId2 = ProphecyAstVisitorDispatcher.dispatchVisitor(this, node.getChild(1));
		String outname = targetSpecifics.getIdentifierName(getNewUniqueIdentifierName());
		String type = targetSpecifics.getBuiltInTypeName((BuiltInTypeSymbol) node.getEvalType());

		template.add(GeneratorTemplateConstants.ADDOP_ARGUMENT_OUTNAME, outname);
		template.add(GeneratorTemplateConstants.ADDOP_ARGUMENT_TYPE, type);
		template.add(GeneratorTemplateConstants.ADDOP_ARGUMENT_NAME1, valueId1);
		template.add(GeneratorTemplateConstants.ADDOP_ARGUMENT_NAME2, valueId2);

		output.append(template.render());

		return outname;
	}

	@Override
	public String visitCall(ProphecyAstNode node)
	{
		AstCall call = new AstCall(node);

		ST template = templates.getInstanceOf(GeneratorTemplateConstants.CALL_TEMPLATE_NAME);

		String returnType = targetSpecifics.getTypeName(node.getEvalType());
		String methodName = targetSpecifics.getMethodName((MethodSymbol) node.getSymbol());
		String outname = targetSpecifics.getIdentifierName(getNewUniqueIdentifierName());

		List<Argument> arguments = new LinkedList<Argument>();
		Argument argument;

		for (ProphecyAstNode child : call.getArgumentsNode().getChildrenAsArray()) {
			argument = new Argument();
			argument.setType(targetSpecifics.getTypeName(child.getEvalType()));
			argument.setValueId(ProphecyAstVisitorDispatcher.dispatchVisitor(this, child));

			arguments.add(argument);
		}

		template.add(GeneratorTemplateConstants.CALL_ARGUMENT_RETURNVOID, BuiltInTypeSymbol.tVoid.equals(node.getEvalType()));
		template.add(GeneratorTemplateConstants.CALL_ARGUMENT_OUTNAME, outname);
		template.add(GeneratorTemplateConstants.CALL_ARGUMENT_RETURNTYPE, returnType);
		template.add(GeneratorTemplateConstants.CALL_ARGUMENT_METHODNAME, methodName);
		template.add(GeneratorTemplateConstants.CALL_ARGUMENT_ARGUMENTS, arguments);

		output.append(template.render());

		return outname;
	}

	@Override
	public String visitId(ProphecyAstNode node)
	{
		ST template = templates.getInstanceOf(GeneratorTemplateConstants.GETVARVALUE_TEMPLATE_NAME);

		String name = targetSpecifics.getIdentifierName(node.getText());
		String outname = targetSpecifics.getIdentifierName(getNewUniqueIdentifierName());
		String type = targetSpecifics.getTypeName(node.getSymbol().getType());

		template.add(GeneratorTemplateConstants.GETVARVALUE_ARGUMENT_OUTNAME, outname);
		template.add(GeneratorTemplateConstants.GETVARVALUE_ARGUMENT_TYPE, type);
		template.add(GeneratorTemplateConstants.GETVARVALUE_ARGUMENT_NAME, name);

		output.append(template.render());

		return outname;
	}

	private void generateReturnVoid()
	{
		ST template = templates.getInstanceOf(GeneratorTemplateConstants.RETURN_TEMPLATE_NAME);

		Type returnType = BuiltInTypeSymbol.tVoid;
		String type = targetSpecifics.getTypeName(returnType);

		template.add(GeneratorTemplateConstants.CALL_ARGUMENT_RETURNVOID, true);
		template.add(GeneratorTemplateConstants.RETURN_ARGUMENT_TYPE, type);

		output.append(template.render());

		generatedReturn = true;
	}
}
