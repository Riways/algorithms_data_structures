package data_structures.trees;

import java.util.Arrays;

public class HeapMax<T extends Comparable<T>> {

	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_RESET = "\u001B[0m";

	private static final String ANSI_YELLOW = "\u001B[33m";

	private static final int DEFAULT_HEAP_SIZE = 10;

	private static final double GROW_RATE = 1.5;

	int heapSize;
	T[] heap;

	@SuppressWarnings("unchecked")
	public HeapMax() {
		heap = (T[]) new Object[DEFAULT_HEAP_SIZE];
		heapSize = 0;
	}

	public HeapMax(T[] arr) {
		heap = arr;
		heapSize = arr.length;
		buildMaxHeap();
	}

	private int getParentIndex(int i) {
		if(i%2 == 0)
			return i/2 -1;
		return i / 2 ;
	}

	private int getLeftIndex(int i) {
		return i * 2 + 1;
	}

	private int getRightIndex(int i) {
		return i * 2 + 2;
	}

	public int size() {
		return heapSize;
	}

	/*
	 * Used to maintain heap properties Method check's node on particular index. If
	 * one of his children is larger, we swap them and call method again with old
	 * index of larger element. If there are no children with larger value, node
	 * stays on current place forming a proper heap.
	 */
	private void maxHeapify(int index) {
		int leftIndex = getLeftIndex(index);
		int rightIndex = getRightIndex(index);
		int largestIndex;
		if (leftIndex < heapSize && heap[leftIndex].compareTo(heap[index]) > 0)
			largestIndex = leftIndex;
		else
			largestIndex = index;
		if (rightIndex < heapSize && heap[rightIndex].compareTo(heap[largestIndex]) > 0)
			largestIndex = rightIndex;
		if (largestIndex != index) {
			swap(index, largestIndex);
			maxHeapify(largestIndex);
		}

	}

	private T[] buildMaxHeap() {
		int halfLength = heap.length / 2;
		for (int i = halfLength; i >= 0; i--) {
			maxHeapify(i);
		}
		return heap;
	}

	/*
	 * sorts array without additional memory On every iteration swaps largest(0
	 * index) element with last element in array. After that maxHeapifyHepler
	 * function restore proper heap structure, besides that pointer of "last"
	 * element to swap shifts left
	 */
	public void heapSort() {
		for (int i = heap.length - 1; i >= 1; i--) {
			swap(0, i);
			heapSize--;
			maxHeapify(0);
		}
	}

