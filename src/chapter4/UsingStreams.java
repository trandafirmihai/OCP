package chapter4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsingStreams {

	public static void main(String[] args) {

		// Java Stream = sequence of data
		// Stream pipeline = the operations that runs in a stream
		// A pipeline can have multiple intermediate operations but only one
		// terminal operation

		Stream<String> empty = Stream.empty();
		Stream<Integer> singleElement = Stream.of(1);
		Stream<Integer> fromArray = Stream.of(1, 2, 3);

		List<String> list = Arrays.asList("a", "b", "c");
		Stream<String> fromList = list.stream();
		Stream<String> fromListParallel = list.parallelStream();

		Stream<Double> randoms = Stream.generate(Math::random);
		// 1 is a seed or a starting value as a first parameter
		// that will be taken by the lambda expression to produce the next value
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);

		System.out.println(empty);
		System.out.println(singleElement);
		
		// Terminal Operations
		System.out.println("Terminal Operations: ");
		// Reductions = return a single value after looking at the entire stream
		Stream<String> s; 
		s = Stream.of("monkey", "gorilla", "bonobo");
		System.out.println("The count of monkeys is: " + s.count());
		// if the line below is commented this Exception will occur
		// java.lang.IllegalStateException: stream has already been operated upon or closed
		// The Stream must be reinitialized before other terminal operations are called on it
		s = Stream.of("monkey", "gorilla", "bonobo");
		Optional<String> min = s.min((s1, s2) -> s1.length() - s2.length());
		min.ifPresent(System.out::println);
		s = Stream.of("monkey", "gorilla", "bonobo");
		Optional<String> max = s.max((s1, s2) -> s1.length() - s2.length());
		max.ifPresent(System.out::println);
		// There is no minimum in the next Stream
		Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
		System.out.println(minEmpty.isPresent()); // false
		
		// Concatenate array
		// traditional way
		String[] array = new String[] {"w", "o", "l", "f"};
		String result = "";
		for(String s1 : array) result = result + s1;
		System.out.println("Concatanate result is: " + result);
		// using lambdas and reduction
		Stream<String> stream = Stream.of("w", "o", "l", "f");
		// the first parameter is the identity (=the value to which the accumulator adds)
		String word = stream.reduce("the ", (s2, c) -> s2 + c);
		System.out.println("Concatenate with reduce method: " + word);
		// method reference
		stream = Stream.of("w", "o", "l", "f");
		word = stream.reduce("", String::concat);
		System.out.println("Concat with method reference: " + word);
		
		Stream<Integer> stream1 = Stream.of(1, 2 ,3 ,4);
		// reduce method returns an Optional<Integer>
		Optional<Integer> multiply = stream1.reduce((i, j) -> i*j );
		System.out.println("the multiply result is: " + multiply);
		stream1 = Stream.of(1, 2 ,3 ,4);
		// reduce method returns an T Object (=in this case an Integer)
		System.out.println("the multiply result with other reduce method signature: "+ stream1.reduce(1, (i, j) -> i*j));
		BinaryOperator<Integer> op = (a, b) -> a*b;
		Stream<Integer> empty1 = Stream.empty();
		Stream<Integer> oneElement = Stream.of(3);
		Stream<Integer> threeElements = Stream.of(3, 5, 6);
		empty1.reduce(op).ifPresent(System.out::println); // no output
		oneElement.reduce(op).ifPresent(System.out::println);
		threeElements.reduce(op).ifPresent(System.out::println);
		// third method signature (used for processing collections in parallel)
		BinaryOperator<Integer> op1 = (a, b) -> a*b;
		threeElements = Stream.of(3, 5, 6);
		Integer multiply1 = threeElements.reduce(1, op1, op1);
		System.out.println("The multiply result with the third reduce method signature: " + multiply1);
		
		// special reduction case: collect() method or mutable reduction
		stream = Stream.of("w", "o", "l", "f");
		// first parameter is a supplier that doesn't take any parameter and returns a value
		// the second parameter is a BiConsumer that takes two parameters and doesn't return anything
		// logic in accumulator and combiner parameter is the same (second and third parameter)
		StringBuilder word1 = stream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
		System.out.println("The result of collect method: " + word1);
		// logic in accumulator and combiner differ
		stream = Stream.of("w", "o", "l", "f");
		TreeSet<String> set = stream.collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
		System.out.println("The result of collect method: " + set);
		// using Collectors class
		stream = Stream.of("w", "o", "l", "f");
		TreeSet<String> set1 = stream.collect(Collectors.toCollection(TreeSet::new));
		System.out.println("The result of collect method using Collectors: " + set1);
		stream = Stream.of("w", "o", "l", "f");
		Set<String> set2 = stream.collect(Collectors.toSet());
		System.out.println("The result of collect method using Collectors: " + set2);

		// Not Reductions = sometimes return a value without processing all of the elements from a stream
		s = Stream.of("monkey", "gorilla", "bonobo");
		Stream<String> infinite = Stream.generate(() -> "chimp");
		s.findAny().ifPresent(System.out::println);
		infinite.findAny().ifPresent(System.out::println);
		// Check if Stream String Objects begin with letters
		List<String> list1 = Arrays.asList("monkey", "2", "chimp");
		Stream<String> infinite1 = Stream.generate(() -> "chimp");
		Predicate<String> pred = x -> Character.isLetter(x.charAt(0));
		System.out.println(list1.stream().anyMatch(pred));
		System.out.println(list1.stream().allMatch(pred));
		System.out.println(list1.stream().noneMatch(pred));
		System.out.println(infinite1.anyMatch(pred));
		
		s = Stream.of("monkey", "gorilla", "bonobo");
		// Streams can't use a traditional loop because they don't implement Iterable interface 
		s.forEach(System.out::println);
		
		// Intermediate Operations
		System.out.println("Intermidiate Operations: ");
		s = Stream.of("monkey", "gorilla", "bonobo");
		s.filter(x -> x.startsWith("m")).forEach(System.out::println);
		Stream<String> s1 = Stream.of("duck", "duck", "duck", "goose");
		s1.distinct().forEach(System.out::println);
		Stream<Integer> s2 = Stream.iterate(1, n -> n+1);
		s2.skip(5).limit(2).forEach(System.out::println);
		// Converting a list of Strings into a list of Integers with their lengths
		s = Stream.of("monkey", "gorilla", "bonobo");
		s.map(String::length).forEach(System.out::println);
		List<String> zero = Arrays.asList();
		List<String> one = Arrays.asList("Bonobo");
		List<String> two = Arrays.asList("Mama Gorilla", "Baby Gorilla");
		Stream<List<String>> animals = Stream.of(zero, one, two);
		animals.flatMap(l -> l.stream()).forEach(System.out::println);
		Stream<String> s3 = Stream.of("brown-", "bear-");
		s3.sorted().forEach(System.out::println);
		Stream<String> s4 = Stream.of("brown bear-", "grizzly-");
		s4.sorted(Comparator.reverseOrder()).forEach(System.out::println);
//		s4.sorted(Comparator::reverseOrder); DOES NOT COMPILE
		Stream<String> s5 = Stream.of("black bear", "brown bear", "grizzly");
		long count = s5.filter(string -> string.startsWith("g")).peek(System.out::println).count();
		System.out.println("The count of bears is: " + count);
		// Danger: Changing State with peek()
		List<Integer> numbers = new ArrayList<>();
		List<Character> letters = new ArrayList<>();
		numbers.add(1);
		letters.add('a');
		Stream<List<?>> stream_ = Stream.of(numbers, letters);
		stream_.map(List::size).forEach(System.out::print); // 11
		System.out.println();
		StringBuilder builder = new StringBuilder();
		Stream<List<?>> good = Stream.of(numbers, letters);
		good.peek(l -> builder.append(l)).map(List::size).forEach(System.out::print); // 11
		System.out.println();
		System.out.println("The builder is: " + builder); // [1][a]
		// Java doesn't prevent from writing bad peek() code
		Stream<List<?>> bad = Stream.of(numbers, letters);
		bad.peek(l -> l.remove(0)).map(List::size).forEach(System.out::print); // 00
		
		// Putting together the Pipeline
		
		
	}

}
