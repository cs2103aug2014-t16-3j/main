package udo.main;

import java.io.IOException;
import java.util.ArrayList;

import udo.util.engine.Cache;
import udo.util.engine.FileManager;
import udo.util.engine.RecycleBin;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
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

	public boolean loadFile() {
		boolean readOK = mFileManager.startReadMode();
		if (!readOK) {
			return false;
		}
		try {
			mCache.clear();
			while (mFileManager.hasNextItem()) {
				ItemData item = mFileManager.getNextItem();
				mCache.addItem(item);
			}
		} catch (IOException e) {
			mCache.clear();
			return false;
		} finally {
			mFileManager.closeReadMode();
		}
		return true;
	}
	
	public OutputData execute(InputData input) {
		Command cmd = input.getCommand();
		// decide what function to run.
		switch (cmd) {
			case ADD_EVENT :
				return runAddEvent(input);
			case LIST :
				return runList(input);
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
		Command cmd = inputData.getCommand();
		
		ItemData event = new ItemData();
		// extract data from inputdata to make an event
		// assume all data fields are present
		event.put(Keys.UID, mCache.generateUID());
		event.put(Keys.TITLE, inputData.get(Keys.TITLE));
		event.put(Keys.START, inputData.get(Keys.START));
		event.put(Keys.END, inputData.get(Keys.END));
		event.put(Keys.HASHTAGS, inputData.get(Keys.HASHTAGS));
		
		OutputData output;
		
		boolean addOK = mCache.addItem(event);
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
	
	private OutputData runList(InputData inputData) {
		/*
		 * 1. build a list of all items
		 * 2. select only the items we want.
		 * 3. sort the items according to date/time
		 * 4. put the items in the outputdata.
		 * 5. return output
		 */
		mCache.lock();
		ArrayList<ItemData> listOfAllItems = new ArrayList<ItemData>();
		while (mCache.hasNextItem()) {
			listOfAllItems.add(mCache.getNextItem());
		}
		
		
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
		boolean filePrepared = mFileManager.startWriteMode();
		boolean cacheLocked = mCache.lock();
		if (!filePrepared || !cacheLocked) {
			return false;
		}
		
		while (mCache.hasNextItem()) {
			ItemData item = mCache.getNextItem();
			mFileManager.write(item);
		}
		
		mFileManager.closeWriteMode();
		return true;
	}
}
