package udo.util.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import udo.util.exceptions.WritingToStorageFailedException;
import udo.util.shared.Constants.StorageIndices;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.StorageStrings;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

public class FileManager {

	private String mFilename;
	
	private BufferedReader mReader;
	private ItemData mNextItem;
	private boolean mIsReading;
	
	private BufferedWriter mWriter;
	private boolean mIsWriting;

	public FileManager() {
		mFilename = StorageStrings.FILENAME;
		mIsReading = false;
		mIsWriting = false;
	}
	
	public FileManager(String filename) {
		mFilename = filename;
		mIsReading = false;
		mIsWriting = false;
	}

	public ArrayList<ItemData> getFromFile() throws IOException {
		startReadMode();
		if (!isReading()) {
			System.out.println("not readinf");
			return null;
		}
		ArrayList<ItemData> result = new ArrayList<ItemData>();
		while (hasNextItem()) {
			ItemData item = getNextItem();
			result.add(item);
		}
		closeReadMode();
		return result;
	}

	public boolean writeToFile(ArrayList<ItemData> list) throws WritingToStorageFailedException {
		startWriteMode();
		for (ItemData item : list) {
			write(item);
		}
		closeWriteMode();
		return true;
	}

	private boolean startReadMode() {
		if (isWriting()) {
			System.out.println("iswriting");
			return false;
		}
		try {
			mReader = new BufferedReader(
					new FileReader(mFilename));
			String nextLine = mReader.readLine();
			mNextItem = getItemData(nextLine);
		} catch (FileNotFoundException e) {
			// if there's no existing file, create the file.
			// then try opening it again.
			System.out.println("fnf");
			createNewFile(mFilename);
			startReadMode();
		} catch (IOException e) {
			// if unable to read the nextline
			System.out.println("io ex");
			return false;
		}
		setReading(true);
		return true;
	}

	private boolean closeReadMode() {
		try {
			mReader.close();
		} catch (IOException e) {
			return false;
		}
		setReading(false);
		return true;
	}

	private boolean hasNextItem() {
		boolean hasNext = (mNextItem != null);
		return hasNext;
	}

	private ItemData getNextItem() throws IOException {
		if (hasNextItem()) {
			ItemData result = mNextItem;
			String nextLine = getNextLine();
			mNextItem = getItemData(nextLine);
			return result;
		} else {
			return null;
		}
	}

	private boolean createNewFile(String filename) {
		try {
			new FileWriter(filename).close();
		} catch (IOException e) {
			return false;
		}
		return true;
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

		// this method is meant to be wordy
		// TODO should extract to separate class
		
		ItemType type = getItemType(lineArray[StorageIndices.TYPE]);
		switch (type) {
			case EVENT :
				return getEventItemData(lineArray);
			case TASK :
				return getTaskItemData(lineArray);
			case PLAN :
				return getPlanItemData(lineArray);
			default:
				return null;
			
		}
	}
	
	private ItemData getPlanItemData(String[] lineArray) {
		// [0uid, 1type, 2title, 3<tags>]

		ItemData item = new ItemData(ItemType.PLAN);

		int uid = Integer.parseInt(lineArray[StorageIndices.UID]);
		item.put(Keys.UID,
				uid);
		
		String title = lineArray[StorageIndices.TITLE];
		item.put(Keys.TITLE,
				title);
		
		boolean done = Boolean.parseBoolean(lineArray[StorageIndices.PLAN_DONE]);
		item.put(Keys.DONE, 
				done);

		String tagsString = lineArray[StorageIndices.PLAN_HASHTAGS];
		ArrayList<String> tagsList = getList(tagsString);
		item.put(Keys.HASHTAGS, 
				tagsList);

		return item;
	}

	private ItemData getTaskItemData(String[] lineArray) {
		// [0uid, 1type, 2title, 3ddate, 4dtime, 5<tags>]

		ItemData item = new ItemData(ItemType.TASK);

		int uid = Integer.parseInt(lineArray[StorageIndices.UID]);
		item.put(Keys.UID,
				uid);
		
		String title = lineArray[StorageIndices.TITLE];
		item.put(Keys.TITLE,
				title);

		String dueDate = lineArray[StorageIndices.DUE_DATE];
		String dueTime = lineArray[StorageIndices.DUE_TIME];
		Calendar dueCal = getCalendar(dueDate, dueTime);
		item.put(Keys.DUE, 
				dueCal);
		
		boolean done = Boolean.parseBoolean(lineArray[StorageIndices.TASK_DONE]);
		item.put(Keys.DONE, 
				done);

		String tagsString = lineArray[StorageIndices.TASK_HASHTAGS];
		ArrayList<String> tagsList = getList(tagsString);
		item.put(Keys.HASHTAGS, 
				tagsList);
		
		return item;
	}

	private ItemData getEventItemData(String[] lineArray) {
		 // [0uid, 1type, 2title, 3stdate, 4stime, 5endate, 6entime, 7<tags>]
		
		ItemData item = new ItemData(ItemType.EVENT);

		int uid = Integer.parseInt(lineArray[StorageIndices.UID]);
		item.put(Keys.UID,
				uid);
		
		String title = lineArray[StorageIndices.TITLE];
		item.put(Keys.TITLE,
				title);

		String startDate = lineArray[StorageIndices.START_DATE];
		String startTime = lineArray[StorageIndices.START_TIME];
		Calendar startCal = getCalendar(startDate, startTime);
		item.put(Keys.START, 
				startCal);

		String endDate = lineArray[StorageIndices.END_DATE];
		String endTime = lineArray[StorageIndices.END_TIME];
		Calendar endCal = getCalendar(endDate, endTime);
		item.put(Keys.END, 
				endCal);

		String tagsString = lineArray[StorageIndices.EVENT_HASHTAGS];
		ArrayList<String> tagsList = getList(tagsString);
		item.put(Keys.HASHTAGS, 
				tagsList);
		
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
		} else if (typeString.equals(StorageStrings.TYPE_TASK)) {
			return ItemType.TASK;
		} else if (typeString.equals(StorageStrings.TYPE_PLAN)) {
			return ItemType.PLAN;
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
		int month = Integer.parseInt(dateArray[1]) - 1; // to offset the 0-based month in calendar
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

	private boolean startWriteMode() {
		try {
			// will overwrite the current file with the new data.
			mWriter = new BufferedWriter(
					new FileWriter(mFilename));
		} catch (IOException e) {
			return false;
		}
		setWriting(true);
		return true;
	}

	private boolean closeWriteMode() {
		try {
			mWriter.close();
			mWriter = null;
		} catch (IOException e) {
			return false;
		}
		setWriting(false);
		return true;
	}

	private void write(ItemData item) throws WritingToStorageFailedException {
		String itemString = item.toString();
		try {
			mWriter.append(itemString);
			mWriter.newLine();
		} catch (IOException e) {
			throw new WritingToStorageFailedException();
		}
	}

	private boolean isWriting() {
		return mIsWriting;
	}

	private void setWriting(boolean writing) {
		mIsWriting = writing;
	}

	private boolean isReading() {
		return mIsReading;
	}

	private void setReading(boolean reading) {
		mIsReading = reading;
	}
}
