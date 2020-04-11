package com.jk.sort;

public class InsertSort<E extends Comparable<E>> {
	private E[] array;
	
	public void sort(E[] arr) {
		this.array = arr;
		for (int begain = 1; begain < arr.length; begain++) {
			E tmp = arr[begain];
			int dest = insertPosition(begain);
			for (int end = begain - 1; end >= dest; end--) {
				arr[end + 1] = arr[end]; 
			}
			arr[dest] = tmp;
		}
	}
	
	private int insertPosition(int index) {
		int begain = 0;
		int end = index;
		while (begain < end) {
			int middle = (begain + end) >> 1;
			if (array[index].compareTo(array[middle]) < 0) {
				end = middle;
			} else {
				begain = middle + 1;
			}
		}
		return begain;
	}
}
