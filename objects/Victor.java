class Person {
	private String name;
	private int age;
	private String type;
	public static String description = "This is a person class, describing a person with name, age, and type = human";

	public Person() {
		type = "human";
		System.out.println(description);
		System.out.println("Constructor running! "+ type);
	}
	
	public Person(String name) { 
		this(); // Call default constructor
		System.out.println("Second constructor running!");
	}

	public Person(String name, int age) {
		this(); // Call default constructor
		System.out.println("Third constructor running!");
	}

	public void speak() {
		System.out.println("Yo");
	}
	
	public void speak(String name) {  //overloaded methods have the same name
		this.name = name;
		System.out.println("My name is "+name);
	}
}

public class Victor {
	public static void main(String[] args) {
		Person vic1 = new Person();
		Person vic2 = new Person("Vic");
		Person vic3 = new Person ("Vic", 77);
		vic3.speak();
		vic3.speak("Victor");
	}
}

