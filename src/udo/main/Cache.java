package udo.main;

import java.util.HashMap;

import udo.util.ItemData;

public class Cache {
	
	HashMap<String, ItemData> mEventCache;

	public boolean addItem(ItemData item) {
		String type = (String) item.get("item type"); 
		if (type != null && type.equals("Event")) {
			String name = (String) item.get("name");
			mEventCache.put(name, item);
		}
		return true;
	}
	
}
