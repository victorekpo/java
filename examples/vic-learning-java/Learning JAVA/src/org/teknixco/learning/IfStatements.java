package org.teknixco.learning;

public class IfStatements {
	public static void main(String[] args) {
		boolean cond = 5 != 5; // false
		boolean cond2 = 4 == 3; // are they the same value? // fale
		boolean cond3 = 4 == 4; // true
		
		int myInt = 50;
		
		String trueStatement = "Yes it's true!";
		String falseStatement = "No, it's false!";
		
		if(4 == 4) {
			System.out.println(trueStatement);
		}
		
		
		if(myInt < 30) {
			System.out.println(trueStatement);
		}
		else { // only use else if you have binary conditions, that is only 2 possibilities, otherwise use elseif, switch statements, objects, etc.
			System.out.println(falseStatement);
		}
	}
}
