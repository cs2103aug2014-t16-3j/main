//@author Nicholas
package udo.util.engine;

import java.util.ArrayList;
import java.util.Calendar;
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
	
	public ArrayList<ItemData> getAllEvents() {
		ArrayList<ItemData> allEvents = new ArrayList<ItemData>();
		for (ItemData item : getAllItems()) {
			ItemType itemType = item.getItemType();
			if (itemType.equals(ItemType.EVENT)) {
				allEvents.add(item);
			}
		}
		Collections.sort(allEvents);
		return allEvents;
	}
	
	public ArrayList<ItemData> getAllEventsOn(Calendar date) {
		ArrayList<ItemData> allEvents = new ArrayList<ItemData>();
		for (ItemData item : getAllEvents()) {
			if (sameDate(item, date)) {
				allEvents.add(item);
			}
		}
		Collections.sort(allEvents);
		return allEvents;
	}
	
	private boolean sameDate(ItemData item, Calendar date) {
		Calendar itemCal;
		ItemType itemType = item.getItemType();
		if (itemType.equals(ItemType.EVENT)) {
			itemCal = (Calendar) item.get(Keys.START);
		} else if (itemType.equals(ItemType.TASK)) {
			itemCal = (Calendar) item.get(Keys.DUE);
		} else {
			return false;
		}
		return sameDate(itemCal, date);
	}
	
	private boolean sameDate(Calendar c1, Calendar c2) {
		int c1Day = c1.get(Calendar.DAY_OF_MONTH);
		int c1Month = c1.get(Calendar.MONTH);
		int c1Year = c1.get(Calendar.YEAR);
		int c2Day = c2.get(Calendar.DAY_OF_MONTH);
		int c2Month = c2.get(Calendar.MONTH);
		int c2Year = c2.get(Calendar.YEAR);
		if (c1Day != c2Day) {
			return false;
		}
		if (c1Month != c2Month) {
			return false;
		}
		if (c1Year != c2Year) {
			return false;
		}
		return true;
	}
	
	public ArrayList<ItemData> getAllTodo() {
		ArrayList<ItemData> allTasksAndPlans = new ArrayList<ItemData>();
		allTasksAndPlans.addAll(getAllTasks());
		allTasksAndPlans.addAll(getAllPlans());
		Collections.sort(allTasksAndPlans);
		return allTasksAndPlans;
	}
	
	public ArrayList<ItemData> getAllTodoBetween(Calendar from, Calendar to) {
		
		return null;
	}
	
	public ArrayList<ItemData> getAllTasks() {
		ArrayList<ItemData> allTasks = new ArrayList<ItemData>();
		for (ItemData item : getAllItems()) {
			ItemType itemType = item.getItemType();
			if (itemType.equals(ItemType.TASK)) {
				allTasks.add(item);
			}
		}
		Collections.sort(allTasks);
		return allTasks;
	}
	
	public ArrayList<ItemData> getAllPlans() {
		ArrayList<ItemData> allPlans = new ArrayList<ItemData>();
		for (ItemData item : getAllItems()) {
			ItemType itemType = item.getItemType();
			if (itemType.equals(ItemType.PLAN)) {
				allPlans.add(item);
			}
		}
		Collections.sort(allPlans);
		return allPlans;
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

	public void clear() {
		if (isLocked()) {
			return;
		}
		mEvents.clear();
		mTasks.clear();
		mPlans.clear();
	}

	public int generateUID() {
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
