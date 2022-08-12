package algorithms.sort;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class HeapSortTest {

	HeapSort<Integer> heapSort = new HeapSort<>();

	private Integer[] genereateArrWithTenElements() {
		Integer[] arr = new Integer[10];
		for (int i = 0; i < 10; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0, 10);
		}
		return arr;
	}

	@Test
	void heapSortTest() {
		Integer[] arr = genereateArrWithTenElements();
		System.out.println("Before sort: ");
		printArr(arr);
		heapSort.heapSort(arr);
		System.out.println("After sort: ");
		printArr(arr);
		if(!isArraySorted(arr))
			fail();
	}

	private boolean isArraySorted(Integer[]arr) {
		if(arr.length == 1) {
			return true;
		}
		for(int i= 1; i < arr.length; i++) {
			if(arr[i] < arr[i-1]) {
				return false;
			}
		}
		return true;
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
