package udo.util;

import java.util.HashMap;


/**
 * This class carries information from the engine to the UI.
 * The information contained in this class is all the info 
 * that the UI needs to display a message that corresponds to
 * the execution of the command.
 * <p>
 * For example, if the engine executed the addEvent() method,
 * the OutputData object will contain the command executed
 * (ADD_EVENT), the status of the execution (SUCCESS or FAIL),
 * and any additional information regarding the event that was
 * added. 
 * <p>
 * The status and command are accessed directly but any other 
 * additional details will be stored in a hash table and only
 * accessed by a key.
 * 
 */
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
	 * Retrieves the {@code Command} value
	 * @return the {@code Command} value
	 */
	public Command getCommand() {
		return mCommand;
	}

	/**
	 * Retrieves the {@code Status} value
	 * @return the {@code Status} value
	 */
	public Status getStatus() {
		return mStatus;
	}
	
	/**
	 * Associates the specified item with the specified name in the OutputData object.
	 * An existing item of the same name will be replaced. 
	 * The item inserted cannot be {@code null}.
	 * @param name The name of the item.
	 * @param item The item to be put inside the OutputData object.
	 * @return {@code true} when the operation is successful, 
	 * or {@code false} when the inserted item is null
	 */
	public boolean put(String name, Object item) {
		if (item != null) {
			mData.put(name, item);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Retrieves the item that is associated with the name.
	 * @param name The name of the item to retrieve
	 * @return The item, or {@code null} if the name is not mapped.
	 */
	public Object get(String name) {
		if (mData.containsKey(name)) {
			return mData.get(name);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a boolean value describing if the named item exists inside.
	 * @param name The name of the item to check.
	 * @return {@code true} if the item exists, or {@code false} otherwise.
	 */
	public boolean contains(String name) {
		return mData.containsKey(name);
	}
}
