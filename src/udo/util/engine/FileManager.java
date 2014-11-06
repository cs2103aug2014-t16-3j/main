//@author A0108358B
package udo.util.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

import udo.util.exceptions.ReadingFromStorageException;
import udo.util.exceptions.WritingToStorageException;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.StorageIndices;
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
	
	/**
	 * lets you specify what file to read.
	 * 
	 * @param filename the file to read
	 */
	public FileManager(String filename) {
		mFilename = filename;
		mIsReading = false;
		mIsWriting = false;
	}

	/**
	 * gets an arraylist of data after reading in the file
	 * 
	 * @return the list of items
	 * @throws ReadingFromStorageException if cannot read
	 * @throws IOException if reading error
	 */
	public ArrayList<ItemData> getFromFile() throws ReadingFromStorageException, IOException {
		startReadMode();
		if (!isReading()) {
			throw new ReadingFromStorageException("read mode cannot be started");
		}
		ArrayList<ItemData> result = new ArrayList<ItemData>();
		while (hasNextItem()) {
			ItemData item = getNextItem();
			result.add(item);
		}
		stopReadMode();
		return result;
	}

	/**
	 * writes a list of items to the file. 
	 * @param list the list of items to write.
	 * @throws IOException if writing error or file error.
	 * @throws WritingToStorageException if cannot write
	 */
	public void writeToFile(ArrayList<ItemData> list) throws IOException, WritingToStorageException {
		startWriteMode();
		if (!isWriting()) {
			throw new WritingToStorageException("write mode cannot be started");
		}
		for (ItemData item : list) {
			write(item);
		}
		stopWriteMode();
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
			File f = new File(StorageStrings.FILEPATH);
			f.mkdirs();
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
		if (tagsString == null || tagsString.length() == 0) {
			return list;
		} else {
			String[] tagsArray = tagsString.split(",");
			for (int i = 0; i < tagsArray.length; i++) {
				list.add(tagsArray[i]);
			}
			return list;
		}
	}

	private void startReadMode() {
		if (isWriting()) {
			setReading(false);
		}
		
		try {
			mReader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(mFilename),
							"UTF-8"));
			String nextLine = mReader.readLine();
			mNextItem = getItemData(nextLine);
			setReading(true);
			
		} catch (FileNotFoundException e) {
			// if there's no existing file, create the file.
			// then try opening it again.
			setReading(false);
			createNewFile(mFilename);
			startReadMode();
			
		} catch (IOException e) {
			// if unable to read the nextline
			setReading(false);
		}
	}

	private void stopReadMode() {
		try {
			mReader.close();
			setReading(false);
		} catch (IOException e) {
			setReading(true);
		}
	}

	private void startWriteMode() {
		try {
			// will overwrite the current file with the new data.
			mWriter = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(mFilename),
							"UTF-8"));
			setWriting(true);
		} catch (IOException e) {
			setWriting(false);
		}
	}

	private void stopWriteMode() {
		try {
			mWriter.close();
			setWriting(false);
		} catch (IOException e) {
			setWriting(true);
		}
	}

	private void write(ItemData item) throws IOException {
		String itemString = item.toString();
		mWriter.append(itemString);
		mWriter.newLine();
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
