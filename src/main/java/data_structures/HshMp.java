package data_structures;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HshMp<K, V> implements Map<K, V> {

	ListNode<K, V>[] map;

	final static private int DEFAULT_INITIAL_CAPACITY = 1 << 4;

	final static private double DEFAULT_LOAD_FACTOR = 0.75;

	final static private int MAX_MAP_CAPACITY = 1 << 30;

	private int size;

	private Set<Entry<K, V>> entrySet;

	@SuppressWarnings("unchecked")
	public HshMp() {
		map = (ListNode<K, V>[]) new ListNode[DEFAULT_INITIAL_CAPACITY];
		entrySet = new HashSet<>();
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		for (ListNode<K,V> root : map) {
			while (root != null) {
				if (root.getValue().equals(value))
					return true;
				root = root.next;
			}
		}
		return false;
	}

	@Override
	public V get(Object key) {
		int index = generateIndex((K) key);
		ListNode<K,V> root = map[index];
		while (root != null) {
			if (root.key.equals(key))
				return (V) root.value;
			root = root.next;
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		putValue(key, value);
		return value;
	}

	@Override
	public V remove(Object key) {
		int index = generateIndex((K) key);

		ListNode<K,V> node = map[index];
		ListNode<K,V> prev = null;
		V value = null;
		while (node != null) {
			if (node.key.equals(key)) {
				value = (V) node.value;
				if (prev == null) {
					map[index] = node.next;
				} else {
					prev.next = node.next;
				}
				size--;
				entrySet.remove(node);
				return value;
			}
			prev = node;
			node = node.next;
		}
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			putNode((ListNode<K, V>) entry);
		}

	}

	@Override
	public void clear() {
		size = 0;
		for (int i = 0; i < map.length; i++) {
			ListNode<K,V> root = map[i];
			while (root != null) {
				ListNode<K,V> temp = root;
				root = root.next;
				temp = null;
			}
		}
		entrySet.clear();
	}

	@Override
	public Set<K> keySet() {
		Set<K> keys = new HashSet<>();
		entrySet.forEach((entry) -> {
			keys.add(entry.getKey());
		});
		return keys;
	}

	@Override
	public Collection<V> values() {
		Set<V> values = new HashSet<>();
		entrySet.forEach((entry) -> {
			values.add(entry.getValue());
		});
		return values;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return (Set<Entry<K, V>>) entrySet;
	}

	private int hash(K key) {
		int objHashcode = key.hashCode();
		int index = objHashcode ^ (objHashcode >>> 16);
		return index;
	}

	// return index for key
	private int generateIndex(K key) {
		int hash = hash(key);
		int lastIndex = map.length - 1;
		return hash & lastIndex;
	}

	private void putValue(K key, V value) {
		ListNode<K, V> node = new ListNode<K, V>(key, value);
		putNode(node);
	}

	private void putNode(ListNode<K,V> newNode) {
		
		growIfNecessary();

		int index = generateIndex((K) newNode.getKey());

		ListNode<K,V> pointer = map[index];
		ListNode<K,V> prev = null;

		if (pointer == null) {
			map[index] = newNode;
			size++;
			entrySet.add(newNode);
			return;
		}

		while (pointer != null) {
			if (pointer.key.equals(newNode.key)) {
				if (pointer.value == newNode.value)
					return;
				pointer.value = newNode.value;
				return;
			}
			prev = pointer;
			pointer = pointer.next;
		}

		if (prev == null) {
			newNode.next = pointer.next;
			map[index] = newNode;
		} else {
			prev.next = newNode;
		}
		size++;
		entrySet.add(newNode);
	}
	

	@SuppressWarnings("unchecked")
	private void growIfNecessary() {
		if (size <= (int) (map.length * DEFAULT_LOAD_FACTOR) || map.length == MAX_MAP_CAPACITY) {
			return;
		}
		int nextCapacity = calculateNextCapacity();
		showDistribution();
		map = new ListNode[nextCapacity];
		for (Entry<K, V> node : entrySet) {
			putNode((ListNode<K, V>) node);
		}

	}

	private void showDistribution() {
		System.out.println("DISTRIBUTION");
		for (int i = 0; i < map.length; i++) {
			int counter = 0;
			ListNode<K,V> pointer = map[i];
			while (pointer != null) {
				counter++;
				pointer = pointer.next;
			}
			if (counter > 1 | counter == 0) {
				System.out.println("Bucket " + i + " : " + counter);
			}

		}
	}

	private int calculateNextCapacity() {
		int newCapacity = map.length << 1;
		if (newCapacity >= MAX_MAP_CAPACITY) {
			return MAX_MAP_CAPACITY;
		}
		return newCapacity;
	}

	@SuppressWarnings("unused")
	private class ListNode<K, V> implements Entry<K, V> {

		private K key;
		private V value;

		ListNode<K,V> next;

		public ListNode() {
			super();
		}

		public ListNode(K key, V value) {
			super();
			this.key = key;
			this.value = value;
			next = null;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Objects.hash(key);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			if (!this.equals(obj))
				return false;
			ListNode<K,V> other = (ListNode<K,V>) obj;
			if (this.key == other.key) {
				return true;
			}
			return false;
		}

	}
}
