package org.teknixco.learning;

class Frog {
	private String name;
	private int age;

	public void setName(String name) {
		this.name = name; // without using the this prefix, the method will refer to the closest variable.
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

}

public class SetterMethodsThisKeyword {
	public static void main(String[] args) {
		Frog frog1 = new Frog();
		// frog1.name = "Bertie";
		// frog1.age = 1;
		// accessing the variables directly, undesirable and can complicate things since
		// you have to know the internal variables of a class.
		// By using set methods, all you have to know is the methods of a class.
		frog1.setName("Bertie");
		frog1.setAge(1);
		System.out.println(frog1.getName());
		System.out.println(frog1.getAge());
	}
}
