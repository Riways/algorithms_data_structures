package data_structures.trees;

import java.util.Iterator;

public class BinarySearchTree<T extends Comparable<T>> {
	private Node root;

	private int size;

	public BinarySearchTree() {
		size = 0;
	}

	public Node search(T value) {
		return searchFoo(value, root);
	}

	private Node searchFoo(T value, Node node) {
		if (node == null)
			return null;
		if (node.key.equals(value))
			return node;
		if (node.key.compareTo(value) < 0)
			return searchFoo(value, node.right);
		else
			return searchFoo(value, node.left);
	}

	public void insertValue(T value) {
		Node nodeToInsert = new Node(value);
		insertNode(nodeToInsert);
	}

	private void insertNode(Node nodeToInsert) {
		if (root == null) {
			root = nodeToInsert;
			size++;
			return;
		}
		Node previous = null;
		T key = nodeToInsert.key;
		Node x = root;
		while (x != null) {
			previous = x;
			if (key.equals(x.key))
				return;
			if (key.compareTo(x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		x = nodeToInsert;
		x.parent = previous;
		if (previous.key.compareTo(x.key) < 0)
			previous.right = x;
		else
			previous.left = x;
		size++;
	}

	public T minimum() {
		return treeMinimum(root).key;
	}

	public T maximum() {
		return treeMaximum(root).key;
	}

	public void inorderTreeWalk(Node node) {
		if (node == null)
			return;
		inorderTreeWalk(node.left);
		inorderTreeWalk(node.right);
	}

	private Node treeMinimum(Node node) {
		while (node.left != null)
			node = node.left;
		return node;
	}

	private Node treeMaximum(Node node) {
		while (node.right != null)
			node = node.right;
		return node;
	}

	public void delete(T key) {
		Node nodeToDelete = search(key);
		if (nodeToDelete == null)
			return;
		// no children
		if (nodeToDelete.left == null && nodeToDelete.right == null) {
			if (nodeToDelete.key.equals(root.key)) {
				root = null;
				size--;
				return;
			}
			if (nodeToDelete.parent != null) {
				if (nodeToDelete.parent.left == nodeToDelete)
					nodeToDelete.parent.left = null;
				else {
					nodeToDelete.parent.right = null;
				}
			}
			size--;
			return;
		}

		// right child only
		if (nodeToDelete.left == null) {
			replaceNode(nodeToDelete, nodeToDelete.right);
			size--;
			return;
		}

		// left child only
		if (nodeToDelete.right == null) {
			replaceNode(nodeToDelete, nodeToDelete.left);
			size--;
			return;
		}

		// left and right children
		Node successor = treeMinimum(nodeToDelete.right);
		replaceNode(nodeToDelete, successor);
		size--;
	}

	public int size() {
		return size;
	}

	private void replaceNode(Node replaceableNode, Node replacementNode) {
		if (replaceableNode.parent == null) {
			root = replacementNode;
			replacementNode.parent = null;
		} else {
			if (replaceableNode.parent.left == replaceableNode) {
				replaceableNode.parent.left = replacementNode;
			} else {
				replaceableNode.parent.right = replacementNode;
			}
			replacementNode.parent = replaceableNode.parent;
		}
	}

	private Node treeSuccessor(Node node) {
		if (node.right != null)
			return treeMinimum(node.right);
		Node nodeParent = node.parent;
		while (nodeParent != null && node == nodeParent.right) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent;
	}

	public T treeValueSuccessor(T value) {
		Node node = search(value);
		if (node.right != null)
			return treeMinimum(node.right).key;
		Node nodeParent = node.parent;
		while (nodeParent != null && node == nodeParent.right) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent.key;
	}

	private Node treePredcessor(Node node) {
		if (node.left != null)
			return treeMaximum(node.left);
		Node nodeParent = node.parent;
		while (nodeParent != null && node == nodeParent.left) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent;
	}

	public T treeValuePredcessor(T value) {
		Node node = search(value);
		if (node.left != null)
			return treeMaximum(node.left).key;
		Node nodeParent = node.parent;
		while (nodeParent != null && node == nodeParent.left) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent.key;
	}

	public void printTree() {
		printTreeUtil(root, 0);
	}

	public void printTreeUtil(Node node, int currDepth) {
		if (node == null)
			return;

		printTreeUtil(node.left, currDepth + 1);
		for (int i = 0; i < currDepth; i++) {
			System.out.print("|");
		}
		System.out.print(node.key + "(");
		if (node.parent != null)
			System.out.print(node.parent.key);
		else
			System.out.print("root");
		System.out.println(")");
		printTreeUtil(node.right, currDepth + 1);
	}

	private class Node {

		T key;
		Node left;
		Node right;
		Node parent;

		public Node(T key) {
			this.key = key;
		}

	}

}