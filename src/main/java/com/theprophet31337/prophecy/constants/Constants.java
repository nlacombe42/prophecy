package com.theprophet31337.prophecy.constants;

import com.theprophet31337.prophecy.analyser.symboltable.MethodSignature;
import com.theprophet31337.prophecy.analyser.symboltable.symbol.BuiltInTypeSymbol;

public class Constants
{
	public static final String STRING_CLASS_NAME = "String";

	public static final String SYSTEM_CLASS_NAME = "System";
	public static final MethodSignature SYSTEM_PRINTLN_METHODSIGNATURE;
	public static final String SYSTEM_PRINTLN_PARAM_NAME = "i";

	public static final String MAIN_METHOD_NAME = "main";

	static {
		MethodSignature system_println = new MethodSignature("println");
		system_println.addParameter(BuiltInTypeSymbol.tInt);

		SYSTEM_PRINTLN_METHODSIGNATURE = system_println;
	}
}
