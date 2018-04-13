package chapter4.functionalProgramming;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CheckingFunctionalInterfaces {
	
	public static void main(String[] args) {
		
		// Look at the error provided by the commented ones
		Predicate<List<String>> ex1 = x -> "".equals(x.get(0));
		Consumer<Long> ex2 = (Long l) -> System.out.println(l);
		BiPredicate<String, String> ex3 = (s1, s2) -> false;
		//BiPredicate<String, String> ex3_2 = (s1, s2) - > false;
		Function<List<String>, String> ex4 = x -> x.get(0);
		//Function<List<String>> ex4_2 = x -> x.get(0);
		//UnaryOperator<Long> ex5 = (Long l) -> 3.14;
		//UnaryOperator<Long> ex5_2 = (Long l) -> (Long)3.14;
		UnaryOperator<Long> e5_3 = (Long l) -> (long)3.14;
		Predicate<String> ex6 = String::isEmpty;
		//Predicate ex6_2 = String::isEmpty;
		
		
	}

}
