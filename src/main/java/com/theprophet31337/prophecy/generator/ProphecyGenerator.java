package com.theprophet31337.prophecy.generator;

import com.theprophet31337.prophecy.analyser.symboltable.scope.GlobalScope;
import com.theprophet31337.prophecy.generator.flatmodel.FlatGlobalScope;
import com.theprophet31337.prophecy.generator.flatmodel.Function;
import com.theprophet31337.prophecy.reporting.DefaultProphecyBuildReporter;
import com.theprophet31337.prophecy.reporting.ProphecyBuildListener;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.io.OutputStream;

public class ProphecyGenerator
{
	private ProphecyBuildListener buildListener;
	private GlobalScope globalScope;

	public ProphecyGenerator(GlobalScope globalScope)
	{
		this.globalScope = globalScope;
		this.buildListener = new DefaultProphecyBuildReporter();
	}

	public ProphecyGenerator(GlobalScope globalScope, ProphecyBuildListener buildListener)
	{
		this.globalScope = globalScope;
		this.buildListener = buildListener;
	}

	public void generate(OutputStream fout, ProphecyGeneratorTarget target) throws IOException
	{
		STGroup templates = new STGroupFile(target.getTemplateFileName());

		FlatModelTranslator translator = new FlatModelTranslator(target, templates);
		FlatGlobalScope flatGlobalScope = translator.translate(globalScope);

		for (Function function : flatGlobalScope.getFunctions()) {
			if (function.getInstructions() == null)
				generateMethodBody(function, templates, target.getTargetSpecifics());
		}

		ST template = templates.getInstanceOf(GeneratorTemplateConstants.FILE_TEMPLATE_NAME);

		template.add(GeneratorTemplateConstants.FILE_ARGUMENT_MODEL, flatGlobalScope);

		fout.write(template.render().getBytes());
	}

	private void generateMethodBody(Function function, STGroup templates, GeneratorTargetSpecifics targetSpecifics)
	{
		ProphecyMethodBodyGenerator methodBodyGenerator = new ProphecyMethodBodyGenerator(templates,
				targetSpecifics, buildListener);

		methodBodyGenerator.generateMethodBody(function);

		function.setInstructions(methodBodyGenerator.getOutput());
	}
}