	public void insert(T value) {
		try {
			growIfNecessary(1);
		} catch (Exception e) {
			return;
		}
		heapSize++;
		heap[heapSize - 1] = value;
		try {
			increaseKey(heapSize - 1, value);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void growIfNecessary(int amount) throws Exception {
		int minSize = heapSize + amount;
		if (heap.length >= minSize)
			return;
		if (minSize > Integer.MAX_VALUE)
			throw new Exception("Max heap size exceeded");
		int newSize = (int) (heap.length * GROW_RATE);
		while (newSize < minSize) {
			newSize = (int) (newSize * GROW_RATE);
		}
		if (newSize > Integer.MAX_VALUE)
			newSize = Integer.MAX_VALUE;
		heap = Arrays.copyOf(heap, newSize);
	}

	public T extractMax() {
		if (heap.length < 1) {
			return null;
		}
		T max = getMax();
		heap[0] = heap[heapSize - 1];
		heap[heapSize - 1] = null;
		heapSize--;
		maxHeapify(0);
		return max;
	}

	public T getMax() {
		return heap[0];
	}

	// Replace key of element placed on specified index.
	// New key cannot be less than previous
	public void increaseKey(int index, T key) throws Exception {
		if (heap[index].compareTo(key) > 0)
			throw new Exception("New key must be greater than old one");
		heap[index] = key;
		int parentIndex = getParentIndex(index);
		while (index > 0 && heap[parentIndex].compareTo(heap[index]) < 0) {
			swap(parentIndex, index);
			index = parentIndex;
			parentIndex = getParentIndex(parentIndex);
		}
	}

	private void swap(int first, int second) {
		T temp = heap[first];
		heap[first] = heap[second];
		heap[second] = temp;
	}

	private void swapArr(T[] arr, int first, int second) {
//		System.out.println("SWAP " + arr[first] + " " + arr[second]);
		T temp = arr[first];
		arr[first] = arr[second];
		arr[second] = temp;
	}

	public boolean isTreeValid() {
		if (heapSize <= 1) {
			return true;
		}
		return isTreeValidHelper(0);
	}

	private boolean isTreeValidHelper(int index) {
		if (index >= heapSize)
			return true;
		int left = getLeftIndex(index);
		int right = getRightIndex(index);

		if (left < heapSize && heap[left].compareTo(heap[index]) > 0)
			return false;
		if (right < heapSize && heap[right].compareTo(heap[index]) > 0)
			return false;
		boolean isLeftValid = isTreeValidHelper(left);
		boolean isRightValid = isTreeValidHelper(right);
		if (!isLeftValid || !isRightValid)
			return false;
		return true;
	}

	

	private void printArr(T[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
			if (i != 0 && i % 30 == 0)
				System.out.println();
		}
		System.out.println();
	}

	public void printTree() {
		System.out.println("----------");
		printTreeUtil(heap, 0, 0);
		System.out.println("----------");
	}

	private void printTreeUtil(T[] arr, int currDepth, int index) {
		if (index >= arr.length || arr == null)
			return;
		int parentIndex = getParentIndex(index);
		int rightIndex = getRightIndex(index);
		int leftIndex = getLeftIndex(index);
		printTreeUtil(arr, currDepth + 1, rightIndex);
		for (int i = 0; i < currDepth; i++) {
			System.out.print("     ");
		}
		if (index != 0) {
			if (getLeftIndex(parentIndex) == index) {
				System.out.print(ANSI_YELLOW + "\\" + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + "/" + ANSI_RESET);
			}
		}
		System.out.print(arr[index]);
		System.out.print("(");
		if (index != 0)
			System.out.print(arr[parentIndex]);
		else
			System.out.print("root");
		System.out.println(")");
		printTreeUtil(arr, currDepth + 1, leftIndex);
	}

	private int getBadNodeIndex() {
		return getBadNodeIndexHelper(0);
	}

	private int getBadNodeIndexHelper(int index) {
		if (index >= heapSize)
			return -1;
		int left = getLeftIndex(index);
		int right = getRightIndex(index);

		if (left < heapSize && heap[left].compareTo(heap[index]) > 0)
			return index;
		if (right < heapSize && heap[right].compareTo(heap[index]) > 0)
			return index;
		int leftResult = getBadNodeIndexHelper(left);
		int rightResult = getBadNodeIndexHelper(right);
		if(leftResult!= -1)
			return leftResult;
		if(rightResult != -1)
			return rightResult;
		return -1;
	}
	
	public void printTreeWithBadIndex() {
		System.out.println("----------");
		int badIndex = getBadNodeIndex();
		printTreeWithBadIndexHelper(heap, 0, 0, badIndex);
		System.out.println("----------");
	}

	private void printTreeWithBadIndexHelper(T[] arr, int currDepth, int index, int badIndex) {
		if (index >= arr.length || arr == null)
			return;
		int parentIndex = getParentIndex(index);
		int rightIndex = getRightIndex(index);
		int leftIndex = getLeftIndex(index);
		printTreeWithBadIndexHelper(arr, currDepth + 1, rightIndex, badIndex);
		for (int i = 0; i < currDepth; i++) {
			System.out.print("     ");
		}
		if (index != 0) {
			if (getLeftIndex(parentIndex) == index) {
				System.out.print(ANSI_YELLOW + "\\" + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + "/" + ANSI_RESET);
			}
		}
		if (index != badIndex)
			System.out.print(arr[index]);
		else
			System.out.print(ANSI_RED + arr[index] + ANSI_RESET);
		System.out.print("(");
		if (index != 0)
			System.out.print(arr[parentIndex]);
		else
			System.out.print("root");
		System.out.println(")");
		printTreeWithBadIndexHelper(arr, currDepth + 1, leftIndex,badIndex);
	}

}
