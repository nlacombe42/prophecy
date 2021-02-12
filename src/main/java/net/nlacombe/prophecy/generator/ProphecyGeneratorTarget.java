package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.generator.llvmir.LlvmIrGeneratorTargetSpecifics;

public enum ProphecyGeneratorTarget
{
	LLVM_IR("llvmir.stg", new LlvmIrGeneratorTargetSpecifics());

	private String templateFileName;
	private GeneratorTargetSpecifics typeConverter;

	private ProphecyGeneratorTarget(String templateFileName, GeneratorTargetSpecifics typeConverter)
	{
		this.templateFileName = templateFileName;
		this.typeConverter = typeConverter;
	}

	public String getTemplateFileName()
	{
		return templateFileName;
	}

	public GeneratorTargetSpecifics getTargetSpecifics()
	{
		return typeConverter;
	}
}
