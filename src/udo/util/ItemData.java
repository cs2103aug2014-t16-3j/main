package udo.util;

import java.util.HashMap;
import java.util.Set;

public class ItemData {
	
	private HashMap<String, Object> mData;
	
	public ItemData() {
		mData = new HashMap<String, Object>();
	}
	
	@Override
	public String toString() {
		String info = "%1$s|||%2$s|||%3$s|||%4$s|||%5$s|||%6$s";
		String result = String.format(info, 
				mData.get("type"),
				mData.get("title"),
				mData.get("date"),
				mData.get("start time"),
				mData.get("end time"),
				mData.get("tags"));
		return result;
	}
	
	/**
	 * Associates the specified item with the specified name
	 * An existing item of the same name will be replaced. 
	 * The item inserted cannot be {@code null}.
	 * @param name The name of the item.
	 * @param item The item to be put inside
	 * @return {@code true} when the operation is successful, 
	 * or {@code false} when the inserted item is null
	 */
	public boolean put(String name, Object item) {
		if (item != null) {
			mData.put(name, item);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Retrieves the item that is associated with the name.
	 * @param name The name of the item to retrieve
	 * @return The item, or {@code null} if the name is not mapped.
	 */
	public Object get(String name) {
		if (mData.containsKey(name)) {
			return mData.get(name);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a boolean value describing if the named item exists inside.
	 * @param name The name of the item to check.
	 * @return {@code true} if the item exists, or {@code false} otherwise.
	 */
	public boolean contains(String name) {
		return mData.containsKey(name);
	}
	
	public Set<String> getNames() {
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
