package com.jk;

public class main {

	public main(String[] args) {
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		intArray.add(5);
		intArray.add(5);
		intArray.set(6, 2);
		intArray.remove(2);
		System.out.println(intArray);
	}

}
