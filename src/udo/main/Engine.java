package udo.main;

import java.io.IOException;
import java.util.Set;

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
	
	public OutputData execute(InputData input) {
		Command cmd = input.getCommand();
		// decide what function to run.
		switch (cmd) {
			case ADD_EVENT :
				return runAddEvent(input);
			case SAVE :
				return runSave();
			default:
				return null;
		}
	}

	public boolean loadFile() {
		mFileManager.openFile();
		try {
			ItemData id = mFileManager.getNextItem();
			while (id != null) {
				mCache.addItem(id);
				id = mFileManager.getNextItem();
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	private OutputData runSave() {
		boolean writeOK = writeCacheToFile();
		if (writeOK) {
			return null;
		}
		return null;
	}
	
	private boolean writeCacheToFile() {
		boolean filePrepared = mFileManager.startWriteMode();
		if (filePrepared) {
			mCache.lock();
			
			
			mFileManager.closeWriteMode();
		}
		return false;
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
}
