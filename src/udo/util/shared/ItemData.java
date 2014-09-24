package udo.util.shared;

import java.util.HashMap;
import java.util.Set;

/**
 * This class holds the all the data that an item should have,
 * like title, date, hashtag, so on.
 *
 */
public class ItemData {
	
	private HashMap<String, Object> mData;
	
	public ItemData() {
		mData = new HashMap<String, Object>();
	}
	
	@Override
	public String toString() {
		//TODO need to revise
		String eventInfo = "%1$s|||%2$s|||%3$s|||%4$s|||%5$s";
		String result = String.format(eventInfo, 
				mData.get("type"),
				mData.get("title"),
				mData.get("start"),
				mData.get("end"),
				mData.get("tags"));
		return result;
	}
	
	/**
	 * Associates the specified item with the specified key
	 * An existing item of the same key will be replaced. 
	 * The item inserted cannot be {@code null}.
	 * @param key The key of the item.
	 * @param item The item to be put inside
	 * @return {@code true} when the operation is successful, 
	 * or {@code false} when the inserted item is null
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
	 * @param key The key of the item to retrieve
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
	 * @param key The key of the item to check.
	 * @return {@code true} if the item exists, or {@code false} otherwise.
	 */
	public boolean contains(String key) {
		return mData.containsKey(key);
	}
	
	/**
     * Returns a {@link Set} view of the keys contained in this object.
     */
	public Set<String> getKeys() {
		return mData.keySet();
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for (Object value : mData.values()) {
			hash = hash | value.hashCode();
		}
		return hash;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		} else if (!other.getClass().equals(this.getClass())) {
			return false;
		} else {
			boolean match = true;
			for (String key : mData.keySet()) {
				Object otherValue = ((ItemData) other).get(key);
				Object thisValue = this.get(key);
				if (otherValue != null && otherValue.equals(thisValue)) {
					match = true;
				} else {
					match = false;
					break;
				}
			}
			return match;
		}
	}
}
