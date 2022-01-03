package org.teknixco.learning;

public class ForLoops {
	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) { // inside round brackets, you need 2 semicolons, but will just execute infinite
										// loop
			// System.out.println("Hello baby " + i);
			System.out.printf("The value of i is: %d\n", i); // %d means print this string, but replace with the
																// argument.
		}
	}
}
