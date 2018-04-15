package chapter4.functionalProgramming;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ImplementingSupplier {

	public static void main(String[] args) {

		// Use Generic to declare the type of Supplier
		Supplier<LocalDate> s1 = LocalDate::now; // Method reference
		Supplier<LocalDate> s2 = () -> LocalDate.now();
		
		LocalDate d1 = s1.get();  // No argument and get a date
		LocalDate d2 = s2.get();
		
		System.out.println("Date 1 is : " + d1);
		System.out.println("Date 2 is : " + d2);
		
		Supplier<StringBuilder> s3 = StringBuilder::new; // Constructor reference to create the object
		Supplier<StringBuilder> s4 = () -> new StringBuilder();
//		Supplier<StringBuilder> s5 = new StringBuilder(); Wrong (Cannot convert types)
		
		// Empty SBs
		System.out.println("SB1 is : " + s3.get());
		System.out.println("SB2 is : " + s4.get());
		
		StringBuilder sb3NotEmpty = s3.get().append("smth");
		System.out.println("SB3 not empty is: " + sb3NotEmpty);
		System.out.println("SB4 not empty is : " + s4.get().append(3002)); // append integer
	
		Supplier<ArrayList<String>> sAL = ArrayList::new;
		ArrayList<String> al1 = sAL.get();
		al1.add(0, "smth"); // Any other index gives Exception; so use add simple on an empty AL
		al1.add("smthelse");
		al1.add(2, "smthnew"); // only indexes 0, 1 or 2 works here
		System.out.println("The AL is: " + al1);
		
		Supplier<List<Integer>> sli = ArrayList::new;
		System.out.println("Supplier get an empty List? " + sli.get()); // return a List
		System.out.println("Supplier get the empty List and add an element? " + sli.get().add(2));
		System.out.println("Supplier get the List and return its size? " + sli.get().size()); // size 0; return an other List
		sli.get().add(2); // List size 0; return and add to an other List
		sli.get().add(3); // List size 0; return and add to an other List
		List<Integer> list = sli.get(); // save the returned List to a variable and then add elements
		System.out.println("Supplier get the right size of the List now? " + list.size()); // size 0; return an other List
		list.add(2);
		System.out.println("Supplier get the right size of the List now? " + list.size());
		
		
	}
}
