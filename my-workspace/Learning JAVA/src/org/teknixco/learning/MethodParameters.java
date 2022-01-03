package org.teknixco.learning;

class Robot {
	//parameters are variables that you pass into a method
	public void speak(String text) { 
		System.out.println("Hello");
	}
	
	public void jump(int height) {
		System.out.println("Jumping " + height);
	}
	
	public void move(String direction, double distance) {
		System.out.println("Moving " + distance + " inches in direction " + direction + ".");
	}
}


public class MethodParameters {
	public static void main(String[] args) {
		Robot vic = new Robot();
		
		vic.speak("Hi my name is Vic.");
		vic.jump(7);
		vic.move("right", 30.5);
	}
}

//Note that primitive types change containers when passed from variable to parameter, but objects only change reference pointer.
