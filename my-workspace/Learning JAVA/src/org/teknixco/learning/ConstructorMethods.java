package org.teknixco.learning;

//Constructors are special methods that are run every time you create an instance of your class.
//Constructors do not have to have a return type, they have to be the same name as your class.
//Constructors are also used for initialization of instance variables.
//You can also call constructors within constructors using this() or this(parameters)
class Machine {
	private String name;
	private int code;

	public Machine() {
		System.out.println("Constructor running!");
		name = "Arnie"; // sets initial name to "Arnie"
	}

	public Machine(String name) {
		this(); // calls the first constructor
		this.name = name; // setting the name to a passed in value.
		System.out.println("Second constructor running");
	}
	
	public Machine(String name, int code) {
		this("Zuky"); // calls the second constructor
		System.out.println(this.name);
		this.name = name;
		this.code = code;
		System.out.println("Third constructor running" + name + code);
	}
}

public class ConstructorMethods {
	public static void main(String[] args) {
		Machine machine1 = new Machine();
		// new Machine(); // You can also run a constructor by just calling a new
		// instance of the class, without setting a reference to it.

		Machine machine2 = new Machine("Bertie"); // when this is run it will look for the constructor that accepts
													// these parameters.
		
		Machine machine3 = new Machine("Chalky", 7);
		
	}
}
