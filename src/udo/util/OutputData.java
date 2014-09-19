package udo.util;

import java.util.HashMap;

public class OutputData {
	Command mCommand;
	Status mStatus;
	HashMap<String, Object> mData;
	
	public OutputData(Command cmd, Status status) {
		mCommand = cmd;
		mStatus = status;
		mData = new HashMap<String, Object>();
	}
	
	/**
	 * Associates the specified item with the specified name in the OutputData object.
	 * An existing item of the same name will be replaced.
	 * @param name The name of the item.
	 * @param item The item to be put inside the OutputData object.
	 * @return {@code true} when the operation is successful
	 */
	public boolean put(String name, Object item) {
		mData.put(name, item);
		return true;
	}
	
	public Object get(String name) {
		return mData.get(name);
	}
}
