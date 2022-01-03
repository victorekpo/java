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
		
		// int value = 0; // allocates enough memory to hold an integer, can only hold an integer, default value is 0 if not declared, primitive type because of lower-case letter
		// String text = null; // non primitive type because of capital letter, String is a class, does not allocate memory for the string but allocates memory for the reference to a string (address of some memory), default value for references is null.
	}
}
