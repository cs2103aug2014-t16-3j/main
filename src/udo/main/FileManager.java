package udo.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import udo.util.ItemData;

public class FileManager {
	
	private static final String FILENAME = "uDo_data.txt";
	private static final String SEPARATOR_REGEX = "\\|\\|\\|";
	
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
			// if unable to read the nextline
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
		ItemData data = getItemData(line);
		return data;
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
		String[] lineArray = getStringArray(line);
		System.out.println(Arrays.toString(lineArray));
		
		
		return null;
	}
	
	private String[] getStringArray(String str) {
		return str.split(SEPARATOR_REGEX);
	}
}
