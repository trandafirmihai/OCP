package chapter4.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
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
		// Java 7
		List<String> names = Arrays.asList("Jennifer", "Daniel", "Mark", "Ginny", "Berta" );
		List<String> filteredNames = new ArrayList<>();
		for (String name : names) {
			if(name.length() == 5) 
				filteredNames.add(name);
		}
		Collections.sort(filteredNames);
		Iterator<String> iter = filteredNames.iterator();
		System.out.println();
		if(iter.hasNext()) System.out.println("Name 1 is : "+ iter.next());
		if(iter.hasNext()) System.out.println("Name 2 is : "+ iter.next());
		
		// Java 8
		System.out.println("Filter and print a list in Java 8: ");
		System.out.println("Focusing on what and not on how..");
		names.stream().filter(n -> n.length() == 5).sorted().limit(2).forEach(System.out::println);
		
		// Generates an infinite Stream; an Exception Out of memory is thrown if run it
//		Stream.generate(() -> "Elsa").filter(n -> n.length() == 4).sorted().limit(2).forEach(System.out::println);
		// Prints Elsa two times
		Stream.generate(() -> "Elsa").filter(n -> n.length() == 4).limit(2).sorted().forEach(System.out::println);
		// Generate an infinnite Stream also and throws an Exception Out of memory
//		Stream.generate(() -> "Olaf Lazisson").filter(n -> n.length() == 4).limit(2).sorted().forEach(System.out::println);
		
		Stream<Integer> infinite2 = Stream.iterate(1, x -> x + 1);
		infinite2.limit(5).filter(x -> x % 2 == 1).forEach(System.out::print);
		System.out.println();
		infinite2 = Stream.iterate(1, x -> x + 1);
		infinite2.limit(5).peek(System.out::print).filter(x -> x % 2 == 1).forEach(System.out::print);
		System.out.println();
		// Reversing the order of the intermediate operations changes the result:
		infinite2 = Stream.iterate(1, x -> x + 1);
		infinite2.filter(x -> x % 2 == 1).limit(5). forEach(System.out::print);
		System.out.println();
		infinite2 = Stream.iterate(1, x -> x + 1);
		infinite2.filter(x -> x % 2 == 1).peek(System.out::print).limit(5). forEach(System.out::print);
		System.out.println();
		
		// Working with primitives
		Stream<Integer> intPrimitive = Stream.of(1, 2, 3);
		System.out.println("Print primitives: " + intPrimitive.reduce(0, (s6, n)->s6+n));
		intPrimitive = Stream.of(1, 2, 3);
		System.out.println("Print primitives using IntStream: " + intPrimitive.mapToInt(x->x).sum());
		IntStream intStream = IntStream.of(4, 5, 6);
		OptionalDouble avg = intStream.average();
		System.out.println("The average as double is: " + avg.getAsDouble());
		
		Stream<String> objStream = Stream.of("penguin", "fish");
		IntStream intStream1 = objStream.mapToInt(s7 -> s7.length());
		intStream1.forEach(System.out::println);
		List<Integer> ints = Arrays.asList(2, 3, 4);
		IntStream ints2 = ints.stream().flatMapToInt(x -> IntStream.of(6,7, 8,9));
		ints.stream().forEach(System.out::print);
		System.out.println();
		ints2.forEach(System.out::print);
		System.out.println();
		
		// Using Optional with Primitive Streams
		IntStream intStream2 = IntStream.rangeClosed(1, 10);
		OptionalDouble optionalDouble = intStream2.average();
		optionalDouble.ifPresent(System.out::println);
		System.out.println(optionalDouble.getAsDouble());
		System.out.println(optionalDouble.orElseGet(() -> Double.NaN));
		
		LongStream longs = LongStream.of(5, 10);
		long sum = longs.sum();
		System.out.println("The sum of longs from stream is: " + sum);
		
		// Functional Interfaces for Primitives
		// BooleanSupplier
		BooleanSupplier b1 = () -> true;
		BooleanSupplier b2 = () -> Math.random() > .5;
		System.out.println("Boolean Supplier 1 is: " + b1.getAsBoolean());
		System.out.println("Boolean Supplier 2 is: " + b2.getAsBoolean());
		
		// DoubleToInt functional interface
		double d = 5.0;
		DoubleToIntFunction dif = x -> (int)x / 2;
		int intFromDouble = dif.applyAsInt(d);
		System.out.println("int from double is: " + intFromDouble);
		
		// Advanced Stream Pipeline Concepts
		List<String> cats = new ArrayList<>();
		cats.add("Ridley");
		cats.add("Cody");
		Stream <String> streamOfCats = cats.stream();
//		System.out.println("The no. of cats from stream is: " + streamOfCats.count());
		//add to list after stream created from it
		cats.add("Mice"); // this element can't be added if count were before called on the stream
		System.out.println("The no. of cats from stream is: " + streamOfCats.count());
		
		// Chaining Optionals
		// return the three digit number without functional programming
		Optional<Integer> intOpt2 = Optional.of(333);
		if(intOpt2.isPresent()) {
			Integer num = intOpt2.get();
			String str = "" + num;
			if(str.length() == 3) {
				System.out.println("The three digit no. is: " + str);
			}
		}
		// return the three digit number with functional programming
		Optional<Integer> intOpt = Optional.of(333);
		intOpt.map(n -> ""+ n).filter(m -> m.length() == 3).ifPresent(System.out::println);
		
		Optional<String> stringOpt = Optional.of("string");
		Optional<Integer> intOpt3 = stringOpt.map(String::length);
		intOpt3.ifPresent(System.out::println);
		
		// Collecting Results
		// using basic collectors
		Stream<String> felines = Stream.of("lion", "cat", "tiger", "jaguar");
		String joiningFelines = felines.collect(Collectors.joining(", "));
		System.out.println("Joining elements of a Stream of Strings using Collectors: " + joiningFelines);
		
		felines = Stream.of("lion", "cat", "tiger", "jaguar");
		Double avgLength = felines.collect(Collectors.averagingInt(String::length));
		System.out.println("Calculating the average length of the stream elements: " + avgLength);
		
		felines = Stream.of("lion", "cat", "tiger", "jaguar");
		TreeSet<String> treeSetFelines = felines.filter(f -> f.startsWith("j")).collect(Collectors.toCollection(TreeSet::new));
		System.out.println("Convert Stream into a colection after filtering it: " + treeSetFelines);
		
		// collecting into maps
		felines = Stream.of("lion", "cat", "tiger", "jaguar");
