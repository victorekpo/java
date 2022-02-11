package org.learning.inheritance;

public class Person {
	protected int age = 0;
	protected String firstname = "firstname";
	protected String lastname = "lastname";
	protected String gender = "MALE";
	protected int progress = 100;
	
	public Person() {}
	
	public void move() {
		System.out.println("Moved");
	}
	
	public class Adult {
		public Adult() {}
	}

	public class Baby {
		public Baby() {}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person person = new Person();
		System.out.println("an adult " + (person instanceof Object));
	}
}
