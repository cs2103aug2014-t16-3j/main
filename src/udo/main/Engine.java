package udo.main;

import java.io.IOException;

import udo.util.Command;
import udo.util.InputData;
import udo.util.ItemData;
import udo.util.OutputData;
import udo.util.Status;

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
			case SAVE :
				return runSave();
			case EXIT :
				return runExit();
			default:
				return null;
		}
	}
	
	// ********* methods that execute the commands ******* //
	
	private OutputData runSave() {
		boolean writeOK = writeCacheToFile();
		if (!writeOK) {
			return null;
		}
		OutputData output = new OutputData(Command.SAVE, Status.SUCCESS);
		return output;
	}
	
	private OutputData runAddEvent(InputData input) {
		Command cmd = input.getCommand();
		
		ItemData event = new ItemData();
		// extract data from inputdata to make an event
		for (String name : input.getNames()) {
			Object info = input.get(name);
			event.put(name, info);
		}
		
		OutputData output;
		
		boolean addOK = mCache.addItem(event);
		if (addOK) {
			// if added item successfully
			// make output object with the event data inside
			output = new OutputData(cmd, Status.SUCCESS);
			for (String name : event.getNames()) {
				Object info = event.get(name);
				output.put(name, info);
			}
		} else {
			output = new OutputData(cmd, Status.FAIL);
		}
		
		return output;
	}
	
	private OutputData runExit() {
		runSave();
		OutputData output = new OutputData(Command.EXIT, Status.SUCCESS);
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
