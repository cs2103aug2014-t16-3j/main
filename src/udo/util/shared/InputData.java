package udo.util.shared;

import java.util.HashMap;
import java.util.Set;

/**
 * This class carries information parsed from the input string. This class
 * should contain information that allows the Engine to decide what to do, and
 * any additional information that the Engine requires to carry it out.
 * 
 */
public class InputData {

	private Command mCommand;
	private ParsingStatus mParsingStatus;
	private HashMap<String, Object> mData;

	public InputData(Command cmd) {
		mCommand = cmd;
		mData = new HashMap<String, Object>();
	}
	
	public InputData(Command cmd, ParsingStatus status) {
		mCommand = cmd;
		mParsingStatus = status;
		mData = new HashMap<String, Object>();
	}

	/**
	 * gets the command
	 * 
	 * @return the <code>Command</code>
	 */
	public Command getCommand() {
		return mCommand;
	}

	/**
	 * Associates the specified item with the specified key in the OutputData
	 * object. An existing item of the same key will be replaced. The item
	 * inserted cannot be {@code null}.
	 * 
	 * @param key
	 * @param data
	 * @return true when successful, false when object is null
	 */
	public boolean put(String key, Object data) {
		if (data != null) {
			mData.put(key, data);
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
		return mData.get(key);
	}

	/**
	 * Returns a boolean value describing if the named item exists inside.
	 * 
	 * @param key
	 *            The key of the item to check.
	 * @return {@code true} if the item exists, or {@code false} otherwise.
	 */
	public Set<String> getNames() {
		return mData.keySet();
	}
	
	/**
	 * Sets the {@code ParsingStatus} value
	 * 
	 * @return the {@code ParsingStatus} value
	 */
	public void setParsingStatus(ParsingStatus status) {
		mParsingStatus = status;
	}
	
	/**
	 * Returns the {@code ParsingStatus} value
	 * 
	 * @return the {@code ParsingStatus} value
	 */
	public ParsingStatus getStatus() {
		return mParsingStatus;
	}
}
