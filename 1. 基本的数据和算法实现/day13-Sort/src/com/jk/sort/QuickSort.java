package com.jk.sort;

public class QuickSort<E extends Comparable<E>> {
	private E[] array;
	
	public void sort(E[] arr) {
		array = arr;
		subSort(0, array.length);
	}
	
	private void subSort(int begain, int end) {
		if (end - begain < 2) return;
		int middle = pivotIndex(begain, end);
		subSort(begain, middle);
		subSort(middle + 1, end);
	}
	
	private int pivotIndex(int begain, int end) {
		int randomIndex = begain + (int)(Math.random() * (end - begain));
		E tmp = array[begain];
		array[randomIndex] = array[begain];
		array[begain] = tmp;
		
		E pivot = array[begain];
		end--;
		while (begain < end) {
			while (begain < end) {
				if (array[end].compareTo(pivot) > 0) {
					end--;
				} else {
					array[begain++] = array[end];
					break;
				}
			}
			
			while (begain < end) {
				if (array[begain].compareTo(pivot) < 0) {
					begain++;
				} else {
					array[end--] = array[begain];
					break;
				}
			}
		}
		
		array[begain] = pivot;
		return begain;
	}
}
