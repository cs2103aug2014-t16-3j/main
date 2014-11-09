//@author A0108358B
package udo.data;

import java.util.ArrayList;
import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.constants.Constants.StorageStrings;
import udo.enums.ItemType;

/**
 * This class holds the all the data that an item should have, like title, date,
 * hashtag, so on. The keys for the put() and get() function must be one of the
 * keys from the Constants.Keys class.
 * 
 */
public class ItemData extends DataHolder implements Comparable<ItemData> {

	private ItemType mType;
	
	public ItemData() {
		mType = ItemType.PLAN; // a default value
	}

	public ItemData(ItemType type) {
		super();
		mType = type;
	}
	
	public void setItemType(ItemType type) {
		mType = type;
	}

	public ItemType getItemType() {
		return mType;
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
	public String toString() {
		switch (mType) {
		case EVENT :
			return makeEventString();
		case TASK : 
			return makeTaskString();
		case PLAN : 
			return makePlanString();
		default:
			return null;
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
			// this is placed behind the other
			// if the other has a date and this doesnt.
			return 1;
		} else if (thisCal != null && otherCal == null) {
			// this is placed in front of the other 
			// if this has a date and the other doesnt.
			return -1;
		} else if (thisCal != null && otherCal != null) {
			// if both are not null, then just 
			// return the comparision to the other
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
		result.concat(";");
		// postcondition
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
		
		String result = String.format(StorageStrings.UNFORMATTED_STRING_EVENT,
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
		// uid | type | title | ddate | dtime | done | tags
		@SuppressWarnings("unchecked")
		ArrayList<String> taglist = (ArrayList<String>) mData.get(Keys.HASHTAGS);
		assert (taglist != null);
		String tagsString = getTagString(taglist);
		Calendar dueCal = (Calendar) mData.get(Keys.DUE);
		
		String result = String.format(StorageStrings.UNFORMATTED_STRING_TASK,
				mData.get(Keys.UID),
				mType.toString(),
				mData.get(Keys.TITLE),
				dueCal.get(Calendar.DAY_OF_MONTH),
				dueCal.get(Calendar.MONTH) + 1, // add 1 to offset 0-basing in cal object
				dueCal.get(Calendar.YEAR),
				dueCal.get(Calendar.HOUR_OF_DAY),
				dueCal.get(Calendar.MINUTE), 
				mData.get(Keys.DONE),
				tagsString);
		return result;
	}
	
	private String makePlanString() {
		// uid | type | title | done | tags
		@SuppressWarnings("unchecked")
		ArrayList<String> taglist = (ArrayList<String>) mData.get(Keys.HASHTAGS);
		assert (taglist != null);
		String tagsString = getTagString(taglist);
		
		String result = String.format(StorageStrings.UNFORMATTED_STRING_PLAN,
				mData.get(Keys.UID),
				mType.toString(),
				mData.get(Keys.TITLE),
				mData.get(Keys.DONE),
				tagsString);
		return result;
	}
}
