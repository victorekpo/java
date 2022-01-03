package org.teknixco.learning;
import java.io.IOException;
import java.util.Scanner;

public class SwitchStatements {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);		
		try {
			System.out.println("Please enter a command: ");
			
			String text = input.nextLine();
			// Switch can be used in two ways, to get particular cases or to start at a particular case, break command separates cases, without case it will go to the next one until it finds a break.
			switch(text) { // switch is most commonly used with strings and integers, for every value you want to check, you need a case.
			case "start": 
				System.out.println("Machine started!");
				break; // break is needed or it will go to the next case until it reaches break
				
			case "stop": 
				System.out.println("Machine stopped.");
				break; 
				
			default: //default case goes at the bottom
				System.out.println("Command not recognized");

			} // close switch
		} // end try block
		finally {
			input.close();
		}

		
	}
}
