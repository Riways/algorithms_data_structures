package data_structures;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrLst<E> extends AbstractSequentialList<E> implements Deque<E> {

	private final static int INITIAL_CAPACITY = 10;

	private static double EXPANSION_RATIO = 1.5;

	private int size;

	ArrayList<E> arl = new ArrayList<>();
	private E[] data;

	@SuppressWarnings("unchecked")
	public ArrLst(int capacity) {
		data = (E[]) new Object[capacity];
		size = 0;
	}

	@SuppressWarnings("unchecked")
	public ArrLst() {
		data = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	@Override
	public void addFirst(E e) {
		growIfNecessary(1);
		moveAllElementsToTheRight(1);
		data[0] = e;
		size++;
	}

	@Override
	public void addLast(E e) {
		growIfNecessary(1);
		int emptyIndex = getFirstEmptyBucketIndex();
		data[emptyIndex] = e;
		size++;
	}

	@Override
	public boolean add(E e) {
		growIfNecessary(1);
		int lastElementIndex = getIndexOflastElement();
		if (lastElementIndex < Integer.MAX_VALUE) {
			data[lastElementIndex + 1] = e;
			size++;
			return true;
		} else
			return false;
	}

	@Override
	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}

	@Override
	public boolean offerLast(E e) {
		addLast(e);
		return false;
	}

	public boolean isEmpty() {
		int freeBuckets = amountOfFreeBuckets();
		return freeBuckets == data.length;
	}

	@Override
	public E removeFirst() {
		E firstElement = data[0];
		moveAllElementsToTheLeft(1);
		size--;
		return firstElement;
	}

	@Override
	public E removeLast() {
		int indexOfLastElement = getIndexOflastElement();
		E lastElement = data[indexOfLastElement];
		data[indexOfLastElement] = null;
		size--;
		return lastElement;
	}

	@Override
	public E pollFirst() {
		if (isEmpty())
			return null;
		else {
			E firstElement = getFirst();
			removeFirst();
			return firstElement;
		}
	}

	@Override
	public E pollLast() {
		if (isEmpty())
			return null;
		else {
			E lastElement = getLast();
			removeLast();
			return lastElement;
		}
	}

	@Override
	public E getFirst() {
		return data[0];
	}

	@Override
	public E getLast() {
		int lastElementIndex = getIndexOflastElement();
		return data[lastElementIndex];
	}

	@Override
	public E peekFirst() {
		return data[0];
	}

	@Override
	public E peekLast() {
		int lastElementIndex = getIndexOflastElement();
		return data[lastElementIndex];
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		int firstOccuranceIndex = getFirstOccuranceIndex((E) o);
		if (firstOccuranceIndex > 0)
			return remove(firstOccuranceIndex) != null;
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		int lastOccuranceIndex = getLastOccuranceIndex((E) o);
		if (lastOccuranceIndex > 0)
			return remove(lastOccuranceIndex) != null;
		return false;
	}

	@Override
	public boolean offer(E e) {
		return add(e);
	}

	@Override
	public E remove(int index) {
		E element = data[index];
		moveAllElementsToTheLeftFromIndex(1, index);
		size--;
		return element;
	}

	@Override
	public E remove() {
		return pollFirst();
	}

	@Override
	public E poll() {
		return pollFirst();
	}

	@Override
	public E element() {
		return peek();
	}

	@Override
	public E peek() {
		return peekFirst();
	}

	@Override
	public void push(E e) {
		addFirst(e);
	}

	@Override
	public E pop() {
		return removeFirst();
	}

	@Override
	public Iterator<E> descendingIterator() {
		return new DescendingIteratorIpml();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new ListIteratorImpl(index);
	}

	@Override
	public int size() {
		int freeBuckets = amountOfFreeBuckets();
		return data.length - freeBuckets;
	}

	private int getFirstOccuranceIndex(E e) {
		int lastIndex = data.length - 1;
		for (int i = 0; i <= lastIndex; i++) {
			if (data[i].equals(e)) {
				return i;
			}
		}
		return -1;
	}

	private int getLastOccuranceIndex(E e) {
		int lastIndex = data.length - 1;
		for (int i = lastIndex; i >= 0; i++) {
			if (data[i].equals(e)) {
				return i;
			}
		}
		return -1;
	}

	private void moveAllElementsToTheRight(int positions) {
		int lastElementIndex = getIndexOflastElement();
		for (int i = lastElementIndex; i >= 0; i--) {
			data[i + positions] = data[i];
			data[i] = null;
		}
	}

	private void moveAllElementsToTheRightFromIndex(int positions, int startIndex) {
		int lastElementIndex = getIndexOflastElement();
		for (int i = lastElementIndex; i >= startIndex; i--) {
			data[i + positions] = data[i];
			data[i] = null;
		}
	}

	private void moveAllElementsToTheLeft(int positions) {
		int lastElementIndex = getIndexOflastElement();
		for (int i = 0; i <= lastElementIndex; i++) {
			data[i] = data[i + positions];
			data[i + positions] = null;
		}
	}

	private void moveAllElementsToTheLeftFromIndex(int positions, int startIndex) {
		int lastElementIndex = getIndexOflastElement();
		for (int i = startIndex; i <= lastElementIndex; i++) {
			data[i] = data[i + positions];
			data[i + positions] = null;
		}
	}

	private int getIndexOflastElement() {
		return size-1;
	}

	private int getFirstEmptyBucketIndex() {
		return size;
	}

	private void growIfNecessary(int amountOfElementsToInsert) {
		int freeBuckets = amountOfFreeBuckets();
		if (freeBuckets > amountOfElementsToInsert)
			return;
		int finalSize = getExpansionSize();
		data = Arrays.copyOf(data, finalSize);
	}
	
	private int getExpansionSize() {
		int currentSize = data.length;
		double newSizeDouble = currentSize * EXPANSION_RATIO;
		int finalSize;
		if (newSizeDouble > Integer.MAX_VALUE) {
			finalSize = Integer.MAX_VALUE - 16;
		} else {
			finalSize = (int) newSizeDouble;
		}
		return finalSize;
	}

	private int amountOfFreeBuckets() {
		return data.length - size;
	}

	private class DescendingIteratorIpml implements Iterator<E> {

		int currentPosition = getIndexOflastElement();

		@Override
		public boolean hasNext() {
			return currentPosition >= 0;
		}

		@Override
		public E next() {
			return data[currentPosition--];
		}

	}

	private class ListIteratorImpl implements ListIterator<E> {

		int lastElementIndex = getIndexOflastElement();

		int currentIndex;

		public ListIteratorImpl(int startIndex) {
			currentIndex = startIndex;
		}

		@Override
		public boolean hasNext() {
			return currentIndex <= lastElementIndex && size > 0;
		}

		@Override
		public E next() {
			return data[currentIndex++];
		}

		@Override
		public boolean hasPrevious() {
			return currentIndex <= lastElementIndex && currentIndex > 0;
		}

		@Override
		public E previous() {
			return data[--currentIndex];
		}

		@Override
		public int nextIndex() {
			return currentIndex;
		}

		@Override
		public int previousIndex() {
			return currentIndex - 1;
		}

		@Override
		public void remove() {
			moveAllElementsToTheLeftFromIndex(1, currentIndex);
			size--;
		}

		@Override
		public void set(E e) {
			data[currentIndex] = e;
		}

		@Override
		public void add(E e) {
			growIfNecessary(1);
			moveAllElementsToTheRightFromIndex(1, currentIndex);
			data[currentIndex] = e;
			size++;
		}

	}
}
