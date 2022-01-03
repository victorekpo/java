class Person {
	private String name; // private variables only accessible by this class
	private int age;
	private String type;
	public static String description = "This is a person class, describing a person with name, age, and type = human"; //public variable
	public static final int luckyNumber = 7; // constant, public number that belongs to the class
	public static int count = 0; // static variables belong to the class and can only be changed by the class, not the objects
	public int id; // non static variables belong to the objects created 

	public Person() { //public method that can be accessed outside of the class
		count++;
		id = count;
		type = "human";
		System.out.println(description);
		System.out.println("Constructor running! " + type);
	}

	public Person(String name) {
		this(); // Call default constructor first and then run this constructor
		System.out.println("Second constructor running!");
	}

	public Person(String name, int age) { // constructor called depending on arguments
		this(); // Call default constructor first and then run this constructor
		System.out.println("Third constructor running!");
	}

	public void speak() {
		System.out.println("Yo");
	}

	public void speak(String name) { // overloaded methods have the same name but depend on the arguments given
		this.name = name;
		System.out.println("My name is " + name);
	}
}

public class Victor {
	public static void main(String[] args) {
		Person vic1 = new Person();
		Person vic2 = new Person("Vic");
		Person vic3 = new Person("Vic", 77);
		vic3.speak();
		vic3.speak("Victor");
		System.out.println(Person.count);
		System.out.println(vic1.id);
		System.out.println(vic2.id);
		System.out.println(vic3.id);
	}
}
