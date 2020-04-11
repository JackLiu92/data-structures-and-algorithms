package com.jk.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {

	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	public RBTree() {
		this(null);
	}

	public RBTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	private Node<E> color(Node<E> node, boolean color) {
		if (node == null) return node;
		((RBNode<E>)node).color = color;
		return node;
	}
	
	@Override
	protected void afterAdd(Node<E> node) {
		Node<E> parent = node.parent;
		
		// 根节点
		if (parent == null) {
			black(node);
			return;
		}
		
		// 添加的父节点是黑色的
		if (isBlack(node)) return;
		
		// 叔父节点
		Node<E> uncle = parent.sibling();
		// 叔父节点为红色, 上溢
		Node<E> grand = red(parent.parent);
		if (isRed(uncle)) {
			black(uncle);
			black(parent);
			afterAdd(grand);
			return;
		}
		
		if (parent.isLeftChild()) {
			if (node.isLeftChild()) {
				black(parent);
			} else {
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else {
			if (node.isRightChild()) {
				black(parent);
			} else {
				black(node);
				rotateRight(parent);
			}
			rotateLeft(grand);
		}
		
	}
	
	@Override
	protected void afterRemove(Node<E> node) {
		// 删除的事红色节点 或者替代节点是红色
		if (isRed(node)) {
			black(node);
			return;
		}
		
		Node<E> parent = node.parent;
		if (parent == null) return;
		
		boolean left = parent.left == null || node.isLeftChild();
		Node<E> sibling = left ? parent.right : parent.left;
		if (left) {
			if (isRed(sibling)) {
				black(sibling);
				red(parent);
				rotateLeft(parent);
				sibling = parent.right;
			}
			
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else {
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}
				
				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);
			}
		} else {
			if (isRed(sibling)) {
				black(sibling);
				red(parent);
				rotateRight(parent);
				sibling = parent.left;
			}
			
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else {
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left;
				}
				
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
		}
		
	}
	
	private Node<E> red(Node<E> node) {
		return color(node, RED);
	}
	
	private Node<E> black(Node<E> node) {
		return color(node, BLACK);
	}
	
	private boolean colorOf(Node<E> node) {
		return node == null ? BLACK : ((RBNode<E>)node).color;
	}
	
	private boolean isBlack(Node<E> node) {
		return colorOf(node) == BLACK;
	}
	
	private boolean isRed(Node<E> node) {
		return colorOf(node) == RED;
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new RBNode<E>(element, parent);
	}
	
	private static class RBNode<E> extends Node<E> {

		boolean color = RED;
		
		public RBNode(E element, Node<E> parent) {
			super(element, parent);
		}
		
		@Override
		public String toString() {
			String string = "";
			if (color == RED) {
				string += "R_";
			}
			return string + element.toString();
		}
	}
}
