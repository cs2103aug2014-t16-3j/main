package udo.util.shared;

import java.util.HashMap;

/**
 * This class carries information from the engine to the UI. The information
 * contained in this class is all the info that the UI needs to display a
 * message that corresponds to the execution of the command.
 * <p>
 * For example, if the engine executed the addEvent() method, the OutputData
 * object will contain the command executed (ADD_EVENT), the status of the
 * execution (SUCCESS or FAIL), and any additional information regarding the
 * event that was added.
 * <p>
 * The status and command are accessed directly but any other additional details
 * will be stored in a hash table and only accessed by a key.
 * 
 */
public class OutputData {

	Command mCommand;
	ParsingStatus mParsingStatus;
	ExecutionStatus mExecutionStatus;
	HashMap<String, Object> mData;
	
	public OutputData(Command cmd) {
		mCommand = cmd;
		mData = new HashMap<String, Object>();
	}
	
	public OutputData(Command cmd, ParsingStatus pStatus) {
		mCommand = cmd;
		mData = new HashMap<String, Object>();
		mParsingStatus = pStatus;
	}

	public OutputData(Command cmd, ParsingStatus pStatus, ExecutionStatus eStatus) {
		mCommand = cmd;
		mData = new HashMap<String, Object>();
		mParsingStatus = pStatus;
		mExecutionStatus = eStatus;
	}

	/**
	 * Retrieves the {@code Command} value
	 * 
	 * @return the {@code Command} value
	 */
	public Command getCommand() {
		return mCommand;
	}
	
	/**
	 * Retrieves the {@code ExecutionStatus} value
	 * 
	 * @return the {@code ExecutionStatus} value
	 */
	public void setParsingStatus(ParsingStatus status) {
		mParsingStatus = status;
	}
	
	/**
	 * Retrieves the {@code ExecutionStatus} value
	 * 
	 * @return the {@code ExecutionStatus} value
	 */
	public void setExecutionStatus(ExecutionStatus status) {
		mExecutionStatus = status;
	}
	
	/**
	 * Retrieves the {@code ExecutionStatus} value
	 * 
	 * @return the {@code ExecutionStatus} value
	 */
	public ParsingStatus getParsingStatus() {
		return mParsingStatus;
	}

	/**
	 * Retrieves the {@code ExecutionStatus} value
	 * 
	 * @return the {@code ExecutionStatus} value
	 */
	public ExecutionStatus getExecutionStatus() {
		return mExecutionStatus;
	}

	/**
	 * Associates the specified item with the specified key in the OutputData
	 * object. An existing item of the same key will be replaced. The item
	 * inserted cannot be {@code null}.
	 * 
	 * @param key
	 *            The key of the item.
	 * @param item
	 *            The item to be put inside the OutputData object.
	 * @return {@code true} when the operation is successful, or {@code false}
	 *         when the inserted item is null
	 */
	public boolean put(String key, Object item) {
		if (item != null) {
			mData.put(key, item);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves the item that is associated with the key.
	 * 
	 * @param key
	 *            The key of the item to retrieve
	 * @return The item, or {@code null} if the key is not mapped.
	 */
	public Object get(String key) {
		if (mData.containsKey(key)) {
			return mData.get(key);
		} else {
			return null;
		}
	}

	/**
	 * Returns a boolean value describing if the named item exists inside.
	 * 
	 * @param key
	 *            The key of the item to check.
	 * @return {@code true} if the item exists, or {@code false} otherwise.
	 */
	public boolean contains(String key) {
		return mData.containsKey(key);
	}
}
