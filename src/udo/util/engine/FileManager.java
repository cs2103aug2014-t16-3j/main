package udo.util.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import udo.util.shared.ItemData;

public class FileManager {
	
	private static final String FILENAME = "uDo_data.txt";
	private static final String SEPARATOR_REGEX = "\\|\\|\\|";
	
	private BufferedReader mReader;
	private String mNextLine;
	private ItemData mNextItem;
	private boolean mIsReading;
	private BufferedWriter mWriter;
	private boolean mIsWriting;
	
	public FileManager() {
		mNextLine = null;
		mIsReading = false;
		mIsWriting = false;
	}
	
	public boolean startReadMode() {
		if (isWriting()) {
			return false;
		}
		try {
			mReader = new BufferedReader(new FileReader(FILENAME));
			String nextLine = mReader.readLine();
			mNextItem = getItemData(nextLine);
		} catch (FileNotFoundException e) {
			// if there's no existing file, create the file.
			// then try opening it again.
			createNewFile(FILENAME);
			startReadMode();
		} catch (IOException e) {
			// if unable to read the nextline
			return false;
		}
		setReading(true);
		return true;
	}
	
	public boolean closeReadMode() {
		try {
			mReader.close();
		} catch (IOException e) {
			return false;
		}
		setReading(false);
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
	
	public boolean hasNextItem() {
		boolean hasNext = (mNextItem != null);
		return hasNext;
	}

	public ItemData getNextItem() throws IOException {
		if (hasNextItem()) {
			ItemData result = mNextItem;
			String nextLine = getNextLine();
			mNextItem = getItemData(nextLine);
			return result;
		} else {
			return null;
		}
	}
	
	private String getNextLine() throws IOException {
		return mReader.readLine();
	}

	public ItemData getItemData(String line) {
		if (line == null) {
			return null;
		}
		if (line.equals("")) {
			return null;
		}
		String[] lineArray = getStringArray(line);
		
		ItemData item = new ItemData();
		
		// TODO error checking
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

	public boolean startWriteMode() {
		try {
			// will overwrite the current file with the new data.
			mWriter = new BufferedWriter(new FileWriter(FILENAME));
		} catch (IOException e) {
			return false;
		}
		setWriting(true);
		return true;
	}
	
	public boolean closeWriteMode() {
		try {
			mWriter.close();
			mWriter = null;
		} catch (IOException e) {
			return false;
		}
		setWriting(true);
		return true;
	}

	public boolean write(ItemData item) {
		String itemString = item.toString();
		try {
			mWriter.append(itemString);
			mWriter.newLine();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public boolean isWriting() {
		return mIsWriting;
	}

	public void setWriting(boolean writing) {
		mIsWriting = writing;
	}

	public boolean isReading() {
		return mIsReading;
	}

	public void setReading(boolean reading) {
		mIsReading = reading;
	}
}
