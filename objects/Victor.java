class Person {
	private String name;
	private int age;
	private String type;
	public static String description = "This is a person class, describing a person with name, age, and type = human";
	public static final int luckyNumber = 7;
	public static int count = 0;
	public int id;

	public Person() {
		count++;
		id=count;
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
		System.out.println(Person.count);
		System.out.println(vic1.id);
		System.out.println(vic2.id);
		System.out.println(vic3.id);
	}
}

