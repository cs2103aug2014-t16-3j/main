package udo.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import udo.util.ItemData;

public class FileManager {
	
	public static final String FILENAME = "uDo_data.txt";
	
	BufferedReader reader;
	
	public FileManager() {
		
	}
	
	public boolean openFile() {
		try {
			reader = new BufferedReader(new FileReader(FILENAME));
		} catch (FileNotFoundException e) {
			// when there's no file, create the file.
			try {
				new FileWriter(FILENAME).close();;
			} catch (IOException e1) {
				return false;
			}
		}
		return true;
	}

	public ItemData getNextItem() throws IOException {
		String line = reader.readLine();
		return getItemData(line);
	}

	private ItemData getItemData(String line) {
		// need to parse the item data from the file.
		return null;
	}
}
