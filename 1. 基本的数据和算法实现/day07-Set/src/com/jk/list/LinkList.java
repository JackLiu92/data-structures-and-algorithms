package com.jk.list;

public class LinkList<E> extends AbstractList<E> {
	private Node<E> first;
	private Node<E> last;
	
	private static class Node<E> {
		E element;
		Node<E> prev;
		Node<E> next;
		
	    @SuppressWarnings("unused")
		public Node(Node<E> prev, E element, Node<E> next) {
			this.prev = prev;
			this.next = next;
			this.element = element;
		}
	    
	    @Override
	    public String toString() {
	    	StringBuilder sb = new StringBuilder();
	    	
	    	if (prev != null) {
	    		sb.append(prev.element);
	    	} else {
	    		sb.append("null");
	    	}
	    	
	    	sb.append(element);
	    	
	    	if (next != null) {
	    		sb.append(next.element);
	    	} else {
	    		sb.append("null");
		}
	    	
	    	return sb.toString();
	    }
	}

	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	@Override
	public E get(int index) {
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E old = node.element;
		node.element = element;
		return old;
	}

	@Override
	public void add(int index, E element) {
		if (index == size) {
			Node<E> oldLast = last;
			last = new Node<E>(oldLast, element, null);
			if (oldLast == null) {
				first = last;
			} else {
				oldLast.next = last;
			}
		} else {
			Node<E> next = node(index);
			Node<E> prev = next.prev;
			Node<E> node = new Node<E>(prev, element, next);
			next.prev = node;
			if (prev == null) {
				first = node;
			} else {
				prev.next = node;
			}
		}
		size++;
	}

	@Override
	public E remove(int index) {
		rangeCheck(index);
		
		Node<E> node = node(index);
		Node<E> prev = node.prev;
		Node<E> next = node.next;
		
		if (prev == null) {
			first = next;
		} else {
			prev.next = next;
		}
		
		if (next == null) {
			last = prev;
		} else {
			next.prev = prev;
		}
		size--;
		
		return node.element;
	}

	@Override
	public int indexOf(E element) {
		Node<E> node = first;
		for (int i = 0; i < size; i++) {
			if (element == null) {
				if (node.element == null) return i;
			} else {
				if (element.equals(node.element)) return i;
			}
			node = node.next;
		}
		return ELEMENT_NOT_FOUND;
	}
	
	Node<E> node(int index) {
		rangeCheck(index);
		if (index < size >> 1) {
			Node<E> node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		} else {
			Node<E> node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.prev;
			}
			return node;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("size = " + size);
		Node<E> node = first;
		for (int i = 0; i < size; i++) {
			if (i != 0) sb.append(" ,");
			sb.append(node.element);
		}
		sb.append("]");
		return sb.toString();
	}

}
