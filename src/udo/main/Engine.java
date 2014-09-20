package udo.main;

import java.io.IOException;

import udo.util.Command;
import udo.util.InputData;
import udo.util.ItemData;
import udo.util.OutputData;

public class Engine {
	FileManager fileManager;
	Cache cache;
	RecycleBin recycleBin;
	
	public Engine() {
		fileManager = new FileManager();
		cache = new Cache();
		recycleBin = new RecycleBin();
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
			e.printStackTrace();
		}
		return true;
	}
	
	public OutputData execute(InputData id) {
		Command cmd = id.getCommand();
		// decide what other function to run.
		
		return null;
	}
}
