package udo.util;

import java.util.HashMap;

public class ItemData {
	
	private HashMap<String, Object> mData;
	
	public ItemData() {
		mData = new HashMap<String, Object>();
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
}
