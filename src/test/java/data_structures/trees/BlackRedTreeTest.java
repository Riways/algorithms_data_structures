package data_structures.trees;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class BlackRedTreeTest {

	BlackRedTree<Integer> tree;

	private void initEmptyTree() {
		tree = new BlackRedTree<>();
	}

	private void initTreeWithHundredElements() {
		tree = new BlackRedTree<>();
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		arrList.forEach(num -> {
			tree.insertValue(num);
		});

	}
	
	private ArrayList<Integer> initArrayWithTwentyElementsInRandomArrange() {
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		return arrList;
	}
	private void initTreeWithThousandElements() {
		tree = new BlackRedTree<>();
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		arrList.forEach(num -> {
			tree.insertValue(num);
		});

	}
	private void initTreeWithFiftyThousandElements() {
		tree = new BlackRedTree<>();
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 50000; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		arrList.forEach(num -> {
			tree.insertValue(num);
		});
		
	}

	private void initTreeWithTenElements() {
		tree = new BlackRedTree<>();
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		arrList.forEach(num -> {
			tree.insertValue(num);
		});
		
	}
	private void initTreeWithTwentyElements() {
		tree = new BlackRedTree<>();
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			arrList.add(i);
		}
		Collections.shuffle(arrList);
		arrList.forEach(num -> {
			tree.insertValue(num);
		});
		
	}

	@Test
	void testInsertAndDelete() {
		initTreeWithTwentyElements();
		ArrayList<Integer> arr = initArrayWithTwentyElementsInRandomArrange();
		for (int i = 0; i < 20; i++) {
			System.out.println("DELETING: " + arr.get(i));
			System.out.println("BEFORE MANIPULATION: ");
			tree.printTree();
			
			tree.delete(arr.get(i));
			System.out.println("RESULT: " + tree.isTreeValid());
			tree.printTree();
		}
		assertEquals(tree.size(), 0);
	}

	@Test
	void testPredcessorAndSuccessor() {
		initTreeWithThousandElements();
		assertEquals(2, tree.treeValuePredcessor(3));
		assertEquals(4, tree.treeValueSuccessor(3));
	}
	@Test
	void testMinimumAndMaximim() {
		initTreeWithThousandElements();
		assertEquals(tree.minimum(), 0);
		assertEquals(tree.maximum(), 999);
	}
//	@Test
//	void testPrintTree() {
//		initTreeWithFiftyThousandElements();
//		tree.printTree();
//	}



}
