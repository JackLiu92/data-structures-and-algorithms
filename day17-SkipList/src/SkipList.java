import java.util.Comparator;

@SuppressWarnings("unchecked")
public class SkipList <K, V> {
	private static int MAX_LEVEl = 32;
	private static double P = 0.5;
	private Comparator<K> comparator;
	private Node<K, V> first;
	private int level;
	private int size;
	
	public SkipList() {
		this(null);
	}
	
	public SkipList(Comparator<K> comparator) {
		this.comparator = comparator;
		first = new Node<>(null, null, MAX_LEVEl);
	}
	
	public int size() { 
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public V get(K key) {
		keyCheck(key);
		
		Node<K, V> node = first;
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null && compare(key, node.nexts[i].key) > 0) {
				node = node.nexts[i];
			}
			if (cmp == 0) return node.nexts[i].value;
		}
		return null;
	}
	
	public V put(K key, V value) { 
		keyCheck(key);
		
		Node<K, V> node = first;
		Node<K, V>[] previousNodes = new Node[level];
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null && compare(key, node.nexts[i].key) > 0) {
				node = node.nexts[i];
			}
			
			if (cmp == 0) {
				V old = node.nexts[i].value;
				node.nexts[i].value = value;
				return old;
			}
			previousNodes[i] = node;
		}
		
		int newLevel = randomLevel();
		Node<K, V> newNode = new Node<>(key, value, newLevel);
		for (int i = 0; i < newLevel; i ++) {
			if (i >= level) {
				first.nexts[i] = newNode;
			} else {
				newNode.nexts[i] = previousNodes[i].nexts[i];
				previousNodes[i].nexts[i] = newNode;
			}
		}
		
		size++;
		level = Math.max(level, newLevel);
		
		return null;
	}
	
	private int randomLevel() {
		int level = 1;
		while (Math.random() < P && level < MAX_LEVEl) {
			level++;
		}
		return level;
	}
	
	private void keyCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null.");
		}
	}
	
	private int compare(K k1, K k2) {
		return comparator != null 
				? comparator.compare(k1, k2)
				: ((Comparable<K>)k1).compareTo(k2);
	}
	
	private static class Node<K, V> {
		K key;
		V value;
		Node<K, V>[] nexts;
		public Node(K key, V value, int level) {
			this.key = key;
			this.value = value;
			nexts = new Node[level];
		}
	}
}
