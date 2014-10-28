//@author Nicholas
package udo.util.engine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import udo.util.exceptions.CacheAccessException;
import udo.util.exceptions.InvalidUIDException;
import udo.util.exceptions.ItemNotFoundException;
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
		mUIDs = new HashSet<Integer>();
		mIsLocked = false;
	}

	/**
	 * adds a list of items
	 * 
	 * @param list the list of items to add
	 * @throws CacheAccessException if adding one of the items failed
	 */
	public void addAll(ArrayList<ItemData> list) throws CacheAccessException {
		for (ItemData item : list) {
			addItem(item);
		}
	}

	/**
	 * adds an item
	 * 
	 * @param item the item to add
	 * @throws CacheAccessException if cache is locked when trying to add
	 */
	public void addItem(ItemData item) throws CacheAccessException {
		if (isLocked()) {
			throw new CacheAccessException("cache is locked");
		}
		
		trackUID(item);
		
		ItemType type = item.getItemType();
		switch (type) {
			case EVENT :
				mEvents.add(item);
				break;

			case TASK :
				mTasks.add(item);
				break;

			case PLAN :
				mPlans.add(item);
				break;

			default:

		}
	}
	
	/**
	 * gets an item by the uid
	 * 
	 * @param uid the uid of the item to get
	 * @return the item matching the uid
	 * @throws CacheAccessException if cache cannot be accessed
	 * @throws ItemNotFoundException if item not found
	 * @throws InvalidUIDException if uid invalid
	 */
	public ItemData getItem(int uid) throws CacheAccessException, ItemNotFoundException, InvalidUIDException {
		if (uid < 0) {
			throw new InvalidUIDException();
		}
		
		ArrayList<ItemData> items = getAllItems();
		ItemData result = null;
		for (ItemData item : items) {
			int itemUid = (int) item.get(Keys.UID);
			if (itemUid == uid) {
				result = item;
				break;
			}
		}
		
		if (result == null) {
			throw new ItemNotFoundException();
		}
		
		return result;
	}
	
	/**
	 * deletes an item via the uid
	 * 
	 * @param uid the uid of the item to delete
	 * @return the deleted item.
	 * @throws CacheAccessException if cache cannot be accessed
	 * @throws InvalidUIDException if uid wrong
	 * @throws ItemNotFoundException if item not found
	 */
	public ItemData deleteItem(int uid) throws CacheAccessException, InvalidUIDException, ItemNotFoundException {
		if (uid < 0) {
			throw new InvalidUIDException("negative number");
		}
		
		ArrayList<ItemData> items = getAllItems();
		ItemData toDelete = null;
		for (ItemData item : items) {
			int itemUid = (int) item.get(Keys.UID);
			if (itemUid == uid) {
				toDelete = item;
				break;
			}
		}
		
		if (toDelete == null) {
			throw new ItemNotFoundException();
		}

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
				throw new ItemNotFoundException();
		}

		return toDelete;
	}

	/**
	 * gives the size of the cache
	 * 
	 * @return the size
	 */
	public int size() {
		int eventSize = mEvents.size();
		int taskSize = mTasks.size();
		int planSize = mPlans.size();
		int totalSize = eventSize + taskSize + planSize;
		return totalSize;
	}

	/**
	 * clears all items in the cache.
	 * does NOT invoke the delete command. it's a simple clear.
	 * 
	 * @throws CacheAccessException if cache is locked when trying to clear
	 */
	public void clear() throws CacheAccessException {
		if (isLocked()) {
			throw new CacheAccessException();
		}
		mEvents.clear();
		mTasks.clear();
		mPlans.clear();
	}

	/**
	 * generates a random uid between 10000 and 99999
	 * @return a random uid between 10000 and 99999
	 */
	public int generateUID() {
		Random r = new Random(System.currentTimeMillis());
		int uid = 10000 + r.nextInt(90000);
		if (mUIDs.contains(uid)) {
			return generateUID();
		} else {
			return uid;
		}
	}

	public ArrayList<ItemData> getAllItemsOn(Calendar date) throws CacheAccessException {
		ArrayList<ItemData> allEvents = new ArrayList<ItemData>();
		for (ItemData item : getAllItems()) {
			if (isSameDateAs(item, date)) {
				allEvents.add(item);
			}
		}
		Collections.sort(allEvents);
		return allEvents;
	}
	
	public ArrayList<ItemData> getAllEventsOn(Calendar date) throws CacheAccessException {
		ArrayList<ItemData> allEvents = new ArrayList<ItemData>();
		for (ItemData item : getAllEvents()) {
			if (isSameDateAs(item, date)) {
				allEvents.add(item);
			}
		}
		Collections.sort(allEvents);
		return allEvents;
	}

	public ArrayList<ItemData> getAllTasksBetween(Calendar from, Calendar to) throws CacheAccessException {
		ArrayList<ItemData> allTasks = new ArrayList<ItemData>();
		for (ItemData item : getAllTasks()) {
			if (isBetweenDates(item, from, to)) {
				allTasks.add(item);
			}
		}
		Collections.sort(allTasks);
		return allTasks;
	}
	
	public ArrayList<ItemData> getAllItemsWithHashtag(String tag) throws CacheAccessException {
		ArrayList<ItemData> result = new ArrayList<ItemData>();
		for (ItemData item : getAllItems()) {
			@SuppressWarnings("unchecked")
			ArrayList<String> tags = (ArrayList<String>) item.get(Keys.HASHTAGS);
			if (tags.contains(tag)) {
				result.add(item);
			}
		}
		Collections.sort(result);
		return result;
	}

	public ArrayList<ItemData> getAllEvents() throws CacheAccessException {
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

	public ArrayList<ItemData> getAllTasks() throws CacheAccessException {
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
	
	public ArrayList<ItemData> getAllPlans() throws CacheAccessException {
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

	/**
	 * returns a list of both tasks and plans
	 * 
	 * @return a list of both tasks and plans
	 * @throws CacheAccessException if cache is locked 
	 */
	public ArrayList<ItemData> getAllTodo() throws CacheAccessException {
		ArrayList<ItemData> allTasksAndPlans = new ArrayList<ItemData>();
		allTasksAndPlans.addAll(getAllTasks());
		allTasksAndPlans.addAll(getAllPlans());
		Collections.sort(allTasksAndPlans);
		return allTasksAndPlans;
	}
	
	/**
	 * returns a list of items marked as done.
	 * items can only be of task or plan type.
	 * 
	 * @return the list of done items
	 * @throws CacheAccessException if cache is locked
	 */
	public ArrayList<ItemData> getAllDone() throws CacheAccessException {
		ArrayList<ItemData> allDone = new ArrayList<ItemData>();
		for (ItemData item : getAllTodo()) {
			if ((boolean) item.get(Keys.DONE)) {
				allDone.add(item);
			}
		}
		Collections.sort(allDone);
		return allDone;
	}

	/**
	 * returns a list of all the items
	 * 
	 * @return a list of all the items
	 * @throws CacheAccessException if the cache is locked
	 */
	public ArrayList<ItemData> getAllItems() throws CacheAccessException {
		if (isLocked()) {
			throw new CacheAccessException("cache not locked");
		}
		
		return collateItems();
	}
	
	/**
	 * locks cache, makes an arraylist of items, then unlocks cache.
	 * 
	 * @return the list of items in the cache at this point.
	 */
	private ArrayList<ItemData> collateItems() {
		lock();
		
		ArrayList<ItemData> allItems = new ArrayList<ItemData>();
		while (hasNextItem()) {
			allItems.add(getNextItem());
		}
		
		Collections.sort(allItems);
		
		unlock();
		return allItems;
	}

	private boolean isSameDateAs(ItemData item, Calendar date) {
		Calendar itemCal;
		ItemType itemType = item.getItemType();
		if (itemType == null) {
			return false;
		} else if (itemType.equals(ItemType.EVENT)) {
			itemCal = (Calendar) item.get(Keys.START);
		} else if (itemType.equals(ItemType.TASK)) {
			itemCal = (Calendar) item.get(Keys.DUE);
		} else {
			return false;
		}
		return isSameDateAs(itemCal, date);
	}

	private boolean isSameDateAs(Calendar c1, Calendar c2) {
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

	private boolean isBetweenDates(ItemData item, Calendar from, Calendar to) {
		Calendar itemCal;
		ItemType itemType = item.getItemType();
		if (itemType.equals(ItemType.EVENT)) {
			itemCal = (Calendar) item.get(Keys.START);
		} else if (itemType.equals(ItemType.TASK)) {
			itemCal = (Calendar) item.get(Keys.DUE);
		} else {
			return false;
		}
		return isBetweenDates(itemCal, from, to);
	}

	private boolean isBetweenDates(Calendar itemCal, Calendar from, Calendar to) {
		// compare from 0:00 to 23:59 of the date range
		from.set(Calendar.HOUR_OF_DAY, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		to.set(Calendar.HOUR_OF_DAY, 23);
		to.set(Calendar.MINUTE, 59);
		to.set(Calendar.SECOND, 59);
		
		if (itemCal.after(from) && itemCal.before(to)) {
			return true;
		} else {
			return false;
		}
	}

	private void lock() {
		mIsLocked = true;
		mEventsIterator = mEvents.iterator();
		mTasksIterator = mTasks.iterator();
		mPlansIterator = mPlans.iterator();
	}

	private void unlock() {
		mIsLocked = false;
		mEventsIterator = null;
		mTasksIterator = null;
		mPlansIterator = null;
	}

	private boolean isLocked() {
		return mIsLocked;
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

	private void trackUID(ItemData item) {
		Integer uid = (Integer) item.get(Keys.UID);
		mUIDs.add(uid);
	}

}
