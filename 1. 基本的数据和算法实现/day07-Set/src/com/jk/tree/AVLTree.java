package com.jk.tree;

import java.util.Comparator;

public class AVLTree<E> extends BBST<E> {
		public AVLTree() {
			this(null);
		}
		
		public AVLTree(Comparator<E> comparator) {
			super(comparator);
		}
		
		@Override
		protected void afterAdd(Node<E> node) {
			while ((node = node.parent) != null) {
				if (isBalance(node)) {
					updateHeight(node);
				} else {
					rebalance(node);
					break;
				}
			}
		}
		
		@Override
		protected void afterRemove(Node<E> node) {
			while ((node = node.parent) != null) {
				if (isBalance(node)) {
					updateHeight(node);
				} else {
					rebalance(node);
				}
			}
		}
		
		private boolean isBalance(Node<E> node) {
			return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
		}
		
		private void updateHeight(Node<E> node) {
			((AVLNode<E>)node).updateHeight();
		}
		
		private void rebalance(Node<E> grand) {
			Node<E> parent = ((AVLNode<E>)grand).tallerChild();
			Node<E> node = ((AVLNode<E>)grand).tallerChild();
			
			if (parent.isLeftChild()) {
				if (node.isLeftChild()) {
					rotate(grand, node, node.right, parent, parent.right, grand);
				} else {
					rotate(grand, parent, node.left, node, node.right, grand);
				}
			} else {
				if (node.isRightChild()) {
					rotate(grand, grand, parent.left, parent, node.left, node);
				} else {
					rotate(grand, grand, node.left, node, node.right, parent);
				}
			}
		}
		
		@Override
		protected void rotate(Node<E> root, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
			super.rotate(root, b, c, d, e, f);
			
			updateHeight(b);
			updateHeight(f);
			updateHeight(d);
		}
		
		private static class AVLNode<E> extends Node<E> {
			int height = 1;
			
			public AVLNode(E element, Node<E> parent) {
				super(element, parent);
			}
			
			public int balanceFactor() {
				int leftH = left == null ? 0 : ((AVLNode<E>)left).height;
				int rightH = right == null ? 0 : ((AVLNode<E>)right).height;
				
				return leftH - rightH;
			}
			
			public void updateHeight() {
				int leftH = left == null ? 0 : ((AVLNode<E>)left).height;
				int rightH = right == null ? 0 : ((AVLNode<E>)right).height;
				
				height = Math.max(leftH, rightH) + 1;
			}
			
			public Node<E> tallerChild() {
				int leftH = left == null ? 0 : ((AVLNode<E>)left).height;
				int rightH = right == null ? 0 : ((AVLNode<E>)right).height;
				
				if (leftH < rightH) return right;
				if (leftH > rightH) return left;
				
				return isLeftChild() ? left : right;
			}
			
			@Override
			public String toString() {
				String parentStr = "null";
				if (parent != null) {
					parentStr = parent.element.toString();
				}
				return element.toString() + "_p(" + parentStr + ")_h(" + height + ")";
			}
			
		}
}
