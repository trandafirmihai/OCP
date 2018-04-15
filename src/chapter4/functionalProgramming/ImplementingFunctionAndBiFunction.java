package chapter4.functionalProgramming;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ImplementingFunctionAndBiFunction {

	public static void main(String[] args) {

		// Turning a parameter into a value of a potentially different type and
		// returning it
		Function<String, Integer> f1 = String::length;
		Function<String, Integer> f2 = x -> x.length();
		System.out.print("This function calculates the lentgh of the String parameters: " + f1.apply("cluck") + ", ");
		System.out.println(f2.apply("clucko"));

		// The first two types in the BiFunction are the input types
		BiFunction<String, String, String> b1 = String::concat;
		BiFunction<String, String, String> b2 = (string, toAdd) -> string.concat(toAdd);
		System.out.println(b1.apply("baby ", "chicken"));
		System.out.println(b2.apply("baby ", "duck"));
		
		Supplier<List<Integer>> sli = ArrayList::new;
		List<Integer> listOfInts = sli.get();
		listOfInts.add(1000);
		listOfInts.add(2000);
		Function<List<Integer>, Integer> func = list -> list.size();
		System.out.println("The size of the List is: " + func.apply(listOfInts));
		
		Function<Integer, String> f = (integer) -> Integer.toBinaryString(integer);
		int integer = 1000;
		System.out.println("Binary representation of integer " + integer + " is: " + f.apply(integer));
		
		BiFunction<Integer, Integer, Integer> bf = Integer::compare;
		int int1 = 1000;
		int int2 = 2000;
		System.out.println("The comparation of the integers " + int1 + " & " + int2 + " is: " + bf.apply(int1, int2) );

	}
}
