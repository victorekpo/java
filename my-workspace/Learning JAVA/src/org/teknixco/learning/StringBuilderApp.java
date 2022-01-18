package org.teknixco.learning;

public class StringBuilderApp {
	public static void main(String[] args) {
		// Inefficient every time you create a string, it does not get replaced but a
		// new string is created when you add to it, this can lead to memory issues.
		// This is because strings are immutable and cannot be changed.
		String info = "";
		info += "My name is Vic.";
		info += " ";
		info += "I am a software engineer.";
		System.out.println(info);

		// More efficient.
		StringBuilder sb = new StringBuilder(); // use StringBuffer for multiple threads.
												// StringBuilder is more light-weight.

		sb.append("My name is Vic.")
				.append(" ") // chaining methods
				.append("I am a software engineer.");

		System.out.println(sb.toString());

	}
}