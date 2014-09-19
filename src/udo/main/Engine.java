package udo.main;

import java.io.IOException;

public class Engine {
	FileManager fileManager;
	Cache cache;
	RecycleBin recycleBin;
	
	public Engine() {
		fileManager = new FileManager();
		cache = new Cache();
		recycleBin = new RecycleBin();
	}
	
	boolean loadFile() {
		fileManager.openFile();
		try {
			fileManager.getNextItem();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
