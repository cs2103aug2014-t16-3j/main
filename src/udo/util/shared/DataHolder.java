package udo.util.shared;

import java.util.HashMap;
import java.util.Set;

public class DataHolder {
	
	protected HashMap<String, Object> mData;
	
	public DataHolder() {
		mData = new HashMap<String, Object>();
	}
	
	/**
	 * Associates the specified item with the specified key An existing item of
	 * the same key will be replaced. The item inserted cannot be {@code null}.
	 * 
	 * @param key
	 *            The key of the item.
	 * @param item
	 *            The item to be put inside
	 * @return {@code true} when the operation is successful, or {@code false}
	 *         when the inserted item is null
	 */
	public boolean put(String key, Object item) {
		if (item != null) {
			mData.put(key, item);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves the item that is associated with the key.
	 * 
	 * @param key
	 *            The key of the item to retrieve
	 * @return The item, or {@code null} if the key is not mapped.
	 */
	public Object get(String key) {
		if (mData.containsKey(key)) {
			return mData.get(key);
		} else {
			return null;
		}
	}

	/**
	 * Returns a boolean value describing if the named item exists inside.
	 * 
	 * @param key
	 *            The key of the item to check.
	 * @return {@code true} if the item exists, or {@code false} otherwise.
	 */
	public boolean contains(String key) {
		return mData.containsKey(key);
	}

	/**
	 * Returns a {@link Set} view of the keys contained in this object.
	 * 
	 * @return the keyset
	 */
	public Set<String> getKeys() {
		return mData.keySet();
	}

	/**
	 * Returns the hashmap that holds the data.
	 * 
	 * @return the hashmap
	 */
	public HashMap<String, Object> getData() {
		return mData;
	}
}
