package com.jk;

import java.util.Comparator;

public class BBST<E> extends BinarySearchTree<E> {
	public BBST() {
		this(null);
	}
	
	public BBST(Comparator<E> comparator) {
		super(comparator);
	}
	
	protected void rotateLeft(Node<E> grand) {
		Node<E> parent = grand.right;
		Node<E> child = parent.left;
		grand.right = child;
		parent.left = grand;
		
		afterRotate(grand, parent, child);
	}
	
	protected void rotateRight(Node<E> grand) {
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		grand.left = child;
		parent.right = grand;
		
		afterRotate(grand, parent, child);
	}
	
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		parent.parent = grand.parent;
		if (grand.isLeftChild()) {
			grand.parent.left = parent;
		} else if (grand.isRightChild()) {
			grand.parent.right = parent;
		} else {
			root = parent;
		}
		
		if (child != null) {
			child.parent = grand;
		}
		
		grand.parent = parent;
	}
	
	protected void rotate(Node<E> root, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
		d.parent = root.parent;
		if (root.isLeftChild()) {
			root.parent.left = d;
		} else if (root.isRightChild()) {
			root.parent.right = d;
		} else {
			root = d;
		}
		
		b.right = c;
		if (c != null) {
			c.parent = b;
		}
		
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		
		d.left = b;
		d.right = f;
		b.parent = d;
		f.parent = d;
	}
}
