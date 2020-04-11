package com.jk.sort;

public class BuddleSort<E extends Comparable<E>> {
	public void  sort(E[] array) {
		// 每次冒泡最后的index
		for (int i = array.length - 1; i > 0; i--) {
			int sortedIndex = 0;
			for (int j = 1; j <= i; j++) {
				if (array[j].compareTo(array[j - 1]) < 0) {
					E tmp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = tmp;
					sortedIndex = j;
				}
			}
			i = sortedIndex;
		}
	}
}
