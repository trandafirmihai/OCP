package chapter4;

import java.time.LocalDate;
import java.util.ArrayList;
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
		System.out.println("The AL is: " + al1);
		
	}
}
