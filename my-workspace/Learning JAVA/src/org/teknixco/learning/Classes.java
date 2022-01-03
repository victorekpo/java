package org.teknixco.learning;

class Person {
	//Instance variables (data or "state")
	String name;
	int age;
	
	
	// Notes: Class name must match file name.
	// A class is a template for creating an object. It doesn't represent an actual object but just the template.
	// 1. Classes can contain data, which represent the state of your object. i.e. name, address, mood, heart-rate, etc, how you are at the moment, represented in the form of variables.
	// 2. Subroutines, which are methods, are commands that are executed 	
}

public class Classes {
	public static void main(String[] args) {
		Person person1 = new Person(); // creating an object of type "Person", instantiation
		person1.name = "Victor E.";
		person1.age = 34;
		Person person2 = new Person(); // the keyword "new" sets the type "Person" for person2 to a new Person object.
		person2.name = "you";
		person2.age = 25;
	}

}
