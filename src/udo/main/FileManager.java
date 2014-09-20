package udo.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import udo.util.ItemData;

public class FileManager {
	
	public static final String FILENAME = "uDo_data.txt";
	
	private BufferedReader mReader;
	private String mNextLine;
	
	public FileManager() {
		mNextLine = null;
	}
	
	public boolean openFile() {
		try {
			mReader = new BufferedReader(new FileReader(FILENAME));
			mNextLine = mReader.readLine();
		} catch (FileNotFoundException e) {
			// if there's no existing file, create the file.
			// then try opening it again.
			createNewFile(FILENAME);
			openFile();
		} catch (IOException e) {
			// for the nextline function
			return false;
		}
		return true;
	}

	protected boolean createNewFile(String filename) {
		try {
			new FileWriter(filename).close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean hasNext() {
		boolean hasNext = (mNextLine != null);
		return hasNext;
	}

	public ItemData getNextItem() throws IOException {
		String line = getNextLine();
		return getItemData(line);
	}
	
	public String getNextLine() throws IOException {
		String nextLine = mNextLine;
		mNextLine = mReader.readLine();
		return nextLine;
	}

	private ItemData getItemData(String line) {
		// need to parse the item data from the file.
		if (line == null) {
			return null;
		}
		
		return null;
	}
}
