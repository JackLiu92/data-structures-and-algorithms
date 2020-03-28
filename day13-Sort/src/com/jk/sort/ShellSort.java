package com.jk.sort;

import java.util.LinkedList;
import java.util.List;

public class ShellSort<E extends Comparable<E>> {
	private E[] array;
	
	public void sort(E[] arr) {
		array = arr;
		for (int step: sedgewickStepSequence()) {
			subSort(step);
		}
	}
	
	private void subSort(int step) {
		for (int col = 0; col < step; col++ ) {
			for (int row = col + step; row < array.length; row += step) {
				int current = row;
				while (current > col && array[current].compareTo(array[current - step]) < 0) {
					E tmp = array[current];
					array[current] = array[current - step];
					array[current - step] = tmp;
					current -= step;
				}
			}
		}
	}
	
	private List<Integer> sedgewickStepSequence() {
		List<Integer> stepSequence = new LinkedList<>();
		int k = 0, step = 0;
		while (true) {
			if (k % 2 == 0) {
				int pow = (int) Math.pow(2, k >> 1);
				step = 1 + 9 * (pow * pow - pow);
			} else {
				int pow1 = (int) Math.pow(2, (k - 1) >> 1);
				int pow2 = (int) Math.pow(2, (k + 1) >> 1);
				step = 1 + 8 * pow1 * pow2 - 6 * pow2;
			}
			if (step >= array.length) break;
			stepSequence.add(0, step);
			k++;
		}
		return stepSequence;
	}
}
