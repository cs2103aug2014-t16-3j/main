package udo.main;

import java.util.HashMap;
import java.util.Iterator;

import udo.util.ItemData;

public class Cache {
	
	private HashMap<String, ItemData> mEvents;
	private HashMap<String, ItemData> mTasks;
	private HashMap<String, ItemData> mPlans;
	private boolean mIsLocked;
	private Iterator<ItemData> mEventsIterator;
	private Iterator<ItemData> mTasksIterator;
	private Iterator<ItemData> mPlansIterator;
	
	public Cache() {
		mEvents = new HashMap<String, ItemData>();
		mTasks = new HashMap<String, ItemData>();
		mPlans = new HashMap<String, ItemData>();
		mIsLocked = false;
	}

	public boolean addItem(ItemData item) {
		String type = (String) item.get("type");
		
		if (type != null && type.toLowerCase().equals("event")) {
			String name = (String) item.get("title");
			mEvents.put(name, item);
		} else {
			return false;
		}
		
		return true;
	}
	
	public int size() {
		int eventSize = mEvents.size();
		int taskSize = mTasks.size();
		int planSize = mPlans.size();
		int totalSize = eventSize + taskSize + planSize;
		return totalSize;
	}
	
	public boolean isLocked() {
		return mIsLocked;
	}
	
	public boolean lock() {
		mIsLocked = true;
		mEventsIterator = mEvents.values().iterator();
		mTasksIterator = mTasks.values().iterator();
		mPlansIterator = mPlans.values().iterator();
		return true;
	}
	
	public boolean hasNextItem() {
		if (!mIsLocked) {
			return false;
		} else {
			return mEventsIterator.hasNext() ||
					mTasksIterator.hasNext() ||
					mPlansIterator.hasNext();
		}
	}
	
	public ItemData getNextItem() {
		if (!mIsLocked) {
			return null;
		}
		if (mEventsIterator.hasNext()) {
			return mEventsIterator.next();
		} else if (mTasksIterator.hasNext()) {
			return mTasksIterator.next();
		} else if (mPlansIterator.hasNext()) {
			return mPlansIterator.next();
		} else {
			return null;
		}
	}
	
	public boolean unlock() {
		mIsLocked = false;
		mEventsIterator = null;
		mTasksIterator = null;
		mPlansIterator = null;
		return true;
	}
	
	public void clear() {
		mEvents.clear();
		mTasks.clear();
		mPlans.clear();
	}
	
}
