package org.teknixco.learning;

public class MultidimensionalArrays {
	public static void main(String[] args) {
		int[] values = {3, 5, 2343}; // example of a one-dimensional array 
		System.out.println(values[2]);
		
		int[][] grid = { // multidimensional array is just an array of arrays, two dimensional
			{3,5, 2343}, // don't have to be same length // row 0
			{2,4}, // row 1
			{1,2,3,4} // row 2
		};
		System.out.println(grid[1][0]); // start with the row, then column
		
		String[][] texts = new String[2][3]; // an array of an array of strings, two dimensional
		texts[0][1] = "Hello there";
		System.out.println(texts[0][1]);
		
		for(String[] row: texts) { // type refers to each array 
			for(String item: row) {  // type refers to each string
				System.out.println(item);
			}
		}
		
		String[][] words = new String[2][]; // if you don't explicitly set each dimension, you will have to do so later
		words[0] = new String[3];
		words[0][1] = "Hi there";
		System.out.println(words[0][1]);
	}
}
