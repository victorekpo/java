package org.teknixco.learning;

public class WhileLoops {
	public static void main(String[] args) {
		boolean loop = 4 < 5; //small number goes first with less than usage, and vice veras
		boolean loop2 = 6 < 1;
		int value = 10;
		boolean loop3 = value < 20;
		int value2 = 0;
		while(value2 < 10) { //as long as the condition is true, the loop will run
			System.out.println("Hello baby");
			value2++;  // increments value2 until it gets to 10, then the loop exits.
		}
		
		System.out.println(loop);
		System.out.println(loop2);
		System.out.println(loop3);
		
	}
}
