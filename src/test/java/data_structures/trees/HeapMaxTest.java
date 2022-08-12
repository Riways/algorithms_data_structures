package data_structures.trees;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class HeapMaxTest {

	HeapMax<Integer> heap;

	private Integer[] genereateArrWithHundredElements() {
		Integer[] arr = new Integer[100];
		for (int i = 0; i < 100; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0, 100);
		}
		return arr;
	}

	private Integer[] genereateArrWithMillionElements() {
		Integer[] arr = new Integer[1000000];
		for (int i = 0; i < 1000000; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0, 1000000);
		}
		return arr;
	}

	private Integer[] genereateArrWithTenElements() {
		Integer[] arr = new Integer[10];
		for (int i = 0; i < 10; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0, 10);
		}
		return arr;
	}

	@Test
	void buildMaxHeapTest() {
		Integer[] arr = genereateArrWithTenElements();
		heap = new HeapMax<Integer>(arr);
		assertEquals(true, heap.isTreeValid());
	}

	@Test
	void extractMaxTest() {
		Integer[] arr = genereateArrWithTenElements();
		heap = new HeapMax<Integer>(arr);
		for (int i = 0; i < arr.length; i++) {
			int max = heap.extractMax();
			if (!heap.isTreeValid())
				fail();
//			printArr(arr);
		}
	}

	@Test
	void increaseKeyTest() {
		Integer[] arr = genereateArrWithTenElements();
		heap = new HeapMax<Integer>(arr);
		int index = ThreadLocalRandom.current().nextInt(0, 9);
		try {
			heap.increaseKey(index, 101);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(heap.getMax(), 101);
		assertEquals(heap.size(), 10);
	}

	@Test
	void heapSortTest() {
		Integer[] arr = genereateArrWithMillionElements();
		heap = new HeapMax<>(arr);
//		double start = System.currentTimeMillis();
		heap.heapSort();
//		double end = System.currentTimeMillis();
//		System.out.println((end- start) / 1000 + " seconds ");
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < arr[i - 1]) {
				printArr(heap.heap);
				heap.printTreeWithBadIndex();
				fail();
			}
		}
		assertEquals(arr.length, 1000000);
	}

	@Test
	void insertTest() {
		Integer[] arr = genereateArrWithTenElements();
		heap = new HeapMax<>(arr);
		for (int i = 0; i < 15; i++) {
			int value = ThreadLocalRandom.current().nextInt(0, 30);
			heap.insert(value);
			if (!heap.isTreeValid()) {
				System.out.println("BAD! " + value);
				printArr(heap.heap);
				heap.printTreeWithBadIndex();
				fail();
			}
		}
	}

	private void printArr(Integer[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
			if (i != 0 && i % 30 == 0)
				System.out.println();
		}
		System.out.println();
	}
}
