class Person {
	public Person() {
		System.out.println("Constructor running!");
	}
	
	public Person(String name) {
		System.out.println("Second constructor running!");
	}

	public Person(String name, int age) {
		System.out.println("Third constructor runnign!");
	}
}

public class Victor {
	public static void main(String[] args) {
		Person vic1 = new Person();
		Person vic2 = new Person("Vic");
		Person vic3 = new Person ("Vic", 34);
	}
}

