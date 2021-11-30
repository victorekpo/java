package org.teknixco.learning;

import java.util.ArrayList;
import java.util.HashMap;

public class Generics {
	public static void main(String[] args) {
		
		/// OLD STYLE ///
		ArrayList list = new ArrayList();
		
		list.add("apple");
		list.add("banana");
		list.add("orange");
		
		String fruit = (String)list.get(0);
		System.out.println(fruit);
		
		
		// NEW STYLE //
		ArrayList<String> strings = new ArrayList<>(); //optional to add type for the new initialization
		strings.add("cat");
		strings.add("dog");
		strings.add("alligator");
		
		String animal = strings.get(0);
		
		System.out.println(animal);
		
		// HASHMAP // 
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(1,"victor");
		map.put(2, "is");
		map.put(3,"the best");
		
		System.out.println(map.get(1));
	}
}
