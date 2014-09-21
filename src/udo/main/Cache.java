package udo.main;

import java.util.HashMap;

import udo.util.ItemData;

public class Cache {
	
	private HashMap<String, ItemData> mEvents;
	private HashMap<String, ItemData> mTasks;
	private HashMap<String, ItemData> mPlans;
	
	public Cache() {
		mEvents = new HashMap<String, ItemData>();
		mTasks = new HashMap<String, ItemData>();
		mPlans = new HashMap<String, ItemData>();
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
	
}
