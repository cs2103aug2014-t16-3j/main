package udo.util.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import udo.util.shared.Constants.Indices;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.StorageStrings;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

public class FileManager {
	
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
			mReader = new BufferedReader(new FileReader(StorageStrings.FILENAME));
			String nextLine = mReader.readLine();
			mNextItem = getItemData(nextLine);
		} catch (FileNotFoundException e) {
			// if there's no existing file, create the file.
			// then try opening it again.
			createNewFile(StorageStrings.FILENAME);
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

	private ItemData getItemData(String line) {
		if (line == null) {
			return null;
		}
		if (line.equals("")) {
			return null;
		}
		String[] lineArray = getStringArray(line);
		/*
		 * event
		 * [0uid, 1type, 2title, 3stdate, 4stime, 5endate, 6entime, 7<tags>]
		 */
		
		ItemType type = getItemType(lineArray[Indices.TYPE]);
		
		ItemData item = new ItemData(type);

		int uid = Integer.parseInt(lineArray[Indices.UID]);
		item.put(Keys.UID,
				uid);
		
		item.put(Keys.TITLE,
				lineArray[Indices.TITLE]);
		
		if (type.equals(ItemType.EVENT)) {
			
			String startDate = lineArray[Indices.START_DATE];
			String startTime = lineArray[Indices.START_TIME];
			Calendar startCal = getCalendar(startDate, startTime);
			item.put(Keys.START,
					startCal);
			
			String endDate = lineArray[Indices.END_DATE];
			String endTime = lineArray[Indices.END_TIME];
			Calendar endCal = getCalendar(endDate, endTime);
			item.put(Keys.END,
					endCal);
			
			String tagsString = lineArray[Indices.HASHTAGS];
			ArrayList<String> tagsList = getList(tagsString);
			item.put(Keys.HASHTAGS,
					tagsList);
			
		} else if (type.equals(ItemType.TASK)) {
			
		} else if (type.equals(ItemType.PLAN)) {
			
		} else {
			
		}
		
		return item;
	}
	
	private String[] getStringArray(String str) {
		return str.split(StorageStrings.FIELD_DELIMITER);
	}
	
	private ItemType getItemType(String typeString) {
		if (typeString == null) {
			return null;
		} else if (typeString.equals(StorageStrings.TYPE_EVENT)) {
			return ItemType.EVENT;
		} else {
			return null;
		}
	}
	
	private Calendar getCalendar(String date, String time) {
		Calendar cal = Calendar.getInstance();
		// parse date and time
		String[] timeArray = time.split(StorageStrings.TIME_DELIMITER);
		String[] dateArray = date.split(StorageStrings.DATE_DELIMITER);
		int day = Integer.parseInt(dateArray[0]);
		int month = Integer.parseInt(dateArray[1]) - 1; // convert from 1-based to 0-based for calendar
		int year = Integer.parseInt(dateArray[2]);
		int hour = Integer.parseInt(timeArray[0]);
		int minute = Integer.parseInt(timeArray[1]);
		cal.set(year, month, day, hour, minute);
		return cal;
	}
	
	private ArrayList<String> getList(String tagsString) {
		ArrayList<String> list = new ArrayList<String>();
		String[] tagsArray = tagsString.split(",");
		for (int i = 0; i < tagsArray.length; i++) {
			list.add(tagsArray[i]);
		}
		return list;
	}

	public boolean startWriteMode() {
		try {
			// will overwrite the current file with the new data.
			mWriter = new BufferedWriter(new FileWriter(StorageStrings.FILENAME));
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
