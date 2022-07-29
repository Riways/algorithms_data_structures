package data_structures.trees;

import java.util.Arrays;

//a-z and '
public class Trie {

	private Node root;

	public Trie() {
		 root = new Node();
	}

	public void addWord(String word) {
		if (!isWordValid(word))
			return;

		char[] chars = word.toCharArray();
		Node node = root;
		for (int i = 0; i < chars.length; i++) {
			char currChar = chars[i];
			Node nextNode = node.getNextNodeIfPresentByChar(currChar);
			if (nextNode == null) {
				nextNode = node.addChild(currChar);
			}
			node = nextNode;
			if (i == chars.length - 1)
				node.markAsFullWord();
		}
	}

	private boolean isWordValid(String word) {
		char[] chars = word.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char chrctr = chars[i];
			if (!(chrctr >= 'a' && chrctr <= 'z')) {
				if (chrctr != '\'' && chrctr != '*')
					return false;
			}
		}
		return true;

	}

	public boolean isWordPresent(String word) {
		if (!isWordValid(word))
			return false;
		char[] chars = word.toCharArray();
		Node nextNode = root;
		for (int i = 0; i < chars.length; i++) {
			char currChar = chars[i];
			nextNode = nextNode.getNextNodeIfPresentByChar(currChar);
			if (nextNode == null)
				return false;
			if (i == chars.length - 1 && !nextNode.isWord())
				return false;
		}
		return true;
	}

	private class Node {
		char character;
		Node[] children;

		private int size;
		private final static int INIT_CAPACITY = 2;
		private final static double EXPANSION_RATE = 1.5;
		// a-z and ' and *;
		private final static int MAX_CAPACITY = 28;

		public Node() {
			children = new Node[INIT_CAPACITY];
			size = 0;
		}

		public Node(char character) {
			this.character = character;
			children = new Node[INIT_CAPACITY];
			size = 0;
		}
		

		public Node getNextNodeIfPresentByChar(char chrctr) {
			for (int i = 0; i < children.length; i++) {
				if(children[i] == null)
					return null;
				char ch = children[i].character;
				if (ch == chrctr)
					return children[i];
			}
			return null;
		}

		public boolean isWord() {
			for (int i = 0; i < children.length; i++) {
				if (children[i] == null)
					return false;
				if (children[i].character == '*')
					return true;
			}
			return false;
		}

		public Node addChild(char chrctr) {
			growIfNecessary(1);
			Node newNode = new Node(chrctr);
			for (int i = 0; i < children.length; i++) {
				if (children[i] == null) {
					children[i] = newNode;
					size++;
					return newNode;
				}
			}
			return null;
		}
		
		

		public void markAsFullWord() {
			growIfNecessary(1);
			addChild('*');
		}

		private void growIfNecessary(int elementsToInsert) {
			int freeSpace = children.length - size;
			if (freeSpace >= elementsToInsert)
				return;
			int newSize = calculateNextCapacity(elementsToInsert);
			children = Arrays.copyOf(children, newSize);
		}

		private int calculateNextCapacity(int elementsToInsert) {
			int minimalNeededCapacity = size + elementsToInsert;
			int nextCapacity = children.length;
			while (nextCapacity <= minimalNeededCapacity) {
				nextCapacity *= EXPANSION_RATE;
			}
			if (nextCapacity > MAX_CAPACITY)
				return MAX_CAPACITY;
			return nextCapacity;
		}

	}

}
