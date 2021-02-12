package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.analyser.symboltable.symbol.ClassSymbol;
import net.nlacombe.prophecy.analyser.symboltable.symbol.MethodSymbol;
import net.nlacombe.prophecy.analyser.symboltable.symbol.Symbol;
import net.nlacombe.prophecy.constants.Constants;
import net.nlacombe.prophecy.generator.flatmodel.FlatGlobalScope;
import net.nlacombe.prophecy.generator.flatmodel.FlatSymbol;
import net.nlacombe.prophecy.generator.flatmodel.Function;
import org.stringtemplate.v4.STGroup;

public class SystemCodeGenerator
{
	private STGroup templates;
	private ProphecyGeneratorTarget target;
	private GeneratorTargetSpecifics targetSpecifics;

	public SystemCodeGenerator(STGroup templates, ProphecyGeneratorTarget target)
	{
		this.templates = templates;
		this.target = target;
		this.targetSpecifics = target.getTargetSpecifics();
	}

	public void generateSystemClass(FlatGlobalScope flatGlobalScope, ClassSymbol classSymbol)
	{
		for (MethodSymbol method : classSymbol.getInstanceMethods())
			generateSystemMethod(flatGlobalScope, method);
	}

	private void generateSystemMethod(FlatGlobalScope flatGlobalScope, MethodSymbol method)
	{
		String name;

		if (method.getName().equals(Constants.MAIN_METHOD_NAME)) {
			name = targetSpecifics.getMainMethodName();
		} else {
			name = targetSpecifics.getMethodName(method);
		}

		String returnType = targetSpecifics.getTypeName(method.getType());

		Function function = new Function(name, returnType);

		if (!method.isStatic()) {
			String className = targetSpecifics.getPointerTypeName(method.getParentClass().getName());
			function.addParameter(new FlatSymbol("this", className));
		}

		String parameterName;
		String parameterType;

		for (Symbol parameter : method.getParameters()) {
			parameterName = targetSpecifics.getIdentifierName(parameter.getName());
			parameterType = targetSpecifics.getTypeName(parameter.getType());

			function.addParameter(new FlatSymbol(parameterName, parameterType));
		}

		if (method.getParentClass().getName().equals(Constants.SYSTEM_CLASS_NAME) &&
				method.getSignature().equals(Constants.SYSTEM_PRINTLN_METHODSIGNATURE))
			generateSystemPrintln(flatGlobalScope, function);

		flatGlobalScope.addFunction(function);
	}

	private void generateSystemPrintln(FlatGlobalScope flatGlobalScope, Function function)
	{
		String printfStrId = "@" + Constants.SYSTEM_CLASS_NAME + "$println$int$printf";

		flatGlobalScope.addCustomStartCode(printfStrId + " = internal constant [4 x i8] c\"%d\\0A\\00\"\n");
		flatGlobalScope.addCustomStartCode("declare i32 @printf(i8*, ...)");

		String methodBody = "%_t1 = getelementptr [4 x i8], [4 x i8]* " + printfStrId + ", i64 0, i64 0\n" +
				"%_t2 = load i32, i32* %" + Constants.SYSTEM_PRINTLN_PARAM_NAME + "\n" +
				"call i32 (i8*, ...) @printf(i8* %_t1, i32 %_t2)\n" +
				"ret void\n";

		function.setInstructions(methodBody);
	}
}
