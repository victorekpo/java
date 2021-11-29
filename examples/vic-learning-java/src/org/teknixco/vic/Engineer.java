package org.teknixco.vic;

public class Engineer extends Person{
	//public int id = 8; You should only override methods, not attributes/variables.
	@Override
	public void speak() {
		System.out.println("I am an engineer");
	}

}
