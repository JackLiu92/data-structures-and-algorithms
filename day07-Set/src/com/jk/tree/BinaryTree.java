package com.jk.tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E> {
	
	protected static class Node<E>{
		E element;
		Node<E> parent;
		Node<E> left;
		Node<E> right;
		
		public Node(E element, Node<E> parent) {
			this.element = element;
			this.parent = parent;
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
		
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}
		
		public boolean isRightChild() {
			return parent != null && this == parent.right;
		}
		
		public Node<E> sibling() {
			if (isLeftChild()) {
				return parent.right;
			}
			
			if (isRightChild()) {
				return parent.left;
			}
			
			return null;
		}
	}
	
	public static  abstract class Visitor<E> {
		boolean stop;
		public abstract boolean visit(E element);
	}
	
	protected int size;
	protected Node<E> root;
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		size = 0;
		root = null;
	}
	
	public void preorder(Visitor<E> visitor) {
		if (visitor == null) { return; }
		preorder(root, visitor);
	}
	
	private void preorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) { return; }
		
		visitor.stop = visitor.visit(node.element);
		preorder(node.left, visitor);
		preorder(node.right, visitor);
	}
	
	public void inorder(Visitor<E> visitor) {
		inorder(root, visitor);;
	}
	
	private void inorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) { return; }
		
		preorder(node.left, visitor);
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
		preorder(node.right, visitor);
	}
	
	public void postorder(Visitor<E> visitor) {
		if (visitor == null) return;
		postorder(root, visitor);
	}
	
	private void postorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) { return; }
		
		preorder(node.left, visitor);
		preorder(node.right, visitor);
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
	}
	
	public void levelOrder(Visitor<E> vistor) {
		if (root == null || vistor == null) return;
		
		Queue<Node<E>> queue = new LinkedList<Node<E>>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			boolean stop = vistor.visit(node.element);
			if (stop) return;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
	}
	
	public boolean isComplete() {
		if (root == null) return false;
		Queue<Node<E>> queue = new LinkedList<Node<E>>();
		queue.offer(root);
		
		boolean leaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			
			if (leaf && !node.isLeaf()) return false;
			
			if (node.left != null) {
				queue.offer(node.left);
			} else if (node.right != null) {
				return false;
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			} else {
				leaf = true;
			}
		}
		
		return true;
	}
	
	public int height() {
		if (root == null) return 0;
		Queue<Node<E>> queue = new LinkedList<Node<E>>();
		queue.offer(root);
		
		int height = 0;
		int levelSize = 1;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			levelSize--;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			} 
			
			if (levelSize == 0) {
				height++;
				levelSize = queue.size();
			}
		}
		
		return height;
	}
	
	protected Node<E> precessor(Node<E> node) {
		Node<E> n = node.left;
		if (n != null) {
			while (n.right != null) {
				n = n.right;
			}
			return n;
		}
		
		while (n.parent != null && n == n.parent.left) {
			n = n.parent;
		}
		
		return n.parent;
	}
	
	protected Node<E> successor(Node<E> node) {
		Node<E> n = node.right;
		if (n != null) {
			while (n.left != null) {
				n = n.left;
			}
			return n;
		}
		
		while (n.parent != null && n == n.parent.right) {
			n = n.parent;
		}
		
		return n.parent;
	}
	
	protected Node<E> createNode(E element, Node<E> parent) {
		return new Node<E>(element, parent);
	}
	
}
