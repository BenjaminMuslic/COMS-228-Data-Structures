package map;

import java.util.Iterator;

public interface SimpleMap<K, V> {
	/**
	 * Creates key -> value. 
	 * 
	 * @param key
	 * @param value
	 * @return previous value associated with key or null
	 */
	public V put(K key, V value);

	/**
	 * 
	 * @param key
	 * @return value such that key -> value, or null if there is no mapping
	 */
	public V get(K key);

	/**
	 * Deletes the mapping key -> ? (anything). 
	 * 
	 * @param key
	 * @return previous value or null 
	 */
	public V remove(K key);

	/** 
	 * 
	 * @param key
	 * @return true if the map contains key
	 */
	public boolean containsKey(K key);

	public int size();

	/**
	 * 
	 * @return an iterator over the key set of the map
	 */
	public Iterator<K> keyIterator();
}
