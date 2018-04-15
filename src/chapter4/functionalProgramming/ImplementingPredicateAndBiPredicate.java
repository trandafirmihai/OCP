package chapter4.functionalProgramming;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ImplementingPredicateAndBiPredicate {
	
	public static void main(String[] args) {
		
		Predicate<String> p1 = String::isEmpty;
		Predicate<String> p2 = x -> x.isEmpty();
		
		System.out.println("Test for an empty string is: " + p1.test(""));
		System.out.println("Test for a not empty string is: " + p2.test("asd"));
		
		BiPredicate<String, String> bp1 = String::startsWith; // Instance method reference
		BiPredicate<String, String> bp2 = (string, prefix) -> string.startsWith(prefix);
		
		System.out.println(bp1.test("chicken", "chick"));
		System.out.println(bp2.test("chicken", "chick"));
		
		Predicate<String> egg = s -> s.contains("egg");
		Predicate<String> brown = s -> s.contains("brown");
		
//		Long to read, containing duplicate code and longer time to change something
		Predicate<String> brownEggs = s -> s.contains("egg") && s.contains("brown");
		Predicate<String> otherEggs = s -> s.contains("egg") && ! s.contains("brown");
		
//		Using two default methods from Predicate interface
		Predicate<String> brownEggs2 = egg.and(brown);
		Predicate<String> otherEggs2 = egg.and(brown.negate());
		
		System.out.println(brownEggs2.test("egg"));
		System.out.println(otherEggs2.test("egg"));
		
		List<String> listOfSweets = new ArrayList<>();
		Predicate<String> pls = listOfSweets::contains;
		listOfSweets.add("sugar");
		listOfSweets.add("icecream");
		listOfSweets.add("candy");
		System.out.println("The sweets List is: " + listOfSweets);
		boolean hasSugar = pls.test("sugar");
		System.out.println("The sweets List has sugar: " + hasSugar);
		boolean hasCream = pls.test("cream");
		System.out.println("The sweets List has cream: " + hasCream);
	}
	
}
