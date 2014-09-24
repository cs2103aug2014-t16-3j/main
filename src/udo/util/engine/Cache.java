package udo.util.engine;

import java.util.HashSet;
import java.util.Iterator;

import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

public class Cache {
	
	private HashSet<ItemData> mEvents;
	private HashSet<ItemData> mTasks;
	private HashSet<ItemData> mPlans;
	private boolean mIsLocked;
	private Iterator<ItemData> mEventsIterator;
	private Iterator<ItemData> mTasksIterator;
	private Iterator<ItemData> mPlansIterator;
	
	public Cache() {
		mEvents = new HashSet<ItemData>();
		mTasks = new HashSet<ItemData>();
		mPlans = new HashSet<ItemData>();
		mIsLocked = false;
	}

	public boolean addItem(ItemData item) {
		if (isLocked()) {
			return false;
		}
		
		ItemType type = item.getItemType();
		switch (type) {
			case EVENT :
				mEvents.add(item);
				return true;
			default :
				return false;
		}
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
		mEventsIterator = mEvents.iterator();
		mTasksIterator = mTasks.iterator();
		mPlansIterator = mPlans.iterator();
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
		if (isLocked()) {
			return;
		}
		mEvents.clear();
		mTasks.clear();
		mPlans.clear();
	}

	public int generateUID() {
		// TODO 
		return 0;
	}
	
}
