package algorithms.sort;


public class HeapSort <T extends Comparable<T>>{

	
	
	public void heapSort(T[] array) {
		array = buildMaxHeapHepler(array);
		int heapSize = array.length;
		for (int i = array.length - 1; i >= 1; i--) {
			swapArr(array, 0, i);
			heapSize--;
			maxHeapifyHelper(array, 0, heapSize);
//			printTree(array);
		}
	}
	
	private int getParentIndex(int i) {
		return i << 1;
	}

	private int getLeftIndex(int i) {
		return (i << 1) + 1;
	}

	private int getRightIndex(int i) {
		return (i << 1) + 2;
	}
	
	private void maxHeapifyHelper(T[] arr, int index, int heapSize) {
		int leftIndex = getLeftIndex(index);
		int rightIndex = getRightIndex(index);
		int largestIndex;
		if (leftIndex < heapSize && arr[leftIndex].compareTo(arr[index]) > 0)
			largestIndex = leftIndex;
		else
			largestIndex = index;
		if (rightIndex < heapSize && arr[rightIndex].compareTo(arr[largestIndex]) > 0)
			largestIndex = rightIndex;
		if (largestIndex != index) {
			swapArr(arr, index, largestIndex);
			maxHeapifyHelper(arr, largestIndex, heapSize);
		}

	}
	
	private T[] buildMaxHeapHepler(T[] arr) {
		int halfLength = arr.length / 2;
		for (int i = halfLength; i >= 0; i--) {
			maxHeapifyHelper(arr, i, arr.length);
		}
		return arr;
	}
	
	private void swapArr(T[] arr, int first, int second) {
		T temp = arr[first];
		arr[first] = arr[second];
		arr[second] = temp;
	}
}
