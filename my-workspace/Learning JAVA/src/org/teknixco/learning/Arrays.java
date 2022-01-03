package org.teknixco.learning;

public class Arrays {
	public static void main(String[] args) {
		//int value = 7; // create enough memory (32 bits) to hold an integer, value type, value variable
		int[] values; // holds multiple values or list of integers, int[] is a label, reference variable
		values = new int[3]; // points the reference to a new Array object with 3 members
		values[0] = 10; // if not explicitly defined, Java will give a default value of 0, unlike C++.
		System.out.println(values[0]);
		System.out.println(values[1]);
		System.out.println(values[2]);
		// System.out.println(values[3]); // This will throw an error since it is outside of the list.
		
		for(int i=0; i<values.length;i++) {
			System.out.println(values[i]);
		}
		
		int[] numbers = {5, 6, 7}; // You can declare the values upon instantiation
		
		for(int i=0; i<numbers.length; i++) {
			System.out.println(numbers[i]);
		}
	}

}
