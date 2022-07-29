package data_structures.trees;

public class BlackRedTree<T extends Comparable<T>> {

	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_YELLOW = "\u001B[33m";

	private Node root;
	private Node nil;

	private int size;

	public BlackRedTree() {
		size = 0;
		nil = new Node();
		root = nil;
	}

	public int maxDepth() {
		int depth = 1;

		while (1 << depth < size) {
			depth++;
		}
		return depth;
	}

	public Node search(T value) {
		return searchFoo(value, root);
	}
	private boolean isNil(Node node) {
		return node == nil;
	}

	private Node searchFoo(T value, Node node) {
		if (node == nil)
			return nil;
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

	public T minimum() {
		return treeMinimum(root).key;
	}

	public T maximum() {
		return treeMaximum(root).key;
	}

	public void inorderTreeWalk(Node node) {
		if (node == nil)
			return;
		inorderTreeWalk(node.left);
		inorderTreeWalk(node.right);
	}

	private Node treeMinimum(Node node) {
		while (node.left != nil)
			node = node.left;
		return node;
	}

	private Node treeMaximum(Node node) {
		while (node.right != nil)
			node = node.right;
		return node;
	}

	public void delete(T key) {
		Node nodeToDelete = search(key);
		if (nodeToDelete == nil)
			return;
		boolean isNodeWasRed = nodeToDelete.isRed;
		Node x = nil;
		// right child only or no children
		if (nodeToDelete.left == nil) {
			x = nodeToDelete.right;
			replaceNode(nodeToDelete, nodeToDelete.right);
			size--;
		} else
		// left child only
		if (nodeToDelete.right == nil) {
			x = nodeToDelete.left;
			replaceNode(nodeToDelete, nodeToDelete.left);
			size--;
		} else
		// left and right children
		{
			Node successor = treeMinimum(nodeToDelete.right);
			isNodeWasRed = successor.isRed;
			x = successor.right;
			if (x == nil) {
//				x =
			}
			if (successor.parent != nodeToDelete) {
				replaceNode(successor, successor.right);
				successor.right = nodeToDelete.right;
				successor.right.parent = successor;
			}
			replaceNode(nodeToDelete, successor);
			successor.left = nodeToDelete.left;
			nodeToDelete.left.parent = successor;
			successor.isRed = nodeToDelete.isRed;
			printTree();
			size--;
		}
		if (!isNodeWasRed) {
			repaintTreeAterDelete(x);
		}
	}

	public String isTreeValid() {
		int depth = treeValidationHelper(root, 0);
		if (depth < 0)
			return ANSI_RED + "BAD" + ANSI_RESET;
		else
			return ANSI_GREEN + "GOOD" + ANSI_RESET;
	}

	private int treeValidationHelper(Node node, int blackDepth) {
		if (node == nil) {
			return blackDepth;
		}
		if (!node.isRed) {
			blackDepth++;
		}
		if (isNodeHasTwoChilds(node)) {
			int checkLeftDepth = treeValidationHelper(node.left, blackDepth);
			int checkRightDepth = treeValidationHelper(node.right, blackDepth);
			if (checkLeftDepth < 0 | checkRightDepth < 0 | checkRightDepth != checkLeftDepth) {
				System.out.println("BAD NODE: " + node.key + " DEPTH: " + blackDepth);
				return -1;
			} else
				return checkLeftDepth;
		} else if (node.left != nil) {
			blackDepth = treeValidationHelper(node.left, blackDepth);
		} else if (node.right != nil) {
			blackDepth = treeValidationHelper(node.right, blackDepth);
		}
		return blackDepth;
	}

	private boolean isNodeHasTwoChilds(Node node) {
		return node.left != nil && node.right != nil;
	}

//	private void prepareNil(Node node) {
//		nil.parent = node.parent;
//		nil.isRed = node.isRed;
//		if (node.parent != nil) {
//			if (node.parent.right == node)
//				node.parent.right = nil;
//			else
//				node.parent.left = nil;
//		}
//	}
//
//	private void resetNil() {
//		nil.right = nil;
//		nil.left = nil;
//		nil.isRed = false;
//		if (nil.parent != nil) {
//			if (nil.parent.right == nil)
//				nil.parent.right = nil;
//			else
//				nil.parent.left = nil;
//		}
//		nil.parent = nil;
//	}

	private void repaintTreeAterDelete(Node node) {
		if (node == nil) {
			System.out.println("PAINTING TREE: nil NODE");
			return;
		}
		System.out.println("PAINTING TREE: " + (node == nil ? "nil" : node.key));
		printTree();
		// pop additional black color up
		while (node != root && !node.isRed) {
			if (node == node.parent.left) {
				Node parent = node.parent;
				Node sibling = parent.right;
				Node newphew = parent.right.right;
				Node niece = parent.right.left;
				if (sibling != nil && sibling.isRed) {
					System.out.println("CASE 1 (SIBLING IS RED) IN LEFT TREE: " + node.key);
					printTree();
					sibling.isRed = false;
					parent.isRed = true;
					leftRotate(parent);
					System.out.println("RESULT: ");
					printTree();
				} else if (newphew.isRed) {
					System.out.println("CASE 2 IN LEFT TREE: " + node.key);
					printTree();
					sibling.isRed = parent.isRed;
					parent.isRed = false;
					newphew.isRed = false;
					leftRotate(parent);
					System.out.println("RESULT: ");
					printTree();
					return;
				} else if (niece != nil && niece.isRed) {
					niece.isRed = false;
					sibling.isRed = true;
					rightRotate(sibling);
				} else {
					sibling.isRed = true;
					node = parent;
				}
			} else {
				Node parent = node.parent;
				Node sibling = parent.left;
				Node newphew = parent.left.left;
				Node niece = parent.left.left;
				if (sibling != nil && sibling.isRed) {
					System.out.println("CASE 1 (SIBLING IS RED) IN left TREE: " + node.key);
					printTree();
					sibling.isRed = false;
					parent.isRed = true;
					leftRotate(parent);
					System.out.println("RESULT: ");
					printTree();
				} else if (newphew.isRed) {
					System.out.println("CASE 2 IN left TREE: " + node.key);
					printTree();
					sibling.isRed = parent.isRed;
					parent.isRed = false;
					newphew.isRed = false;
					leftRotate(parent);
					System.out.println("RESULT: ");
					printTree();
					return;
				} else if (niece != nil && niece.isRed) {
					niece.isRed = false;
					sibling.isRed = true;
					leftRotate(sibling);
				} else {
					sibling.isRed = true;
					node = parent;
				}
			}
		}
		node.isRed = false;
	}

	private boolean isSiblingHasTwoBlackChildren(Node sib) {

		if (sib.left == nil && sib.right == nil)
			return true;
		if (sib.left == nil) {
			if (!sib.right.isRed)
				return true;
		} else {
			if (!sib.left.isRed)
				return true;
		}
		return false;
	}
//	private void repaintTreeAterDelete(Node node) {
//		System.out.println("PAINTING TREE: " + node.key);
//		printTree();
//		// pop additional black color up
//		while (node != root && !node.isRed) {
//			if (node == node.parent.left) {
//				Node sibling = node.parent.right;
//				if (sibling != nil && sibling.isRed) {
//					System.out.println("CASE 1 (SIBLING IS RED) IN LEFT TREE: " + node.key);
//					printTree();
//					sibling.isRed = false;
//					node.parent.isRed = true;
//					leftRotate(node.parent);
//					sibling = node.parent.right;
//					System.out.println("RESULT: ");
//					printTree();
//				}
//				if (sibling.left != nil && sibling.right != nil && !sibling.left.isRed && !sibling.right.isRed) {
//					System.out.println("CASE 2 IN LEFT TREE: " + node.key);
//					printTree();
//					sibling.isRed = true;
//					node = node.parent;
//					System.out.println("RESULT: ");
//					printTree();
//				} else {
//					if (!sibling.right.isRed) {
//						System.out.println("CASE 3 IN LEFT TREE: " + node.key);
//						printTree();
//						sibling.left.isRed = false;
//						sibling.isRed = true;
//						rightRotate(sibling);
//						sibling = node.parent.right;
//						System.out.println("RESULT: ");
//						printTree();
//					}
//					System.out.println("CASE 4 IN LEFT TREE: " + node.key);
//					printTree();
//					sibling.isRed = node.parent.isRed;
//					node.parent.isRed = false;
//					sibling.right.isRed = false;
//					node = root;
//					System.out.println("RESULT: ");
//					printTree();
//				}
//			} else {
//				Node sibling = node.parent.left;
//				if (sibling != nil && sibling.isRed) {
//					System.out.println("CASE 1 (SIBLING IS RED) IN RIGHT TREE: " + node.key);
//					printTree();
//					sibling.isRed = false;
//					node.parent.isRed = true;
//					rightRotate(node.parent);
//					sibling = node.parent.left;
//					System.out.println("RESULT: ");
//					printTree();
//				}
//				if ((sibling.left == nil && sibling.right == nil) | !sibling.right.isRed && !sibling.left.isRed) {
//					System.out.println("CASE 2 IN RIGHT TREE: " + node.key);
//					printTree();
//					sibling.isRed = true;
//					node = node.parent;
//					System.out.println("RESULT: ");
//					printTree();
//				} else {
//					if (!sibling.left.isRed) {
//						System.out.println("CASE 3 IN RIGHT TREE: " + node.key);
//						printTree();
//						sibling.right.isRed = false;
//						sibling.isRed = true;
//						leftRotate(sibling);
//						sibling = node.parent.left;
//						System.out.println("RESULT: ");
//						printTree();
//					}
//					System.out.println("CASE 4 IN RIGHT TREE: " + node.key);
//					printTree();
//					sibling.isRed = node.parent.isRed;
//					node.parent.isRed = false;
//					sibling.left.isRed = false;
//					node = root;
//					System.out.println("RESULT: ");
//					printTree();
//				}
//			}
//		}
//		node.isRed = false;
//	}

	public int size() {
		return size;
	}

	private void replaceNode(Node replaceableNode, Node replacementNode) {
		if (replaceableNode.parent == nil) {
			root = replacementNode;
		} else {
			if (replaceableNode.parent.left == replaceableNode) {
				replaceableNode.parent.left = replacementNode;
			} else {
				replaceableNode.parent.right = replacementNode;
			}
		}
		if (replacementNode != nil)
			replacementNode.parent = replaceableNode.parent;
	}

	private Node treeSuccessor(Node node) {
		if (node.right != nil)
			return treeMinimum(node.right);
		Node nodeParent = node.parent;
		while (nodeParent != nil && node == nodeParent.right) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent;
	}

	public T treeValueSuccessor(T value) {
		Node node = search(value);
		if (node.right != nil)
			return treeMinimum(node.right).key;
		Node nodeParent = node.parent;
		while (nodeParent.parent != nil && node == nodeParent.right) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent.key;
	}

	private Node treePredcessor(Node node) {
		if (node.left != nil)
			return treeMaximum(node.left);
		Node nodeParent = node.parent;
		while (nodeParent != nil && node == nodeParent.left) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent;
	}

	public T treeValuePredcessor(T value) {
		Node node = search(value);
		if (node.left != nil)
			return treeMaximum(node.left).key;
		Node nodeParent = node.parent;
		while (nodeParent.parent != nil && node == nodeParent.left) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent.key;
	}

	private void insertNode(Node nodeToInsert) {
		if (root == nil) {
			root = nodeToInsert;
			size++;
			return;
		}
		Node previous = nil;
		T key = nodeToInsert.key;
		Node insertionPlace = root;
		while (insertionPlace != nil) {
			previous = insertionPlace;
			if (key.equals(insertionPlace.key))
				return;
			if (key.compareTo(insertionPlace.key) < 0)
				insertionPlace = insertionPlace.left;
			else
				insertionPlace = insertionPlace.right;
		}
		insertionPlace = nodeToInsert;
		nodeToInsert.parent = previous;
		if (previous == nil)
			root = nodeToInsert;
		if (previous.key.compareTo(nodeToInsert.key) < 0)
			previous.right = nodeToInsert;
		else
			previous.left = nodeToInsert;
		nodeToInsert.isRed = true;
		repaintTreeAfterModify(nodeToInsert);
		size++;
	}

	// in every case node is a leaf
	private void repaintTreeAfterModify(Node node) {
		// System.out.println("TREE STATE BEFORE MANIPULATIONS: ");
		// printTree();
		while (node.parent != nil && node.parent.isRed) {
			if (node.parent.parent.left == node.parent) {
				Node uncle = node.parent.parent.right;
				if (uncle != nil && uncle.isRed) {
					// System.out.println("FIRST CASE IN LEFT BRANCH:" + node.key);
					// printTree();
					node.parent.isRed = false;
					uncle.isRed = false;
					node = node.parent.parent;
					node.isRed = true;

				} else {
					if (node.parent != nil && node.parent.right == node) {

						// node is closer to middle of tree
						// System.out.println("SECOND CASE IN LEFT BRANCH:" + node.key);
						// printTree();
						node = node.parent;
						leftRotate(node);

					}
					// node is closer to outer edge of tree
					// System.out.println("THIRD CASE IN LEFT BRANCH:" + node.key);
					// printTree();
					node.parent.isRed = false;
					node.parent.parent.isRed = true;

					rightRotate(node.parent.parent);

				}
			} else {
				Node uncle = node.parent.parent.left;
				if (uncle != nil && uncle.isRed) {
					// System.out.println("FIRST CASE IN RIGHT BRANCH: " + node.key);
					// printTree();
					node.parent.isRed = false;
					uncle.isRed = false;
					node = node.parent.parent;
					node.isRed = true;

				} else {
					if (node.parent.left == node) {
						// node is closer to middle of tree
						// System.out.println("SECOND CASE IN RIGHT BRANCH: " + node.key);
						// printTree();
						node = node.parent;
						rightRotate(node);
					}
					// node is closer to outer edge of tree
					// System.out.println("THIRD CASE IN RIGHT BRANCH: " + node.key);
					// printTree();
					node.parent.isRed = false;
					node.parent.parent.isRed = true;
					leftRotate(node.parent.parent);
				}
			}
		}
		root.isRed = false;
	}

	// put node below
	private void leftRotate(Node node) {
//		System.out.println("LEFT ROTATE: " + node.key);
		Node nodeThatGoesUp = node.right;
		node.right = nodeThatGoesUp.left;

		if (nodeThatGoesUp.left != nil)
			nodeThatGoesUp.left.parent = node;
		nodeThatGoesUp.parent = node.parent;

		if (node.parent == nil) {
			root = nodeThatGoesUp;
		} else {
			if (node == node.parent.right) {
				node.parent.right = nodeThatGoesUp;
			} else {
				node.parent.left = nodeThatGoesUp;
			}
		}

		nodeThatGoesUp.left = node;
		node.parent = nodeThatGoesUp;
//		printTree();

	}

	// put node below
	private void rightRotate(Node node) {
//		System.out.println("RIGHT ROTATION: " + node.key);
		Node nodeThatGoesUp = node.left;
		node.left = nodeThatGoesUp.right;

		if (nodeThatGoesUp.right != nil)
			nodeThatGoesUp.right.parent = node;
		nodeThatGoesUp.parent = node.parent;
		if (node.parent == nil) {
			root = nodeThatGoesUp;
		} else {
			if (node.parent.left == node) {
				node.parent.left = nodeThatGoesUp;
			} else {
				node.parent.right = nodeThatGoesUp;
			}
		}

		nodeThatGoesUp.right = node;
		node.parent = nodeThatGoesUp;
//		printTree();
	}

	public void printTree() {
		System.out.println("--------------");
		printTreeUtil(root, 0);
		System.out.println("--------------");
	}

	public void printTreeUtil(Node node, int currDepth) {

		if (node == nil)
			return;
		printTreeUtil(node.right, currDepth + 1);
		for (int i = 0; i < currDepth; i++) {
			System.out.print("     ");
		}
		if (node.parent != nil) {
			if (node.parent.left == node) {
				System.out.print(ANSI_YELLOW + "\\" + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + "/" + ANSI_RESET);
			}
		}
		if (node.isRed)
			System.out.print(ANSI_RED + node.key + ANSI_RESET);
		else
			System.out.print(node.key);
		System.out.print("(");
		if (node.parent != nil)
			if (node.parent.isRed)
				System.out.print(ANSI_RED + node.parent.key + ANSI_RESET);
			else
				System.out.print(node.parent.key);
		else
			System.out.print("root");
//		if (node.left != nil)
//			System.out.print(", left: " + node.left.key);
//		if (node.right != nil)
//			System.out.print(", right: " + node.right.key);
		System.out.println(")");
		printTreeUtil(node.left, currDepth + 1);
	}

	private class Node {

		T key;
		Node left;
		Node right;
		Node parent;
		boolean isRed;

		public Node(T key) {
			this.key = key;
		}

		public Node() {
			super();
		}

	}
}
