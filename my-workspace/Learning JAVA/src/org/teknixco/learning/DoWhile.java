package org.teknixco.learning;
import java.io.IOException;
import java.util.Scanner;

public class DoWhile {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		try {
					
		/*Example 1: Using a While Loop, Not Efficient because of repetition
		System.out.println("Enter a number: ");
		int value = input.nextInt();

		while(value !=5) {
			System.out.println("Enter a number: ");
			value = input.nextInt();
		}
		*/
		
		// Example 2: The do while loop executes at least once, condition is checked at the end.
		int value; // declare outside of brackets for variable scope
		do {
		  System.out.println("Enter a number: ");
		  value = input.nextInt();
		}
		while(value != 5);
		// The code loops and doesn't reach the next line until the while loop is broken. 
		System.out.println("Got 5!");
		}		
		finally {
			input.close();
		}
		
		
	}
}
