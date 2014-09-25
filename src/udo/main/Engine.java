package udo.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import udo.util.engine.Cache;
import udo.util.engine.FileManager;
import udo.util.engine.RecycleBin;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;

public class Engine {
	
	private FileManager mFileManager;
	private Cache mCache;
	private RecycleBin mRecycleBin;
	
	public Engine() {
		mFileManager = new FileManager();
		mCache = new Cache();
		mRecycleBin = new RecycleBin();
	}
	
	//****** public methods ******//

	public boolean loadFile() {
		mCache.clear();
		try {
			ArrayList<ItemData> itemsFromFile = mFileManager.getFromFile();
			mCache.addAll(itemsFromFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public OutputData execute(InputData input) {
		Command cmd = input.getCommand();
		// decide what function to run.
		switch (cmd) {
			case ADD_EVENT :
				return runAddEvent(input);
			case LIST :
				return runList(input);
			case DELETE :
				return runDelete(input);
			case UNDO :
				return runUndo(input);
			case SAVE :
				return runSave(input);
			case EXIT :
				return runExit(input);
			default:
				return null;
		}
	}
	
	// ********* methods that execute the commands ******* //
	
	private OutputData runAddEvent(InputData inputData) {
		ItemData event = new ItemData(ItemType.EVENT);
		// extract data from inputdata to make an event
		// assume all data fields are present
		event.put(Keys.UID,
				mCache.generateUID());
		
		event.put(Keys.TITLE,
				inputData.get(Keys.TITLE));
		
		event.put(Keys.START,
				inputData.get(Keys.START));
		
		event.put(Keys.END,
				inputData.get(Keys.END));
		
		event.put(Keys.HASHTAGS,
				inputData.get(Keys.HASHTAGS));
		
		OutputData output;
		
		boolean addOK = mCache.add(event);
		Command cmd = inputData.getCommand();
		if (addOK) {
			// if added item successfully
			// make output object with the event data inside
			output = new OutputData(cmd, ExecutionStatus.SUCCESS);
			output.put(Keys.ITEM, event);
		} else {
			output = new OutputData(cmd, ExecutionStatus.FAIL);
		}
		
		return output;
	}
	
	private OutputData runList(InputData input) {
		/*
		 * 1. build a list of all items
		 * 2. select only the items we want.
		 * 3. sort the items according to date/time
		 * 4. put the items in the outputdata.
		 * 5. return output
		 */
		ArrayList<ItemData> listOfAllItems = mCache.getAllItems();
		ArrayList<ItemData> result;
		
		ListQuery query = (ListQuery) input.get(Keys.QUERY);
		
		switch (query) {
			case ALL :
				result = listOfAllItems;
				break;
			case SINGLE_HASHTAG :
				String tag = (String) input.get(Keys.HASHTAG);
				result = trimList(listOfAllItems, tag);
				break;
			default :
				return null;
		}
		
		OutputData output = new OutputData(Command.LIST, ExecutionStatus.SUCCESS);
		output.put(Keys.ITEMS, result);
		
		return output;
	}
	
	private OutputData runDelete(InputData inputData) {
		int uid = (int) inputData.get(Keys.UID);
		boolean deleteOK = mCache.delete(uid);
		OutputData output;
		if (deleteOK) {
			output = new OutputData(Command.DELETE, ExecutionStatus.SUCCESS);
		} else {
			output = new OutputData(Command.DELETE, ExecutionStatus.FAIL);
		}
		
		return output;
	}
	
	private OutputData runUndo(InputData inputData) {
		
		return null;
	}

	private OutputData runSave(InputData inputData) {
		boolean writeOK = writeCacheToFile();
		if (!writeOK) {
			return null;
		}
		OutputData output = new OutputData(Command.SAVE, ExecutionStatus.SUCCESS);
		return output;
	}
	
	private OutputData runExit(InputData inputData) {
		runSave(null);
		OutputData output = new OutputData(Command.EXIT, ExecutionStatus.SUCCESS);
		return output;
	}
	
	// ********* private abstracted helper methods ******* // 
	
	private boolean writeCacheToFile() {
		/*boolean filePrepared = mFileManager.startWriteMode();
		boolean cacheLocked = mCache.lock();
		if (!filePrepared || !cacheLocked) {
			return false;
		}
		
		while (mCache.hasNextItem()) {
			ItemData item = mCache.getNextItem();
			mFileManager.write(item);
		}
		
		mFileManager.closeWriteMode();*/
		mFileManager.writeToFile(mCache.getAllItems());
		return true;
	}

	private ArrayList<ItemData> trimList(ArrayList<ItemData> list, String tag) {
		ArrayList<ItemData> result = new ArrayList<ItemData>();
		for (ItemData item : list) {
			@SuppressWarnings("unchecked")
			ArrayList<String> tags = (ArrayList<String>) item.get(Keys.HASHTAGS);
			if (tags.contains(tag)) {
				result.add(item);
			}
		}
		Collections.sort(result);
		return result;
	}
}
