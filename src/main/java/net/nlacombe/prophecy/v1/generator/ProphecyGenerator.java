package net.nlacombe.prophecy.v1.generator;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.v1.generator.flatmodel.FlatGlobalScope;
import net.nlacombe.prophecy.v1.generator.flatmodel.Function;
import net.nlacombe.prophecy.v1.reporting.DefaultProphecyBuildReporter;
import net.nlacombe.prophecy.v1.reporting.ProphecyBuildListener;
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
