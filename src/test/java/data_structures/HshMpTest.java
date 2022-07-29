package data_structures;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class HshMpTest {
	
	HshMp<Integer, String> map;
	
	private void initEmptyMap() {
		map = new HshMp<>();
	}
	
//	private void initGrowedMap() {
//		
//		map = new HshMp<>();
//		int dblChck = 1;
//		for (int i = 0; i < 1<<29; i++) {
//			map.put(i, "" );
//			if(i % (1<<dblChck) == 0) {
//				dblChck++ ;
//				System.out.println(i);
//			}
//			
//		}
//		
//		HashMap<Integer, String> mapp = new HashMap<>();
//		int dblChck = 1;
//		for (int i = 0; i < 1<<29; i++) {
//			mapp.put(i, "" );
//			if(i % (1<<dblChck) == 0) {
//				dblChck++ ;
//				System.out.println(i);
//			}
//			
//		}
//	}

	@Test
	void initTest() {
		initEmptyMap();
	}
	
	@Test
	void putAndGetTest() {
		initEmptyMap();
		map.put(2, "dasd");
		assertEquals(1, map.size());
		map.put(2, "dasd");
		assertEquals(1, map.size());
		assertEquals(map.get(2), "dasd");
		assertEquals(1, map.size());
		map.put(3213123, "dasd");
		assertEquals(2, map.size());
		map.put(113, "dasd");
		assertEquals(3, map.size());
		map.put(13213, "dasd");
		assertEquals(4, map.size());
		map.put(3133123, "dasddasdas");
		assertEquals(5, map.size());
		assertEquals(map.get(3133123), "dasddasdas");
		map.put(3133123, "a");
		assertEquals(map.get(3133123), "a");
		assertEquals(map.get(3133123), "a");
	}
	
	@Test
	void removeTest() {
		initEmptyMap();
		map.put(1, "str");
		map.put(2, "dadsa");
		map.put(321, "dasdsada");
		assertEquals(map.size(),3);
		assertEquals(map.get(2), "dadsa");
		map.remove(2);
		assertEquals(2, map.entrySet().size());
		map.remove(2);
		assertEquals(2, map.entrySet().size());
		assertNull(map.get(2) );
		assertEquals(map.size(),2);
		map.remove(1);
		assertEquals(1, map.entrySet().size());
		assertEquals(map.size(),1);
		map.remove(321);
		assertEquals(map.size(),0);
		assertEquals(0, map.entrySet().size());
	}   
	
//	@Test
//	void growTest() {
//		initGrowedMap();
//	}
	
	@Test
	void containsKeyTest() {
		initEmptyMap();
		map.put(1, "a");
		map.put(12, "ab");
		map.put(13, "ac");
		map.put(14, "ad");
		
		assertTrue(map.containsKey(1));
		assertTrue(map.containsKey(12));
		assertTrue(map.containsKey(13));
		assertTrue(map.containsKey(14));
		
		assertFalse(map.containsKey(111));
		
		map.remove(12);
		assertFalse(map.containsKey(12));
	}
	
	@Test
	void containsValueTest() {
		initEmptyMap();
		map.put(1, "a");
		map.put(12, "ab");
		map.put(13, "ac");
		map.put(14, "ad");
		
		assertTrue(map.containsValue("a"));
		assertTrue(map.containsValue("ab"));
		assertTrue(map.containsValue("ac"));
		assertTrue(map.containsValue("ad"));
		
		assertFalse(map.containsValue("x"));
		
		map.remove(12);
		assertFalse(map.containsValue("ab"));
	}

}
