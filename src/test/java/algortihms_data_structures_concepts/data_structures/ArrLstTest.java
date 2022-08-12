package algortihms_data_structures_concepts.data_structures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import data_structures.ArrList;

class ArrLstTest {

	ArrList<Integer> list = new ArrList<>();
	ArrayList<Integer> arrList = new ArrayList<>();

	private void initEmptyList() {
		list = new ArrList<>();
	}

	private void initListBeforeExpansion() {
		list = new ArrList<>();
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
		list.add(ThreadLocalRandom.current().nextInt(10));
	}
	
	

	public void initListBeforeOverflow() {
		list = new ArrList<>();
		long start = System.currentTimeMillis();
		int expansion = 10;
		for (int i = 0; i < Integer.MAX_VALUE - 8; i++) {
			try {
				list.add(0);
				if (i == expansion) {
					expansion = (int)(expansion * 1.5);
					if (i == 354836040) {
						long end = System.currentTimeMillis();
						System.out.println((end - start) / 1000 + "seconds");
					}
					if (i > Integer.MAX_VALUE - 100) {
						System.out.println(i);
					}
				}
			}
			catch (OutOfMemoryError e) {
				System.out.println("err");
				System.out.println(i / 1000000 * 4 * 2.5  + " mb of memory needed");
			}
		}

	}

	@Test
	public void testAddFirstAndGetFirst() {
		initEmptyList();
		list.addFirst(1);
		assertEquals(list.getFirst(), 1);
		list.addFirst(31);
		assertEquals(list.getFirst(), 31);
	}

	@Test
	public void testAddAndGetLast() {
		initEmptyList();
		list.addLast(21);
		assertEquals(list.getLast(), 21);
		list.addLast(12);
		assertEquals(list.getLast(), 12);
		list.addLast(121);
		assertEquals(list.getLast(), 121);
	}

	@Test
	public void isEmptyTest() {
		initEmptyList();
		assertEquals(list.isEmpty(), true);
		list.addFirst(1);
		assertEquals(list.isEmpty(), false);
	}

	@Test
	public void testPollFirst() {
		initEmptyList();
		list.addFirst(121312);
		Integer el = list.pollFirst();
		assertEquals(el, 121312);
		assertEquals(list.size(), 0);
	}

	@Test
	public void testRemoveFirst() {
		initEmptyList();
		list.addFirst(121312);
		list.removeFirst();
		assertEquals(list.size(), 0);
	}

	@Test
	public void testPollLast() {
		initEmptyList();
		list.addFirst(121312);
		list.addLast(31);
		Integer el = list.pollLast();
		assertEquals(el, 31);
		assertEquals(list.size(), 1);
	}

	@Test
	public void testRemoveLast() {
		initEmptyList();
		list.addFirst(121312);
		list.addFirst(12131312);
		list.removeLast();
		assertEquals(list.size(), 1);
	}

	@Test
	public void iteratorTest() {
		initListBeforeExpansion();
		Iterator<Integer> it = list.iterator();
		int counter = 0;
		while(it.hasNext()) {
			counter++;
			it.next();
		}
		assertEquals(counter, 10);
	}
	
	@Test
	public void listIteratorTest() {
//		initListBeforeOverflow();
		initListBeforeExpansion();
		ListIterator<Integer> it = list.listIterator();
		int counter = 0;
		int previous;
		while(it.hasNext()) {
			counter++;
			previous = it.next();
			assertEquals(previous, it.previous());	
			it.remove();
		}
		assertEquals(counter, 10);
		assertEquals(list.size(), 0);
	}

}
