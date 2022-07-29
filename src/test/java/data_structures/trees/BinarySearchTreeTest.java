package data_structures.trees;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

	BinarySearchTree<Integer> tree;

	private void initEmptyTree() {
		tree = new BinarySearchTree<>();
	}

	private void initTreeWithHundredElements() {
		tree = new BinarySearchTree<>();
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		arrList.forEach(num -> {
			tree.insertValue(num);
		});
	}
	
	private void initTreeWithThousendElements() {
		tree = new BinarySearchTree<>();
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		arrList.forEach(num -> {
			tree.insertValue(num);
		});
	}

	@Test
	void testInsertAndDelete() {
		initTreeWithHundredElements();
		assertEquals(tree.size(), 100);
		for (int i = 0; i < 100; i++) {
			tree.delete(i);
		}
		assertEquals(tree.size(), 0);
	}

	@Test
	void testPredcessorAndSuccessor() {
		initTreeWithHundredElements();
		assertEquals(2, tree.treeValuePredcessor(3));
		assertEquals(4, tree.treeValueSuccessor(3));
	}

	@Test
	void testMinimumAndMaximim() {
		initTreeWithHundredElements();
		assertEquals(tree.minimum(), 0);
		assertEquals(tree.maximum(), 99);
	}
	
	@Test
	void printTreeTest() {
		initTreeWithThousendElements();
		tree.printTree( );
	}

}
