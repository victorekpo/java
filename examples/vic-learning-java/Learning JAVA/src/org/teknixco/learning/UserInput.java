package org.teknixco.learning;
import java.util.Scanner;

public class UserInput {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); // instantiates input variable
		
		System.out.println("Enter a line of text: "); //prompts user to input text 
		
		String line = input.nextLine(); // converts the input to a string
		
		System.out.println("You entered: " + line);
	}
}
