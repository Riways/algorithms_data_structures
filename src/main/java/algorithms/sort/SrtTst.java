package algorithms.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class SrtTst {

	public static void main(String[] args) {
		ArrayList<Integer> arrayToSort = new ArrayList<Integer>();
		int bitShift = 1 << 23;

		long quickSortTime;
		long mergeSortTime;
		long javaDefaultSortTime;

		for (int i = 0; i < bitShift; ++i) {
			arrayToSort.add(ThreadLocalRandom.current().nextInt());
		}

		long start = System.currentTimeMillis();
		ArrayList<Integer> quickSortResult = QckSrtExtrSpc.sort(arrayToSort);
		long end = System.currentTimeMillis();
		quickSortTime = (end - start) / 1000;

		start = System.currentTimeMillis();
		ArrayList<Integer> mergeSortResult = (ArrayList<Integer>) MrgSrt.sort(arrayToSort);
		end = System.currentTimeMillis();
		mergeSortTime = (long) (end - start) / 1000;

		System.out.println("YO");
		System.out.println(mergeSortResult.size());
		System.out.println(quickSortResult.size());

		start = System.currentTimeMillis();
		Collections.sort(arrayToSort);
		end  = System.currentTimeMillis();
		
		javaDefaultSortTime = (end - start) / 1000;
		

		for (int i = 0; i < arrayToSort.size(); i++) {
			int quickVal = quickSortResult.get(i);
			int mergeVal = mergeSortResult.get(i);
			int properVal = arrayToSort.get(i);
			if (mergeVal != properVal) {
				System.out.println("MERGE SORT BAD: " + i);
				System.out.println(arrayToSort.subList(i - 2, i + 2));
				System.out.println(mergeSortResult.subList(i - 2, i + 2));
			}
			if (quickVal != properVal) {
				System.out.println("QUICK SORT BAD: " + i);
				System.out.println(arrayToSort.subList(i, i + 5));
				System.out.println(quickSortResult.subList(i, i + 5));
			}
		}

		System.out.println("QuickSort time: " + quickSortTime);
		System.out.println("MergeSort time: " + mergeSortTime);
		System.out.println("Java Collections.sort time: " + javaDefaultSortTime);

	}
}
