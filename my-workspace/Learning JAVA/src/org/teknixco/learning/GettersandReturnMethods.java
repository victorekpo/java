package org.teknixco.learning;

class PersonGetters {
	String name;
	private int age;

	void speak() {
		System.out.println("Hello, my name is " + name + ", I am " + age + " years old.");
	}

	int calculateYearsToRetirment() {
		int yearsLeft = 75 - age;
		// System.out.println(yearsLeft);
		return yearsLeft;
	}

	int getAge() {
		return age;
	}

	void setAge(int age) {
		this.age = age;
	}

	// Encapsulation is important because you will need to hide certain program
	// variables for security.
	// Getter methods allow you to get values that are hidden.
	// Setter methods allow you to set values.

}

public class GettersandReturnMethods {
	public static void main(String[] args) {
		PersonGetters person1 = new PersonGetters(); // creating an object of type "Person", instantiation
		person1.name = "Victor E.";
		person1.setAge(34);
		PersonGetters person2 = new PersonGetters(); // the keyword "new" sets the type "Person" for person2 to
														// a new Person object.
		person2.name = "Someone Special";
		person2.setAge(25);
		person1.speak();
		person2.speak();
		System.out.println(person1.name + " has " + person1.calculateYearsToRetirment() + " years left to retirement");
		System.out.println(person2.name + " has " + person2.calculateYearsToRetirment() + " years left to retirement");
	}

}
