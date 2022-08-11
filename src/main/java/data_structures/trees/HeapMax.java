package data_structures.trees;

public class HeapMax<T extends Comparable<T>> {
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	T[] nodes;

	private T getParentNode(int i) {
		return nodes[i / 2];
	}

	private T getLeftNode(int i) {
		return nodes[i * 2];
	}

	private T getRightNode(int i) {
		return nodes[i * 2 + 1];
	}

	private int getParentIndex(int i) {
		return i / 2;
	}

	private int getLeftIndex(int i) {
		return i * 2 + 1;
	}

	private int getRightIndex(int i) {
		return i * 2 + 2;
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
		if (leftIndex <= nodes.length && nodes[leftIndex].compareTo(nodes[index]) > 0)
			largestIndex = leftIndex;
		else
			largestIndex = index;
		if (rightIndex <= nodes.length && nodes[rightIndex].compareTo(nodes[largestIndex]) > 0)
			largestIndex = rightIndex;
		if (largestIndex != index) {
			swap(index, largestIndex);
			maxHeapify(largestIndex);
		}

	}

	private void maxHeapifyHelper(Heap heap, int index) {
		int leftIndex = getLeftIndex(index);
		int rightIndex = getRightIndex(index);
		T[] arr = heap.nodes;
		int largestIndex;
		if (leftIndex < heap.heapSize && arr[leftIndex].compareTo(arr[index]) > 0)
			largestIndex = leftIndex;
		else
			largestIndex = index;
		if (rightIndex < heap.heapSize && arr[rightIndex].compareTo(arr[largestIndex]) > 0)
			largestIndex = rightIndex;
		if (largestIndex != index) {
			swapArr(arr, index, largestIndex);
			maxHeapifyHelper(heap, largestIndex);
		}

	}

	// Creates max-heap from unsorted array.
	//CORRECT
	private Heap buildMaxHeapHepler(T[] arr) {
		// only half of the nodes can have children
		int halfLength = arr.length / 2;
		Heap heap = new Heap(arr);
		for (int i = halfLength; i >= 0; i--) {
			maxHeapifyHelper(heap, i);
		}
//		System.out.println("Builded heap: ");
//		printTree(arr);
		return heap;
	}

	/*
	 * sorts array without additional memory On every iteration swaps largest(0
	 * index) element with last element in array. After that maxHeapifyHepler
	 * function restore proper heap structure, besides that pointer of "last"
	 * element to swap shifts left
	 */
	public void heapSort(T[] array) {
		Heap heap = buildMaxHeapHepler(array);
		
		for (int i = array.length - 1; i >= 1; i--) {
			swapArr(array, 0, i);
			heap.heapSize--;
			maxHeapifyHelper(heap, 0);
//			printTree(array);
		}
	}

	private void insert(T value) {

	}

	private T extractMax() {
		return null;
	}

	private T getMax() {
		return null;
	}

	// ??
	private void increaseKey() {

	}

	private void swap(int first, int second) {
		T temp = nodes[first];
		nodes[first] = nodes[second];
		nodes[second] = temp;
	}

	private void swapArr(T[] arr, int first, int second) {
//		System.out.println("SWAP " + arr[first] + " " + arr[second]);
		T temp = arr[first];
		arr[first] = arr[second];
		arr[second] = temp;
	}
	
	public void printTree(T[] arr) {
		System.out.println("----------");
		printTreeUtil(arr, 0, 0);
		System.out.println("----------");
	}
	
	private void printArr(T[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
			if(i!= 0 && i % 30 == 0)
				System.out.println();
		}
		System.out.println();
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
			System.out.print(arr[parentIndex] );
		else
			System.out.print("root");
		System.out.println(")");
		printTreeUtil(arr, currDepth + 1,leftIndex);
	}

	private class Heap {
		int heapSize;
		T[] nodes;

		public Heap(T[] nodes) {
			this.nodes = nodes;
			heapSize = nodes.length;
		}

	}
}
