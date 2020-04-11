package com.jk.tree;

import java.util.Comparator;

public class BinarySearchTree<E> extends BinaryTree<E> {
	
	private Comparator<E> comparator;
	
	public BinarySearchTree() {
		this(null);
	}
	
	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	public void add(E element) {
		checkElement(element);
		
		if (root == null) {
			root = createNode(element, null);
			size++;
			
			afterAdd(root);
			
			return;
		}
		
		Node<E> parent = root;
		Node<E> node = root;
		int cmp = 0;
		while (node != null) {
			cmp = compare(element, node.element);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else {
				 node.element = element;
				 return;
			}
		}
		
		Node<E> newNode = createNode(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		
		size++;
		
		afterAdd(newNode);
		
	}
	
	protected void afterAdd(Node<E> node) {
		
	}
	
	public boolean contains(E element) {
		return node(element) != null;
	}
	
	public void remove(E element) {
		checkElement(element);
		
		remove(node(element));
	}
	
	private void remove(Node<E> node) {
		if (node == null) return;
		
		if (node.hasTwoChildren()) {
			Node<E> delete = successor(node);
			node.element = delete.element;
			node = delete;
		}
		size--;
		
		Node<E> replacement = node.left != null ? node.left : node.right;
		if (replacement != null) {
			replacement.parent = node.parent;
			if (node.parent == null) {
				root = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else {
				node.parent.right = replacement;
			}
			afterRemove(replacement);
		} else if (node.parent == null) {
			root = null;
			afterRemove(node);
		} else {
			if (node.parent.left == node) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
			afterRemove(node);
		}
		
		afterRemove(node);
	}
	
	protected void afterRemove(Node<E> node) {
		
	}
	
	Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			int cmp = compare(element, node.element);
			if (cmp == 0) return node;
			if (cmp > 0) {
				node = node.right;
			} else  {
				node = node.right;
			} 
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private int compare(E element1, E element2) {
		if (this.comparator != null) {
			return this.comparator.compare(element1, element2);
		}
		
		return ((Comparable<E>)element1).compareTo(element2);
	}
	
	private void checkElement(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not to be null");
		}
	}
	
}
