package org.teknixco.learning;

class PersonWithMethods {
	// Instance variables (data or "state")
	String name;
	int age;

	// methods should always start with a lowercase letter, or use camelCase.
	// Constructor methods use the same name as the class.
	void speak() {
		System.out.println("Hello, my name is " +name+ ", I am " + age + " years old.");
	}

	// Notes: Class name must match file name.
	// A class is a template for creating an object. It doesn't represent an actual
	// object but just the template.
	// 1. Classes can contain data, which represent the state of your object. i.e.
	// name, address, mood, heart-rate, etc, how you are at the moment, represented
	// in the form of variables.
	// 2. Subroutines, which are methods, are commands that are executed
}

public class Methods {
	public static void main(String[] args) {
		PersonWithMethods person1 = new PersonWithMethods(); // creating an object of type "Person", instantiation
		person1.name = "Victor E.";
		person1.age = 34;
		PersonWithMethods person2 = new PersonWithMethods(); // the keyword "new" sets the type "Person" for person2 to
																// a new Person object.
		person2.name = "Someone Special";
		person2.age = 25;
		person1.speak();
		person2.speak();
	}

}
