package com.jk.sort;

public class CountigSort {
	public void sort(Integer[] array) {
		int max = array[0];
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
			}
			if (min > array[i]) {
				min = array[i];
			}
		}
		
		int[] counts = new int[max - min];
		for (int i = 0; i < array.length; i++) {
			counts[array[i] - min]++;
		}
		for (int i = 1; i < array.length; i++) {
			counts[i] += counts[i - 1];
		}
		
		int[] newArray = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--) {
			newArray[--counts[array[i] - min]] = array[i];
		}
		
		for (int i = 0; i < newArray.length; i++) {
			array[i] = newArray[i];
		}
	}
}
