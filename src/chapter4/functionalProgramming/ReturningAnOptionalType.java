package chapter4.functionalProgramming;

import java.util.Optional;

public class ReturningAnOptionalType {

	public static void main(String[] args) {

		// This answers to the questions: "we don't know" and "not applicable"
		// in Java
		// An Optional is created using a factory
		// An Optional is like a box that wraps something
		// It can contain or not an object
		Optional<Double> opt = average(90, 100);
		if (opt.isPresent()) 
			System.out.println(opt.get());
		Optional<Double> opt2 = average(); 
//		System.out.println(opt2.get()); // bad; returns an Exception
		Object value = null;
		Optional o = (value == null) ? Optional.empty(): Optional.of(value);
		Optional o1 = Optional.ofNullable(value);
		
		opt.ifPresent(System.out::println);
		
		System.out.println(opt2.orElse(Double.NaN));
		System.out.println(opt2.orElseGet(() -> Math.random()));
//		System.out.println(opt2.orElseThrow(() -> new  IllegalStateException()));
		
		System.out.println(opt.orElse(Double.NaN));
		System.out.println(opt.orElseGet(() -> Math.random()));
		System.out.println(opt.orElseThrow(() -> new  IllegalStateException()));
		
		// Is Optional the same as null?

	}
	
	// The entire method can be written in one line
	// but this is later in the chapter
	public static Optional<Double> average(int... scores) {
		if (scores.length == 0)
			// return an empty Optional when the average can't be calculated
			return Optional.empty();
		int sum = 0;
		for (int score : scores)
			sum += score;
		return Optional.of((double) sum / scores.length);
	}

	// Additional notes
	// Playing with getClass method it can be fun and useful
	int method(int a) {
		getClass();
		return 0;
	}

}
