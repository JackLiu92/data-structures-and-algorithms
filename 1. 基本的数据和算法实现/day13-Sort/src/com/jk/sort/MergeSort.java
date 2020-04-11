package com.jk.sort;

@SuppressWarnings("unchecked")
public class MergeSort<E extends Comparable<E>> {
	private E[] leftArray;
	private E[] array;
	
	public void sort(E[] arr) {
		array = arr;
		leftArray = (E[])new Object[array.length >> 1];
		subSort(0, array.length);
	}
	
	private void subSort(int begain, int end) {
		if (end - begain < 2) return;
		int mid = (end + begain) >> 1;
		subSort(begain, mid);
		subSort(mid, end);
		merge(begain, mid, end);
 	}
	
	private void merge(int begain, int middle, int end) {
		int leftStartIndex = 0, leftEndIndex = middle - begain;
		int rightStartIndex = middle, rightEndIndex = end;
		int mergeStartIndex = begain;
		
		for (int i = 0; i < leftEndIndex; i++) {
			leftArray[i] = array[i + begain];
		}
		
		while (leftStartIndex < rightEndIndex) {
			if (rightStartIndex < rightEndIndex && (array[rightStartIndex].compareTo(leftArray[leftStartIndex]) < 0)) {
				array[mergeStartIndex++] = array[rightStartIndex++];
			} else {
				array[mergeStartIndex++] = leftArray[leftStartIndex++];
			}
		}
	}
}
