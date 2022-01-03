package org.teknixco.learning;

// Static member variables belong to the class, and copies are not made. 
// Static methods are accessed by the class name. Static methods cannot refer to non-static (instance) variables/methods.
// But instance methods (non-static methods) can access static data. 
// A good use of static is to set class constants such as Math.PI or to count the number of objects.

class Thing {
	public String name;
	public static String description;
	public static final int LUCKY_NUMBER = 7; // class constant
	public static int count = 0;
	
	public Thing() {
		count++; // constructor increments count every time an instance is created.
	}
	
	public void getName() {
		System.out.println(description + ": " + name);
	}

	public static void showInfo() {
		System.out.println("Hello");
	}

}

public class StaticVariablesMethods {
	public static void main(String[] args) {
		Thing thing1 = new Thing();
		Thing thing2 = new Thing();

		Thing.description = "I am a thing";
		Thing.showInfo();
		System.out.println(Thing.description);
		System.out.println("Current count of Thing objects are: " + Thing.count);

		thing1.name = "Bob";
		thing2.name = "Sue";
		thing1.getName();
		thing2.getName();
		
		System.out.println(Math.PI); //  an example of a static constant
		System.out.println(Thing.LUCKY_NUMBER);

	}
}
