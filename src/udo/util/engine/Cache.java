package udo.util.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import udo.util.shared.Constants.Keys;
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

	private HashSet<Integer> mUIDs;

	public Cache() {
		mEvents = new HashSet<ItemData>();
		mTasks = new HashSet<ItemData>();
		mPlans = new HashSet<ItemData>();
		mIsLocked = false;
		mUIDs = new HashSet<Integer>();
	}

	public boolean addAll(ArrayList<ItemData> list) {
		for (ItemData item : list) {
			add(item);
		}
		return true;
	}

	public boolean add(ItemData item) {
		if (isLocked()) {
			return false;
		}
		trackUID(item);
		ItemType type = item.getItemType();
		switch (type) {
		case EVENT:
			mEvents.add(item);
			return true;
		default:
			return false;
		}
	}

	public ItemData getItem(int uid) {
		ArrayList<ItemData> items = getAllItems();
		ItemData result = null;
		for (ItemData item : items) {
			int itemUid = (int) item.get(Keys.UID);
			if (itemUid == uid) {
				result = item;
				break;
			}
		}
		return result;
	}

	public boolean deleteItem(int uid) {
		// TODO
		ArrayList<ItemData> items = getAllItems();
		ItemData toDelete = null;
		for (ItemData item : items) {
			int itemUid = (int) item.get(Keys.UID);
			if (itemUid == uid) {
				toDelete = item;
				break;
			}
		}
		if (toDelete != null) {
			ItemType type = toDelete.getItemType();
			switch (type) {
			case EVENT:
				mEvents.remove(toDelete);
				break;
			case TASK:
				mTasks.remove(toDelete);
				break;
			case PLAN:
				mPlans.remove(toDelete);
				break;
			default:
				return false;
			}
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

	public ArrayList<ItemData> getAllItems() {
		lock();
		ArrayList<ItemData> allItems = new ArrayList<ItemData>();
		while (hasNextItem()) {
			allItems.add(getNextItem());
		}
		unlock();
		Collections.sort(allItems);
		return allItems;
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
		Random r = new Random(System.currentTimeMillis());
		int uid = 10000 + r.nextInt(90000);
		if (mUIDs.contains(uid)) {
			return generateUID();
		} else {
			return uid;
		}
	}

	private boolean isLocked() {
		return mIsLocked;
	}

	private boolean lock() {
		mIsLocked = true;
		mEventsIterator = mEvents.iterator();
		mTasksIterator = mTasks.iterator();
		mPlansIterator = mPlans.iterator();
		return true;
	}

	private boolean hasNextItem() {
		if (!mIsLocked) {
			return false;
		} else {
			return mEventsIterator.hasNext() || mTasksIterator.hasNext()
					|| mPlansIterator.hasNext();
		}
	}

	private ItemData getNextItem() {
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

	private boolean unlock() {
		mIsLocked = false;
		mEventsIterator = null;
		mTasksIterator = null;
		mPlansIterator = null;
		return true;
	}

	private void trackUID(ItemData item) {
		Integer uid = (Integer) item.get(Keys.UID);
		mUIDs.add(uid);
	}

}