//		Map<String, Integer> collectToMap = felines.collect(Collectors.toMap(f -> f, String::length));
		Map<String, Integer> collectToMap = felines.collect(Collectors.toMap(Function.identity(), String::length)); 
		System.out.println("Collecting to a map after applying two functions on a Stream elements: " + collectToMap);
		// reverse the key value map pair
		// BAD approach, because the length of the animals are different
		felines = Stream.of("lion", "cat", "tiger", "jaguar");
		Map<Integer, String> collectToMap1 = felines.collect(Collectors.toMap(String::length, Function.identity()));
		System.out.println(collectToMap1);
		// what if many lengths were the same; an Exception will be thrown
		Stream<String> animals1 = Stream.of("lions", "bears", "wolves");
		// concatenate  the ones with the same length
		Map<Integer, String> collectToMap2 = animals1.collect(Collectors.toMap(String::length, Function.identity(), (a,b)-> a+", "+b));
		System.out.println("Collecting to a map avoiding to duplicate a key " + collectToMap2);
		System.out.println("The returning implementation of the Map it is not guaranteed: " + collectToMap2.getClass());
		
		animals1 = Stream.of("lions", "bears", "wolves");
		TreeMap<Integer, String> collectToTreeMap = animals1.collect(Collectors.toMap(String::length, a -> a, (a, b) -> a+", "+b, TreeMap::new));
		System.out.println("Collecting into a specified Map implementation: " + collectToTreeMap);
		System.out.println("The class specified for this collection: " + collectToTreeMap.getClass());
		
		// Collecting using Grouping, Partitioning, Mapping
		animals1 = Stream.of("lions", "bears", "wolves");
		Map<Integer, List<String>> mapGrouped = animals1.collect(Collectors.groupingBy(String::length));
		System.out.println("Grouped Map is: " + mapGrouped);
		animals1 = Stream.of("lions", "bears", "wolves", "ant", "ape");
		Map<Boolean, List<String>> mapGrouped1 = animals1.collect(Collectors.groupingBy(ss -> ss.startsWith("a")));
		System.out.println("Grouped Map by letter is: " + mapGrouped1);
		
		animals1 = Stream.of("lions", "bears", "wolves", "wolves", "ant", "ape", "ape");
		Map<Integer, Set<String>> mapWithSetGrouped = animals1.collect(Collectors.groupingBy(String::length, Collectors.toSet()));
		System.out.println("Map with Set grouped " + mapWithSetGrouped);
		// Change the type of returned Map
		animals1 = Stream.of("lions", "bears", "wolves", "wolves", "ant", "ape", "ape");
		TreeMap<Integer, Set<String>> treeMapWithSetGrouped = animals1.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));
		System.out.println("TreeMap with Set grouped is : " + treeMapWithSetGrouped);
		
		animals1 = Stream.of("lions", "bears", "wolves", "wolves", "ant", "ape", "ape");
		TreeMap<Integer, List<String>> treeMapWithListGrouped = animals1.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));
		System.out.println("TreeMap with List grouped is : " + treeMapWithListGrouped);
		
		// Partitioning - splitting in two parts
		animals1 = Stream.of("lions", "bears", "wolves", "ant", "ape");
		Map<Boolean, List<String>> mapPartitioned = animals1.collect(Collectors.partitioningBy(ss -> ss.length() <= 5));
		System.out.println("Partitioned Map is : " + mapPartitioned);
		
		animals1 = Stream.of("lions", "bears", "wolves", "ant", "ape");
		Map<Boolean, List<String>> mapPartitioned2 = animals1.collect(Collectors.partitioningBy(ss -> ss.length() <= 7));
		System.out.println("Partitioned Map is : " + mapPartitioned2);
		
		animals1 = Stream.of("lions", "bears", "wolves", "wolves", "ant", "ape", "ape");
		Map<Boolean, Set<String>> mapWithSetPartitioned = animals1.collect(Collectors.partitioningBy(ss -> ss.length() <= 5, Collectors.toSet()));
		System.out.println("Partitioned Map with Set is : " + mapWithSetPartitioned);
		// Cannot change the type of returned Map like with groupingBy()
		animals1 = Stream.of("lions", "bears", "wolves", "ant", "ape");
		Map<Integer, Long> mapGrouped2 = animals1.collect(Collectors.groupingBy(String::length, Collectors.counting()));
		System.out.println("Map grouped by counting is : " + mapGrouped2);
		
		animals1 = Stream.of("lions", "bears", "wolves", "wolves", "ant", "ape", "ape");
		Map<Integer, Optional<Character>> mapGroupedByMapping = animals1.collect(Collectors.groupingBy(String::length, 
				Collectors.mapping(ss -> ss.charAt(0), Collectors.minBy(Comparator.naturalOrder()))));
		System.out.println("Map grouped by Mapping is : " + mapGroupedByMapping);
		

	
	}

}