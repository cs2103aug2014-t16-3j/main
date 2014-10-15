package udo.util.shared;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.StorageStrings;

/**
 * This class holds the all the data that an item should have, like title, date,
 * hashtag, so on. The keys for the put() and get() function must be one of the
 * keys from the Constants.Keys class.
 * 
 */
public class ItemData implements Comparable<ItemData> {

	private ItemType mType;
	private HashMap<String, Object> mData;

	public ItemData(ItemType type) {
		mType = type;
		mData = new HashMap<String, Object>();
	}

	public ItemType getItemType() {
		return mType;
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

	public HashMap<String, Object> getData() {
		return mData;
	}

	@Override
	public String toString() {
		// TODO need to revise
		switch (mType) {
		case EVENT :
			return makeEventString();
		case TASK : 
			return makeTaskString();
		default:
			return null;
		}
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
		if (other == this) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!other.getClass().equals(this.getClass())) {
			return false;
		} else {
			ItemData otherItem = (ItemData) other;
			if (!this.getItemType().equals(otherItem.getItemType())) {
				return false;
			}
			if (!this.getData().equals(otherItem.getData())) {
				return false;
			}
			return true;
		}
	}

	@Override
	public int compareTo(ItemData other) {
		Calendar thisCal;
		Calendar otherCal;

		if (this.contains(Keys.START)) {
			thisCal = (Calendar) this.get(Keys.START);
		} else if (this.contains(Keys.DUE)) {
			thisCal = (Calendar) this.get(Keys.DUE);
		} else {
			thisCal = null;
		}

		if (other.contains(Keys.START)) {
			otherCal = (Calendar) other.get(Keys.START);
		} else if (other.contains(Keys.DUE)) {
			otherCal = (Calendar) other.get(Keys.DUE);
		} else {
			otherCal = null;
		}

		if (thisCal == null && otherCal == null) {
			// they are equal if nothing to compare
			return 0;
		} else if (thisCal == null && otherCal != null) {
			// this object is less than the other
			// if the other has a date and this doesnt.
			return -1;
		} else if (thisCal != null && otherCal == null) {
			// the other object is less than this object
			// if this object has a date and the other doesnt.
			return 1;
		} else if (thisCal != null && otherCal != null) {
			// if both are not null, then can just return
			// the comparision to the
			return thisCal.compareTo(otherCal);
		} else {
			// this should not happen
			return 0;
		}
	}

	private String getTagString(ArrayList<String> list) {
		String result = new String();
		for (int i = 0; i < list.size(); i++) {
			result = result.concat(list.get(i));
			if (i != list.size() - 1) { //not last element 
				result = result.concat(StorageStrings.TAG_DELIMITER);
			}
		}
		assert (result != null);
		return result;
	}

	private String makeEventString() {
		// uid | type | title | sdate | stime | edate | etime | tags
		Calendar startCal = (Calendar) mData.get(Keys.START);
		Calendar endCal = (Calendar) mData.get(Keys.END);
		@SuppressWarnings("unchecked")
		ArrayList<String> taglist = (ArrayList<String>) mData.get(Keys.HASHTAGS);
		assert (taglist != null);
		String tagsString = getTagString(taglist);
	
		String eventInfo = "%1$d|||%2$s|||%3$s|||%4$d/%5$d/%6$d"
				+ "|||%7$d:%8$d|||%9$d/%10$d/%11$d" + "|||%12$d:%13$d|||%14$s";
		String result = String.format(
				eventInfo,
				mData.get(Keys.UID),
				mType.toString(),
				mData.get(Keys.TITLE),
				startCal.get(Calendar.DAY_OF_MONTH),
				startCal.get(Calendar.MONTH) + 1, // add 1 to offset 0-basing in cal object
				startCal.get(Calendar.YEAR),
				startCal.get(Calendar.HOUR_OF_DAY),
				startCal.get(Calendar.MINUTE),
				endCal.get(Calendar.DAY_OF_MONTH),
				endCal.get(Calendar.MONTH) + 1, // add 1 to offset 0-basing in cal object
				endCal.get(Calendar.YEAR), 
				endCal.get(Calendar.HOUR_OF_DAY),
				endCal.get(Calendar.MINUTE), 
				tagsString);
		return result;
	}

	private String makeTaskString() {
		// uid | type | title | sdate | stime | edate | etime | tags
		Calendar startCal = (Calendar) mData.get(Keys.START);
		Calendar endCal = (Calendar) mData.get(Keys.END);
		@SuppressWarnings("unchecked")
		ArrayList<String> taglist = (ArrayList<String>) mData.get(Keys.HASHTAGS);
		assert (taglist != null);
		String tagsString = getTagString(taglist);

		String eventInfo = "%1$d|||%2$s|||%3$s|||%4$d/%5$d/%6$d"
				+ "|||%7$d:%8$d|||%9$d/%10$d/%11$d" + "|||%12$d:%13$d|||%14$s";
		String result = String.format(
				eventInfo,
				mData.get(Keys.UID),
				mType.toString(),
				mData.get(Keys.TITLE),
				startCal.get(Calendar.DAY_OF_MONTH),
				startCal.get(Calendar.MONTH) + 1, // add 1 to offset 0-basing in cal object
				startCal.get(Calendar.YEAR),
				startCal.get(Calendar.HOUR_OF_DAY),
				startCal.get(Calendar.MINUTE),
				endCal.get(Calendar.DAY_OF_MONTH),
				endCal.get(Calendar.MONTH) + 1, // add 1 to offset 0-basing in cal object
				endCal.get(Calendar.YEAR), 
				endCal.get(Calendar.HOUR_OF_DAY),
				endCal.get(Calendar.MINUTE), 
				tagsString);
		return result;
	}
}
