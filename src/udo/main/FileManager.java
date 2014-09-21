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

	private boolean createNewFile(String filename) {
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
		if (hasNext()) {
			String line = getNextLine();
			ItemData data = getItemData(line);
			return data;
		} else {
			return null;
		}
	}
	
	public String getNextLine() throws IOException {
		String nextLine = mNextLine;
		mNextLine = mReader.readLine();
		return nextLine;
	}

	public ItemData getItemData(String line) {
		String[] lineArray = getStringArray(line);
		//System.out.println(Arrays.toString(lineArray));
		
		ItemData item = new ItemData();
		
		// we assume every field is filled up, correctly.
		for (int i = 0; i < lineArray.length; i++) {
			switch (i) {
				case 0 :
					item.put("type", lineArray[i]);
					break;
				case 1 :
					item.put("title", lineArray[i]);
					break;
				case 2 :
					item.put("date", lineArray[i]);
					break;
				case 3 :
					item.put("start time", lineArray[i]);
					break;
				case 4 :
					item.put("end time", lineArray[i]);
					break;
				case 5 :
					item.put("tags", lineArray[i]);
					break;
				default:
					return null;
					// but this shudnt happen
			}
		}
		
		return item;
	}
	
	private String[] getStringArray(String str) {
		return str.split(SEPARATOR_REGEX);
	}
}
