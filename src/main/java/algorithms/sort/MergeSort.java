package algorithms.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MergeSort {

	public static void main(String[] args) {
		ArrayList<Integer> arrayToSort = new ArrayList<Integer>();
		int bitShift = 1 << 4;
		for (int i = 0; i < bitShift; ++i) {
			arrayToSort.add(ThreadLocalRandom.current().nextInt());
		}
		System.out.println("Before sort: ");
		System.out.println(arrayToSort);

		arrayToSort = (ArrayList<Integer>) sort(arrayToSort);

		System.out.println("\n" + "After sort: ");
		System.out.println(arrayToSort);
	}

	public static List<Integer> sort(List<Integer> arrayToSort) {
		if (arrayToSort.size() == 1) {
			return (ArrayList<Integer>) arrayToSort;
		}
		if (arrayToSort.size() == 2) {
			return swapIfNecessary(arrayToSort);
		}
		int size = arrayToSort.size();
		int middle = size / 2;
		List<Integer> left = sort(arrayToSort.subList(0, middle));
		List<Integer> right = sort(arrayToSort.subList(middle, size));

		return merge(left, right);
	}

	private static List<Integer> merge(List<Integer> left, List<Integer> right) {

		ArrayList<Integer> result = new ArrayList<>();

		int sizeLeft = left.size();
		int sizeRight = right.size();

		int sumLength = sizeLeft + sizeRight;

		int l = 0;
		int r = 0;
		for (int z = 0; z <= sumLength; z++) {
			if (sizeLeft  == l) {
				for (int i = r; i < sizeRight; i++) {
					result.add(right.get(i));
				}

				return result;
			} else if (sizeRight  == r) {
				for (int i = l; i < sizeLeft; i++) {
					result.add(left.get(i));
				}
				return result;
			}
			Integer currLeft = left.get(l);
			Integer currRight = right.get(r);

			if (currLeft < currRight) {
				l++;
				result.add(currLeft);
			} else {
				r++;
				result.add(currRight);
			}
		}
		return result;
	}

	private static List<Integer> swapIfNecessary(List<Integer> arrToSort) {
		Integer zero = arrToSort.get(0);
		Integer one = arrToSort.get(1);
		if (zero > one) {
			arrToSort.set(1, zero);
			arrToSort.set(0, one);
		}
		return arrToSort;
	}
}
