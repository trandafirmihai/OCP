package chapter4.functionalProgramming;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ImplementingFunctionAndBiFunction {

	public static void main(String[] args) {

		// Turning a parameter into a value of a potentially different type and
		// returning it
		Function<String, Integer> f1 = String::length;
		Function<String, Integer> f2 = x -> x.length();
		System.out.println("This function calculates the lentgh of the String parameter: " + f1.apply("cluck"));
		System.out.println(f2.apply("clucko"));

		// The first two types in the BiFunction are the input types
		BiFunction<String, String, String> b1 = String::concat;
		BiFunction<String, String, String> b2 = (string, toAdd) -> string.concat(toAdd);
		System.out.println(b1.apply("baby ", "chicken"));
		System.out.println(b2.apply("baby ", "girl"));

	}
}
