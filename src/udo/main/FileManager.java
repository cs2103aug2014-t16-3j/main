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
	String nextLine;
	
	public FileManager() {
		nextLine = null;
	}
	
	public boolean openFile() {
		try {
			reader = new BufferedReader(new FileReader(FILENAME));
			nextLine = getNextLine();
		} catch (FileNotFoundException e) {
			// if there's no existing file, create the file.
			// then try opening it again.
			createNewFile();
			openFile();
		} catch (IOException e) {
			// for the nextline function
			return false;
		}
		return true;
	}

	private void createNewFile() {
		try {
			new FileWriter(FILENAME).close();
		} catch (IOException e) {
			return;
		}
	}

	public ItemData getNextItem() throws IOException {
		String line = getNextLine();
		return getItemData(line);
	}
	
	public String getNextLine() throws IOException {
		return reader.readLine();
	}

	private ItemData getItemData(String line) {
		// need to parse the item data from the file.
		if (line == null) {
			return null;
		}
		
		return null;
	}
}
