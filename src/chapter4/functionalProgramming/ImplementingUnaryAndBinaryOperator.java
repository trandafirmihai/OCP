package chapter4.functionalProgramming;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class ImplementingUnaryAndBinaryOperator {
	
	public static void main(String[] args) {
		
	// They require all type parameters to be the same type
	// UnaryOperator extends Function and increment a value by one
	// BinaryOperator merges two values (adding two numbers)
	// It extends BiFunction
	UnaryOperator<String> u1 = String::toUpperCase;
	UnaryOperator<String> u2 = x -> x.toUpperCase();
	// notice above that the return type is not specified in the generic
	// the return type an parameter type are alike
	System.out.println(u1.apply("chirpo"));
	System.out.println(u2.apply("chisrpo"));
	
	BinaryOperator<String> b1 = String::concat;
	BinaryOperator<String> b2 = (string, toAdd) -> string.concat(toAdd);
	System.out.println(b1.apply("birds", " chirp"));
	System.out.println(b2.apply("birds", " chirp"));
	
	UnaryOperator<Boolean> uo = Boolean::valueOf;
	boolean bool = true;
	System.out.println("The Boolean value of boolean "+bool+" is: " + uo.apply(bool));
	
	BinaryOperator<Boolean> bo = Boolean::logicalAnd;
	boolean bool1 = true;
	boolean bool2 = false;
	System.out.println("The boolean value of logicalAnd method between "+bool1+" & " +bool2+" is: " + bo.apply(bool1, bool2));
	bo = Boolean::logicalOr;
	System.out.println("The boolean value of logicalOr method between "+bool1+" & " +bool2+" is: " + bo.apply(bool1, bool2));
	
	}

}
