package com.jk.set;

import com.jk.list.LinkList;
import com.jk.list.List;

public class ListSet<E> implements Set<E> {

	private List<E> List = new LinkList<>();
	
	@Override
	public int size() {
		return List.size();
	}

	@Override
	public boolean isEmpty() {
		return List.isEmpty();
	}

	@Override
	public void clear() {
		List.clear();
		
	}

	@Override
	public boolean contains(E element) {
		return List.contains(element);
	}

	@Override
	public void add(E element) {
		int index = List.indexOf(element);
		if (index == com.jk.list.List.ELEMENT_NOT_FOUND) {
			List.add(element);
		} else {
			List.set(index, element);
		}
		
	}

	@Override
	public void remove(E element) {
		int index = List.indexOf(element);
		if (index != com.jk.list.List.ELEMENT_NOT_FOUND) {
			List.remove(index);
		}
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		if (visitor == null) return;
		
		for (int i = 0; i < List.size(); i++) {
			if (visitor.visit(List.get(i))) return;
		}
	}
	
}
