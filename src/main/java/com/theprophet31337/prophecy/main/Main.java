package com.theprophet31337.prophecy.main;

import com.theprophet31337.prophecy.compiler.ProphecyCompiler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		final String TEST_INPUT = "input.txt";
		final String TEST_OUTPUT = "output.ll";

		ProphecyCompiler compiler = new ProphecyCompiler(new FileInputStream(TEST_INPUT),
				new FileOutputStream(TEST_OUTPUT));

		compiler.compile();
	}
}
