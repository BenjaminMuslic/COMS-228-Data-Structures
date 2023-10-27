package map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListBasedMap<K, V> implements SimpleMap<K, V> {
	private List<MapEntry> list = new LinkedList<MapEntry>();

	// Each entry has a key and a value.
	// Entries are compared by key only.
	private class MapEntry {
		public K key;
		public V value;

		public MapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

	}

	@Override
	public V put(K key, V value) {
		if (key == null)
			throw new IllegalArgumentException();

		for (MapEntry entry : list) {
			if (entry.key.equals(key)) {
				V ret = entry.value;
				entry.value = value;
				return ret;
			}
		}

		list.add(new MapEntry(key, value));
		return null;
	}

	@Override
	public V get(K key) {
		for (MapEntry entry : list) {
			if (entry.key.equals(key)) {
				return entry.value;
			}
		}
		return null;
	}

	@Override
	public V remove(K key) {
		Iterator<MapEntry> iter = list.iterator();
		while (iter.hasNext()) {
			MapEntry entry = iter.next();
			if (entry.key.equals(key)) {
				V ret = entry.value;
				iter.remove();
				return ret;
			}
		}

		return null;
	}

	@Override
	public boolean containsKey(K key) {
		for (MapEntry entry : list) {
			if (entry.key.equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Iterator<K> keyIterator() {
		return new KeyIterator();
	}

	private class KeyIterator implements Iterator<K> {
		private Iterator<MapEntry> iter = list.iterator();

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public K next() {
			return iter.next().key;
		}

		@Override
		public void remove() {
			iter.remove();
		}
	}
}
