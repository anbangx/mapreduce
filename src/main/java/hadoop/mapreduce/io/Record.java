package hadoop.mapreduce.io;

public class Record<K extends Comparable, V> implements Comparable{
	
	K key;
	V value;
	
	public Record(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public int compareTo(Object o) {
		return 0;
	}
	
}
