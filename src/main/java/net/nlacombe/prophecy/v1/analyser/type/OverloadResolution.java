package net.nlacombe.prophecy.v1.analyser.type;

import net.nlacombe.prophecy.v1.analyser.symboltable.MethodSignature;
import net.nlacombe.prophecy.v1.analyser.symboltable.Type;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.BuiltInTypeSymbol;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.ClassSymbol;
import net.nlacombe.prophecy.v1.analyser.symboltable.symbol.MethodSymbol;
import net.nlacombe.prophecy.v1.exception.ProphecyAmbiguousCallException;
import net.nlacombe.prophecy.v1.exception.ProphecyMethodNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OverloadResolution
{
	private static class MethodSpecificityComparator implements Comparator<MethodSymbol>
	{
		@Override
		public int compare(MethodSymbol method1, MethodSymbol method2)
		{
			MethodSignature signature1 = method1.getSignature();
			MethodSignature signature2 = method2.getSignature();

			if (signature1.isMoreOrEquallySpecificThan(signature2)) {
				if (signature2.isMoreOrEquallySpecificThan(signature1))
					return 0;
				else
					return 1;
			} else
				return -1;
		}
	}

	public static class Result
	{
		private MethodSymbol method;
		private boolean ratingBased;

		public Result(MethodSymbol method, boolean ratingBased)
		{
			this.method = method;
			this.ratingBased = ratingBased;
		}

		public MethodSymbol getMethod()
		{
			return method;
		}

		public boolean isRatingBased()
		{
			return ratingBased;
		}
	}

	public static Result resolve(ClassSymbol classSymbol, MethodSignature callSignature) throws ProphecyMethodNotFoundException, ProphecyAmbiguousCallException
	{
		/* Steps:
		 *
		 * 1- Get all methods with the same name and compatible types in the class' super class chain.
		 *    1.1- If no method found throw ProphecyMethodNotFoundException.
		 *
		 * 2- If a method signature is an exact match return the selected method.
		 *
		 * 3- If step 2 fails then, pick the most specific method (if only one).
		 *
		 * 4- If step 3 fails then, pick the best rating method (if only one).
		 *
		 * 5- If unsuccessful, throw ProphecyAmbiguousCallException.
		 */

		//1- Get all compatible methods
		Set<MethodSymbol> methods = getCompatibleMethods(classSymbol, callSignature);

		if (methods.size() == 0)
			throw new ProphecyMethodNotFoundException();

		//2- Try to find exact match
		for (MethodSymbol method : methods) {
			if (callSignature.equals(method.getSignature()))
				return new Result(method, false);
		}

		Set<MethodSymbol> pickedMethods;

		//3- Pick most specific method.
		pickedMethods = pickStrictlyMostSpecific(methods);

		if (pickedMethods.size() == 1)
			return new Result(pickedMethods.iterator().next(), false);

		//4- Rates the methods and pick the one with the highest rating.
		pickedMethods = pickBestRating(methods, callSignature);

		if (pickedMethods.size() == 1)
			return new Result(pickedMethods.iterator().next(), true);
		else
			throw new ProphecyAmbiguousCallException();
	}

	private static Set<MethodSymbol> pickStrictlyMostSpecific(Set<MethodSymbol> methods)
	{
		List<MethodSymbol> methodList = new ArrayList<MethodSymbol>(methods);
		int size = methodList.size();
		Set<MethodSymbol> result = new HashSet<MethodSymbol>();
		MethodSpecificityComparator comparator = new MethodSpecificityComparator();

		Collections.sort(methodList, comparator);

		MethodSymbol mostSpecific = methodList.get(size - 1);
		MethodSymbol current;

		result.add(mostSpecific);

		for (int i = size - 2; i >= 0; i--) {
			current = methodList.get(i);

			if (comparator.compare(current, mostSpecific) == 0)
				result.add(current);
			else
				break;
		}

		return result;
	}

	private static Set<MethodSymbol> pickBestRating(Set<MethodSymbol> methods, MethodSignature callSignature)
	{
		Set<MethodSymbol> result = new HashSet<MethodSymbol>();
		/**
		 * The best rating is the smallest.
		 */
		float bestRating = Float.MAX_VALUE;
		float rating;

		for (MethodSymbol method : methods) {
			rating = rateMethodSignatureMatch(method.getSignature().getParameterTypes(), callSignature.getParameterTypes());

			if (rating == bestRating) {
				result.add(method);
			} else if (rating < bestRating) {
				bestRating = rating;
				result.clear();
				result.add(method);
			}
		}

		return result;
	}

	/**
	 * Rates the compatibility between a set of parameter types and a set of argument types.
	 * <p>
	 * The rating is the sum of all the individual parameter-argument types couples.
	 * A smaller rating is better.
	 * <p>
	 * For a parameter-argument couple, the rating is as follow:
	 * - If both types are the same the couple scores 0.
	 * - If the types are both numeric types, the score is the number of conversion
	 * needed to get from the argument type to the parameter type.
	 * - If the types are classes the score is the number of non-common super classes
	 * between the 2 types.
	 */
	private static float rateMethodSignatureMatch(List<Type> parameterTypes, List<Type> argumentTypes)
	{
		float rating = 0;
		Type parameterType;
		Type argumentType;

		for (int i = 0; i < parameterTypes.size(); i++) {
			parameterType = parameterTypes.get(i);
			argumentType = argumentTypes.get(i);

			if (parameterType.equals(argumentType))
				rating += 0; //Just for clarity I still put his code here
			else if (BuiltInTypeSymbol.isNumeric(parameterType) && BuiltInTypeSymbol.isNumeric(argumentType))
				rating += ((BuiltInTypeSymbol) argumentType).numberOfConversions((BuiltInTypeSymbol) parameterType);
			else {
				List<ClassSymbol> parameterClasses = ((ClassSymbol) parameterType).getSuperClasses();
				parameterClasses.add((ClassSymbol) parameterType);

				List<ClassSymbol> argumentClasses = ((ClassSymbol) argumentType).getSuperClasses();
				argumentClasses.add((ClassSymbol) argumentType);

				for (ClassSymbol classSymbol : argumentClasses) {
					if (!parameterClasses.contains(classSymbol))
						rating += 1;
				}
			}
		}

		return rating;
	}

	private static Set<MethodSymbol> getCompatibleMethods(ClassSymbol classSymbol, MethodSignature callSignature)
	{
		Set<MethodSymbol> resultSet = new HashSet<MethodSymbol>();
		Set<MethodSymbol> methods = classSymbol.resolveMethods(callSignature.getName());

		for (MethodSymbol method : methods) {
			if (method.getSignature().isCallableWith(callSignature))
				resultSet.add(method);
		}

		return resultSet;
	}
}
