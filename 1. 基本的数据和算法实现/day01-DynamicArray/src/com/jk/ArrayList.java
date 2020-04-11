package com.jk;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory.Zephyr;

@SuppressWarnings("unchecked")
public class ArrayList <E> {
	
	public static final int Default_capacity = 10;
	public static final int Not_Found = -1;
	private int size;
	private E[] elements;
	
	public ArrayList() {
		this(Default_capacity);
	}
	
	public ArrayList(int capacity) {
		capacity = (capacity < Default_capacity ? Default_capacity : capacity);
		elements = (E[]) new Object[capacity];
	}
	
	public void clear() {
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public Boolean isEmpty() {
		return size == 0;
	}
	
	public boolean contains(E element) {
		return indexOf(element) == Not_Found;
	}
	
	public int indexOf(E element) {
		if (element == null) {
			for (int i = 0; i < size; i ++) {
				if (null == elements[i]) {
					return i;
				}
			} 
		} else {
			for (int i = 0; i < size; i++) {
				if (elements[i].equals(element)) {
					return i;
				}
			}
		}
		
		return Not_Found;
	}
	
	public void add(E element) {
		add(element, size);
	}
	
	public void add(E element, int index) {
		rangeCheckForAdd(index);
		ensureCapacity();
		
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		elements[index] = element;
		size++;
		
	}
	
	public E set(E element, int index) {
		rangeCheck(index);
		E old = elements[index];
		elements[index] = element;
		return old;
	}
	
	public E get(int index) {
		rangeCheck(index);
		return elements[index];
	}
	
	public E remove(int index) {
		rangeCheck(index);
		E old = elements[index];
		for (int i = index; i < size - 1; i++) {
			elements[index] = elements[index + 1];
		}
		elements[--size] = null;
		return old;
	}
	
	private void ensureCapacity() {
		if (size == elements.length) {
			E[] newElements = (E[]) new Object[size + size >> 1];
			for (int i = 0; i < size; i++) {
				newElements[i] = elements[i];
			}
			elements = newElements;
		}
	}
	
	private void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("index:" + index + " size:" + size);
		}
	}
	
	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index:" + index + " size:" + size);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("size = ").append(size).append("[");
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				sb.append(" ");
				sb.append(elements[i]);
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	
}
