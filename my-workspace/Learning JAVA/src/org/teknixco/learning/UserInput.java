package org.teknixco.learning;
import java.util.Scanner;

public class UserInput {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); // instantiates input variable
		
		System.out.println("Enter a line of text: "); //prompts user to input text 
		
		String line = input.nextLine(); // converts the input to a string, another method is nextInt() for integers, will crash if not integer, nextDouble() is another method. Keep in mind in different parts of the world that doubles are typed differently.
		
		System.out.println("You entered: " + line);
		
		Scanner input2 = new Scanner(System.in); // instantiates input variable
		
		System.out.println("Enter a double: "); //prompts user to input text
		
		double value = input2.nextDouble(); // throws error if not a number
		
		System.out.println("You entered: " + value);
	}
}
