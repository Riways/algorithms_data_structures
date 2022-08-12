package algorithms.sort;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSortExtraSpace {
	

	public static ArrayList<Integer> sort(ArrayList<Integer> arrToSort) {
		if(arrToSort.size() == 1) {
			return arrToSort;
		}
		if(arrToSort.size() == 2) {
			return swapIfNecessary(arrToSort);
		}
		int pivotIndex = arrToSort.size()/2;
		int pivot = arrToSort.get(pivotIndex);
		
		
		ArrayList<Integer> smallerThanPivot = new ArrayList<Integer>();
		ArrayList<Integer> biggerThanPivot = new ArrayList<Integer>();
		
		for(int i = 0; i < arrToSort.size(); i++) {
			if(i == pivotIndex)
				continue;
			
			if(arrToSort.get(i)<pivot)
				smallerThanPivot.add(arrToSort.get(i));
			else
				biggerThanPivot.add(arrToSort.get(i));
		}
		
		if(smallerThanPivot.size() == 0) {
			biggerThanPivot = sort(biggerThanPivot);
			biggerThanPivot.add(0, pivot);
			return biggerThanPivot;
		}else if(biggerThanPivot.size() == 0 ) {
			smallerThanPivot = sort(smallerThanPivot);
			smallerThanPivot.add(pivot);
			return smallerThanPivot;
		}
		
		smallerThanPivot = sort(smallerThanPivot);
		biggerThanPivot = sort(biggerThanPivot);
		
		smallerThanPivot.add(pivot);
		
		smallerThanPivot.addAll(biggerThanPivot);
		return smallerThanPivot;
	}
	
	private static ArrayList<Integer> swapIfNecessary(ArrayList<Integer> arrToSort) {
		Integer zero = arrToSort.get(0);
		Integer one = arrToSort.get(1);
		if(zero > one) {
			arrToSort.set(1, zero);
			arrToSort.set(0, one);
		}
		return arrToSort;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> arrToSort = new ArrayList<Integer>();
		int bitShift = 1 << 10;
		for(int i = 0; i < bitShift ; ++i) {
			arrToSort.add(ThreadLocalRandom.current().nextInt());
		}
		System.out.println(arrToSort.size());
		arrToSort = sort(arrToSort);
		System.out.println(arrToSort );
	}
}
