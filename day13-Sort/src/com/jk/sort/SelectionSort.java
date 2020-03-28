package com.jk.sort;

public class SelectionSort<E extends Comparable<E>> {
	public void  sort(E[] array) {
		for (int i = array.length - 1; i >= 1; i--) {
			int max = 0;
			for (int j = 1; j <= i; j++ ) {
				if (array[max].compareTo(array[j]) < 0) {
					max = j;
				}
			}
			E tmp = array[max];
			array[max] = array[i];
			array[i] = tmp;
		}
	}
}
