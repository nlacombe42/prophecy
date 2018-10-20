package com.theprophet31337.prophecy.util;

public class GenericUtil
{
	public static String repeat(String str, int times)
	{
		if (str == null)
			throw new IllegalArgumentException("str cannot be null");

		if (times < 0)
			throw new IllegalArgumentException("times must be greater than or equal to 0");

		StringBuilder ret = new StringBuilder(str.length() * times);

		for (int i = 0; i < times; i++)
			ret.append(str);

		return ret.toString();
	}

	public static String[] joinArray(String[] arr1, String[] arr2)
	{
		if (arr1 == null || arr2 == null)
			throw new IllegalArgumentException("both arrays must not be null");

		String[] result = new String[arr1.length + arr2.length];

		int pos = 0;

		for (String element : arr1) {
			result[pos] = element;
			pos++;
		}

		for (String element : arr2) {
			result[pos] = element;
			pos++;
		}

		return result;
	}
}
