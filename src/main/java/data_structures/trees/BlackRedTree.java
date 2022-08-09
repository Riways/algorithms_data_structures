package data_structures.trees;

public class BlackRedTree<T extends Comparable<T>> {

	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_YELLOW = "\u001B[33m";

	private Node root;

	private int size;

	public BlackRedTree() {
		size = 0;
		root = null;
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

	public boolean isTreeValid() {
		int depth = treeValidationHelper(root, 0);
		if (depth < 0 || (root != null && root.isRed))
			return false;
		else
			return true;
	}

	private int treeValidationHelper(Node node, int blackDepth) {
		if (node == null) {
			return ++blackDepth;
		}
		if (!node.isRed) {
			blackDepth++;
		}
		if (isNodeHasTwoChilds(node)) {
			if (node.isRed && (node.right.isRed || node.left.isRed)) {
				// System.out.println("BAD NODE: " + node.key + " DEPTH: " + blackDepth);
				return -1;
			}
			int checkLeftDepth = treeValidationHelper(node.left, blackDepth);
			int checkRightDepth = treeValidationHelper(node.right, blackDepth);
			if (checkLeftDepth < 0 | checkRightDepth < 0 | checkRightDepth != checkLeftDepth) {
				// System.out.println("BAD NODE: " + node.key + " DEPTH: " + blackDepth);
				return -1;
			} else
				return checkLeftDepth;
		} else if (node.left != null) {
			if (node.isRed && node.left.isRed) {
				// System.out.println("BAD NODE: " + node.key + " DEPTH: " + blackDepth);
				return -1;
			}
			int prevDepth = blackDepth;
			blackDepth = treeValidationHelper(node.left, blackDepth);
			if (blackDepth != prevDepth + 1) {
				// System.out.println("BAD NODE: " + node.key + " DEPTH: " + blackDepth);
				return -1;
			}
		} else if (node.right != null) {
			if (node.isRed && node.right.isRed) {
				// System.out.println("BAD NODE: " + node.key + " DEPTH: " + blackDepth);
				return -1;
			}
			int prevDepth = blackDepth;
			blackDepth = treeValidationHelper(node.right, blackDepth);
			if (blackDepth != prevDepth + 1) {
				// System.out.println("BAD NODE: " + node.key + " DEPTH: " + blackDepth);
				return -1;
			}
		} else {
			return ++blackDepth;
		}
		return blackDepth;
	}

	private boolean isNodeHasTwoChilds(Node node) {
		return node.left != null && node.right != null;
	}

	private boolean isNodeHasTwoBlackChildren(Node node) {
		if (isNodeHasTwoChilds(node)) {
			return !node.left.isRed && !node.right.isRed;
		} else {
			return node.left == null && node.right == null;
		}
	}

	private boolean nodeHaveNoChildren(Node node) {
		return node.left == null && node.right == null;
	}

	/*
	 * When we delete node we can encounter with three cases: 1. Node have no
	 * children 1.1 If node is red we simply replace it with null 1.2 If node is
	 * black we have to repaint tree because the depth of tree changed(most
	 * complicated case) 2. Node have one child In this case Node always will be
	 * black with red child, and according to black-depth rule red child can't have
	 * children. We have to copy value from red node into black and replace red node
	 * with null; 3. Node have two children Goal of this case is to convert current
	 * layout to one of two previous case's
	 */
	public void delete(T key) {
		Node nodeToDelete = search(key);
		if (nodeToDelete == null)
			return;
		boolean isNodeWasRed = false;
		Node x = null;
		boolean isSimpleCase = false;

		while (!isSimpleCase) {
			isNodeWasRed = nodeToDelete.isRed;
			// no children
			// first or second case
			if (nodeToDelete.left == null && nodeToDelete.right == null) {
				x = nodeToDelete;
				if (!isNodeWasRed) {
					repaintTreeAfterDelete(x);
				}
				replaceNode(nodeToDelete, null);
				size--;
				isSimpleCase = true;
			} else
			// third case
			// right child only
			// only black nodes can have one child, and it always will be red
			if (nodeToDelete.left == null) {
				x = nodeToDelete.right;
				swapValues(nodeToDelete, x);
				replaceNode(x, null);
				isSimpleCase = true;
				size--;
			} else
			// third case
			// left child only
			// only black nodes can have one child, and it always will be red
			if (nodeToDelete.right == null) {
				x = nodeToDelete.left;
				swapValues(nodeToDelete, x);
				replaceNode(x, null);
				isSimpleCase = true;
				size--;
			} else
			// left and right children
			// converts to one of previous cases
			{
				Node successor = treeMinimum(nodeToDelete.right);
				swapValues(nodeToDelete, successor);
				nodeToDelete = successor;
			}
		}

	}

	private void repaintTreeAfterDelete(Node node) {
		if (node == root) {
			return;
		}
//		printTree();
		// pop additional black color up
		if (node == node.parent.left) {
			leftNodeRepaint(node);
		} else {
			rightNodeRepaint(node);
		}
		root.isRed = false;
	}

	private void leftNodeRepaint(Node node) {
		// System.out.println("leftNodeRepaint");
		Node parent = node.parent;
		Node sibling = parent.right;
		Node leftNewphew = null;
		Node rightNewphew = null;
		if (sibling != null) {
			leftNewphew = sibling.left;
			rightNewphew = sibling.right;
		}
		if (parent.isRed) {
			if (sibling == null || (sibling != null && isNodeHasTwoBlackChildren(sibling)))
			// RB1
			{
				if (sibling != null)
					sibling.isRed = true;
				parent.isRed = false;
			} else { // RB2
				if (rightNewphew != null && rightNewphew.isRed) {
					// System.out.println("RB2.1 left");
					parent.isRed = false;
					rightNewphew.isRed = false;
					sibling.isRed = true;
					leftRotate(parent);
				} else if (leftNewphew != null && leftNewphew.isRed) {
					// System.out.println("RB2.2 left");
					parent.isRed = false;
					rightRotate(sibling);
					leftRotate(parent);
				}
			}
		} else {
			if (sibling != null && sibling.isRed) {
				// BR3
				if (isNodeHasTwoBlackChildren(leftNewphew)) {
					// System.out.println("BR3 left");
					sibling.isRed = false;
					leftNewphew.isRed = true;
					leftRotate(parent);
				} else {
					// BR4
					if (leftNewphew.right != null && leftNewphew.right.isRed) {
						// System.out.println("BR4.1 left");
						leftNewphew.right.isRed = false;
						rightRotate(sibling);
						leftRotate(parent);
					} else if (leftNewphew.left != null && leftNewphew.left.isRed) {
						// System.out.println("BR4.2 left");
						leftNewphew.left.isRed = false;
						rightRotate(leftNewphew);
						rightRotate(sibling);
						leftRotate(parent);
					}
				}
			} else {
				// BB6
				if (sibling == null || (sibling != null && isNodeHasTwoBlackChildren(sibling))) {
					sibling.isRed = true;
					repaintTreeAfterDelete(parent);
				} // BB5
				else {
					if (leftNewphew != null && leftNewphew.isRed) {
						leftNewphew.isRed = false;
						rightRotate(sibling);
						leftRotate(parent);
					} else if (rightNewphew != null && rightNewphew.isRed) {
						leftRotate(parent);
						rightNewphew.isRed = false;
					}
				}
			}
		}
	}

	private void rightNodeRepaint(Node node) {
		// System.out.println("rightNodeRepaint");
		Node parent = node.parent;
		Node sibling = parent.left;
		Node rightNewphew = null;
		Node leftNewphew = null;
		if (sibling != null) {
			rightNewphew = sibling.right;
			leftNewphew = sibling.left;
		}
		if (parent.isRed) {

			if (sibling == null || (sibling != null && isNodeHasTwoBlackChildren(sibling)))
			// RB1 COMPLETED
			{
				// System.out.println("RB1 right");
				if (sibling != null)
					sibling.isRed = true;
				parent.isRed = false;
			} else { // RB2
				if (leftNewphew != null && leftNewphew.isRed) {
					// System.out.println("RB2.1 right");
					parent.isRed = false;
					leftNewphew.isRed = false;
					sibling.isRed = true;
					rightRotate(parent);
				} else if (rightNewphew != null && rightNewphew.isRed) {
					// System.out.println("RB2.2 right");
					parent.isRed = false;
					leftRotate(sibling);
					rightRotate(parent);
				}
			}
		} else {
			if (sibling != null && sibling.isRed) {
				// BR3
				if (isNodeHasTwoBlackChildren(rightNewphew)) {
					sibling.isRed = false;
					rightNewphew.isRed = true;
					rightRotate(parent);
				} else {
					// BR4
					if (rightNewphew.left != null && rightNewphew.left.isRed) {
						// System.out.println("BR4.1 right");
						rightNewphew.left.isRed = false;
						leftRotate(sibling);
						rightRotate(parent);
					} else if (rightNewphew.right != null && rightNewphew.right.isRed) {
						// System.out.println("BR4.2 right");
						rightNewphew.right.isRed = false;
						leftRotate(rightNewphew);
						leftRotate(sibling);
						rightRotate(parent);
					}
				}
			} else {
				// BB6
				if (sibling == null || (sibling != null && isNodeHasTwoBlackChildren(sibling))) {
					sibling.isRed = true;
					repaintTreeAfterDelete(parent);
				} // BB5
				else {
					if (rightNewphew != null && rightNewphew.isRed) {
						rightNewphew.isRed = false;
						leftRotate(sibling);
						rightRotate(parent);
					} else if (leftNewphew != null && leftNewphew.isRed) {
						rightRotate(parent);
						leftNewphew.isRed = false;
					}
				}
			}
		}
	}

	public int size() {
		return size;
	}

	private void replaceNode(Node replaceableNode, Node replacementNode) {
		if (replaceableNode.parent == null) {
			root = replacementNode;
		} else {
			Node parentOfReplaceableNode = replaceableNode.parent;
			if (parentOfReplaceableNode.left == replaceableNode) {
				parentOfReplaceableNode.left = replacementNode;
			} else {
				parentOfReplaceableNode.right = replacementNode;
			}
		}
		if (replacementNode != null)
			replacementNode.parent = replaceableNode.parent;
	}

	private void swapValues(Node first, Node second) {
		// System.out.println("SWAPPING " + first.key + " and " + second.key);
		T temp = first.key;
		first.key = second.key;
		second.key = temp;
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
		while (nodeParent.parent != null && node == nodeParent.right) {
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
		while (nodeParent.parent != null && node == nodeParent.left) {
			node = nodeParent;
			nodeParent = nodeParent.parent;
		}
		return nodeParent.key;
	}

	private void insertNode(Node nodeToInsert) {
		if (root == null) {
			root = nodeToInsert;
			size++;
			return;
		}
		Node previous = null;
		T key = nodeToInsert.key;
		Node insertionPlace = root;
		while (insertionPlace != null) {
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
		if (previous == null)
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
		while (node.parent != null && node.parent.isRed) {
			if (node.parent.parent.left == node.parent) {
				Node uncle = node.parent.parent.right;
				if (uncle != null && uncle.isRed) {
					node.parent.isRed = false;
					uncle.isRed = false;
					node = node.parent.parent;
					node.isRed = true;
				} else {
					if (node.parent != null && node.parent.right == node) {
						// node is closer to middle of tree
						node = node.parent;
						leftRotate(node);
					}
					// node is closer to outer edge of tree
					node.parent.isRed = false;
					node.parent.parent.isRed = true;
					rightRotate(node.parent.parent);
				}
			} else {
				Node uncle = node.parent.parent.left;
				if (uncle != null && uncle.isRed) {
					node.parent.isRed = false;
					uncle.isRed = false;
					node = node.parent.parent;
					node.isRed = true;
				} else {
					if (node.parent.left == node) {
						// node is closer to middle of tree
						node = node.parent;
						rightRotate(node);
					}
					// node is closer to outer edge of tree
					node.parent.isRed = false;
					node.parent.parent.isRed = true;
					leftRotate(node.parent.parent);
				}
			}
		}
		root.isRed = false;
	}

	private void leftRotate(Node node) {
		Node nodeThatGoesUp = node.right;
		node.right = nodeThatGoesUp.left;

		if (nodeThatGoesUp.left != null)
			nodeThatGoesUp.left.parent = node;
		nodeThatGoesUp.parent = node.parent;
		if (node.parent == null) {
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
	}

	private void rightRotate(Node node) {
		Node nodeThatGoesUp = node.left;
		node.left = nodeThatGoesUp.right;

		if (nodeThatGoesUp.right != null)
			nodeThatGoesUp.right.parent = node;
		nodeThatGoesUp.parent = node.parent;
		if (node.parent == null) {
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
	}

	public void printTree() {
		System.out.println("--------------");
		printTreeUtil(root, 0);
		System.out.println("--------------");
	}

	public void printTreeUtil(Node node, int currDepth) {

		if (node == null)
			return;
		printTreeUtil(node.right, currDepth + 1);
		for (int i = 0; i < currDepth; i++) {
			System.out.print("     ");
		}
		if (node.parent != null) {
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
		if (node.parent != null)
			if (node.parent.isRed)
				System.out.print(ANSI_RED + node.parent.key + ANSI_RESET);
			else
				System.out.print(node.parent.key);
		else
			System.out.print("root");
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

	}
}
