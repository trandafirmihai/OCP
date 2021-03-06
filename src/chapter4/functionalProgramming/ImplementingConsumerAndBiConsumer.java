package chapter4.functionalProgramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ImplementingConsumerAndBiConsumer {
	
	public static void main(String[] args) {
		
//		Java uses the context of the lambda to determine which overloaded method it should call
		Consumer<String> c1 = System.out::println; // Method reference. 
		Consumer<String> c2 = x -> System.out.println(x);
		
		c1.accept("Annie"); // One argument and print the string
		c2.accept("Annie");
		
		Map<String, Integer> map = new HashMap<>();
//		The BiConsumer parameters don't have to be the same type but can be
		BiConsumer<String, Integer> b1 = map::put; // Instance method reference on a local variable
		BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v); // k and v not-declared variables
		
		b1.accept("word", 2);
		b2.accept("smth", 43);
		b2.accept("smth", 43); // This is not returning an Exception; but the pair is not added|accepted
		
		System.out.println("The map content is: " + map);
		
//		Consumer<List<String>> cls = List::add; // Compile ERROR: cannot make a static reference to the non-static method add
//		List<String> list = ArrayList::new; // Compile ERROR: the target type of this expression must be a functional interface
		List<String> list = new ArrayList<>();
		Consumer<String> cls = list::add;
		cls.accept("accept");
		System.out.println("The List has a String element: " + list);
	}

}
