package data_structures.trees;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;



class HeapMaxTest {
	
	HeapMax<Integer> heap = new HeapMax<>();

	private Integer[] genereateArrWithHundredElements() {
		Integer[] arr = new Integer[100];
		for(int i=0; i < 100; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0,100);
		}
		return arr;
	}
	private Integer[] genereateArrWithMillionElements() {
		Integer[] arr = new Integer[1000000];
		for(int i=0; i < 1000000; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0,1000000);
		}
		return arr;
	}
	private Integer[] genereateArrWithTenElements() {
		Integer[] arr = new Integer[10];
		for(int i=0; i < 10; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(0,10);
		}
		return arr;
	}
	
	@Test
	void heapSortTest() {
		Integer[] arr = genereateArrWithMillionElements();
//		System.out.println("Before sort: ");
//		printArr(arr);
		double start = System.currentTimeMillis();
		heap.heapSort(arr);
		double end = System.currentTimeMillis();
		System.out.println((end- start) / 1000 + " seconds ");
//		System.out.println("After sort: ");
//		printArr(arr);
		for (int i = 1; i < arr.length; i++) {
			if(arr[i] < arr[i-1]) {
				fail();
			}
		}
		assertEquals(arr.length, 1000000);
		
	}
	
	private void printArr(Integer[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
			if(i!= 0 && i % 30 == 0)
				System.out.println();
		}
		System.out.println();
	}
}
