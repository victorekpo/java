import org.teknixco.vic.Engineer;
import org.teknixco.vic.Person; //just because the previous class extends Person doesn't mean this class can access it.

public class App {
	public static void main(String[] args) {
		Engineer Victor = new Engineer();
		Person Someone = new Person();
		Victor.speak();
		Someone.speak();
		Victor.id = 12;
		System.out.println(Victor.id);
		System.out.println(Someone.id);
	}
	
}
