package udo.main;

import java.io.IOException;
import java.util.Set;

import udo.util.Command;
import udo.util.InputData;
import udo.util.ItemData;
import udo.util.OutputData;
import udo.util.Status;

public class Engine {
	private FileManager fileManager;
	private Cache cache;
	private RecycleBin recycleBin;
	
	public Engine() {
		fileManager = new FileManager();
		cache = new Cache();
		recycleBin = new RecycleBin();
	}
	
	public OutputData execute(InputData input) {
		Command cmd = input.getCommand();
		// decide what function to run.
		switch (cmd) {
			case ADD_EVENT :
				return addEvent(input);
			case SAVE :
				return 
			default:
				return null;
		}
	}

	public boolean loadFile() {
		fileManager.openFile();
		try {
			ItemData id = fileManager.getNextItem();
			while (id != null) {
				cache.addItem(id);
				id = fileManager.getNextItem();
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	private OutputData saveData() {
		
		return null;
	}
	
	private boolean writeCache() {
		
		
		return false;
	}
	
	private OutputData addEvent(InputData input) {
		Command cmd = input.getCommand();
		
		ItemData event = new ItemData();
		
		// extract data from inputdata to make an event
		for (String name : input.getNames()) {
			Object info = input.get(name);
			event.put(name, info);
		}
		
		OutputData output;
		
		boolean addOK = cache.addItem(event);
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
