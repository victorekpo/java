package org.teknixco.learning;

public class ArraysofStrings {
	public static void main(String[] args) {
		String[] words = new String[3];
		
		words[0] = "Hello ";
		words[1] = "baby ";
		words[2] = "mmi";
		System.out.println(words[0] + words[1] + words[2]);
		
		String[] greeting = {"Hello", "my", "darling"};
		
		for(String word: greeting) { // easy way to use a for loop for each element, for greet in greeting. 
			System.out.println(word);
		}
		
	}
}
