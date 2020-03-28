package com.jk.sort;

public class RadixSort {
	public void sort(Integer[] array) {
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
			}
		}
		
		for (int divider = 1; divider <= max; divider *= 10) {
			int[] counts = new int[10];
			for (int i = 0; i < array.length; i++) {
				counts[array[i] / divider % 10]++;
			}
			for (int i = 1; i < array.length; i++) {
				counts[i] += counts[i - 1];
			}
			
			int[] newArray = new int[array.length];
			for (int i = array.length - 1; i >= 0; i--) {
				newArray[--counts[array[i] / divider % 10]] = array[i];
			}
			
			for (int i = 0; i < newArray.length; i++) {
				array[i] = newArray[i];
			}
		}
		
	}
}
