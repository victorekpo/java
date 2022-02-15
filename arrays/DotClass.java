public class DotClass {

	public static void main (String[] args) {

		System.out.println(addInt(1,2,3,4,5));
		System.out.println("Test");
	}

	public static int addInt(int ...val) {
		int num = 0;
		for(int i: val) {
			num+=i;
		}
		return num;

	}



}

