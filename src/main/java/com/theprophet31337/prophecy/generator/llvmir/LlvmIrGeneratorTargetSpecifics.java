package com.theprophet31337.prophecy.generator.llvmir;

import com.theprophet31337.prophecy.analyser.symboltable.Type;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.BuiltInTypeSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.ClassSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.MethodSymbol;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.Symbol;
import com.theprophet31337.prophecy.exception.ProphecyNotImplementedException;
import com.theprophet31337.prophecy.generator.GeneratorTargetSpecifics;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LlvmIrGeneratorTargetSpecifics implements GeneratorTargetSpecifics
{
	@Override
	public String getBuiltInTypeName(BuiltInTypeSymbol type)
	{
		if (type.equals(BuiltInTypeSymbol.tInt))
			return "i32";
		else if (type.equals(BuiltInTypeSymbol.tChar))
			return "i8";
		else if (type.equals(BuiltInTypeSymbol.tVoid))
			return "void";
		else
			throw new ProphecyNotImplementedException();
	}

	@Override
	public String getLiteralValue(BuiltInTypeSymbol type, String literalValueText)
	{
		if (BuiltInTypeSymbol.isNumeric(type))
			return literalValueText;
		else
			throw new ProphecyNotImplementedException();
	}

	@Override
	public String getIdentifierName(String sourceName)
	{
		return sourceName;
	}

	@Override
	public String getIdentifierName(String... nameParts)
	{
		return StringUtils.join(nameParts, '$');
	}

	@Override
	public String getPointerTypeName(String sourceName)
	{
		return "%" + getIdentifierName(sourceName) + "*";
	}

	@Override
	public String getPointerTypeName(String... nameParts)
	{
		return "%" + getIdentifierName(nameParts) + "*";
	}

	@Override
	public String getMainMethodName()
	{
		return "main";
	}

	@Override
	public String getTypeName(Type type)
	{
		if (type instanceof BuiltInTypeSymbol)
			return getBuiltInTypeName((BuiltInTypeSymbol) type);
		else if (type instanceof ClassSymbol) {
			ClassSymbol classSymbol = (ClassSymbol) type;

			return getIdentifierName(classSymbol.getName());
		} else
			throw new ProphecyNotImplementedException();
	}

	@Override
	public String getMethodName(MethodSymbol method)
	{
		List<String> nameParts = new ArrayList<String>(method.getParameters().size() + 2);

		nameParts.add(method.getParentClass().getName());
		nameParts.add(method.getName());

		for (Symbol parameter : method.getParameters())
			nameParts.add(parameter.getType().getName());

		return getIdentifierName(nameParts.toArray(new String[0]));
	}
}
