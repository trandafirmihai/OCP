package chapter4.checkedExceptionsAndFunctionalInterfaces;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class ExceptionCaseStudy {

	private static List<String> create() throws IOException {
		throw new IOException();
	}
	
	public static void main(String[] args) 
											throws IOException
	{
		// if main doesn't thrown the Exception the code will not compile
		ExceptionCaseStudy.create().stream().count(); 
	
		// The Supplier Interface does not allow checked exceptions
//		Supplier<List<String>> s = ExceptionCaseStudy::create; // Does not compile
		
		Supplier<List<String>> s1 = () -> {
			try {
				return ExceptionCaseStudy.create();
			}
			catch(IOException ioe) {
				throw new RuntimeException();
			}
		};
		
		Supplier<List<String>> s2 = ExceptionCaseStudy::createSafe;
	}
	
	private static List<String> createSafe() {
		try {
			return ExceptionCaseStudy.create();
		}
		catch(IOException ioe) {
			throw new RuntimeException();
		}
	}
	
}
