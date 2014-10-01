package udo.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ParsingStatus;

/**
 * (Class description)
 * 
 * @author chongjiawei
 * 
 */

public class Parser {

	private String mTitle;
	private Calendar mStartDate;
	private Calendar mEndDate;
	private Calendar mStartTime;
	private Calendar mEndTime;
	private ArrayList<String> mTags;

	public Parser() {

	}

	public InputData getInputData(String input) { // InputData inputData =
													// parser.getInputData(input);
		Command type = determineCommandType(input);
		InputData data = processCommandType(type, input);
		return data;
	}

	public Command determineCommandType(String input) {
		String parts[] = input.split(" ");
		String command = parts[0];
		switch (command) {
		case "add":
			return Command.ADD_EVENT;
		case "list":
			return Command.LIST;
		case "delete":
			return Command.DELETE;
		case "save":
			return Command.SAVE;
		case "exit":
			return Command.EXIT;
		default:
			return null;
		}
	}

	public InputData processCommandType(Command commandType, String details) {
		switch (commandType) {
		case ADD_EVENT:
			return add(commandType, details);
		case LIST:
			return list(commandType, details);
		case DELETE:
			return delete(commandType, details);
		case SAVE:
			return save(commandType, details);
		case EXIT:
			return exit(commandType, details);
		default:
			return null;
		}
	}

	// add <title> <hashTags, if any> on <date> from <start time> to <end time>
	// whether it is an event or a task
	public InputData add(Command type, String details) {
		if (isValidAdd(details)) { // to do: check whether it is an event or a
									// task
			// to do: make date and time into 1 calendar object
			ArrayList<Calendar> listOfDates = getDate(details);
			ArrayList<Calendar> listOfTime = getTime(details);
			
			mTitle = getTitle(details);
			mTags = getTags(details);

			//will be refactored to event, task, plan
			int hour, mins;
			if (listOfDates.size() == 1 && listOfTime.size() == 1) {
				hour = listOfTime.get(0).HOUR;
				mins = listOfTime.get(0).MINUTE;
				listOfDates.get(0).set(Calendar.HOUR, hour);
				listOfDates.get(0).set(Calendar.MINUTE, mins);
			} else if (listOfDates.size() == 1 && listOfTime.size() == 2) {
				hour = listOfTime.get(0).HOUR;
				mins = listOfTime.get(0).MINUTE;
				listOfDates.get(0).set(Calendar.HOUR, hour);
				listOfDates.get(0).set(Calendar.MINUTE, mins);
				hour = listOfTime.get(1).HOUR;
				mins = listOfTime.get(1).MINUTE;
				listOfDates.get(0).set(Calendar.HOUR, hour);
				listOfDates.get(0).set(Calendar.MINUTE, mins);
			}

			InputData addInputData = new InputData(type);
			addInputData.put(Keys.TITLE, mTitle);
			// addInputData.put(Keys.START, mStartDate);
			// addInputData.put(Keys.END, mEndDate);
			addInputData.put(Keys.HASHTAGS, mTags);

			return addInputData;
		} else {
			return null;
		}
	}

	public boolean isValidAdd(String input) {
		if (input.length() < 4) {
			return false;
		} else if (getTitle(input).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isValidEvent(String input) { // checks for events. Otherwise,
												// we tag it as task
		return false;
	}

	public String getTitle(String input) {
		String title = input;
		if (input.contains("#")) {
			int hashtagIndex = input.indexOf("#");
			title = input.substring(0, hashtagIndex);
		} else {
			ArrayList<Calendar> listOfDates = getDate(input);
			ArrayList<Calendar> listOfTime = getTime(input);
			if (listOfDates.size() > 0 || listOfTime.size() > 0) {
				// keywords
				int fromStringIndex = input.lastIndexOf("from");
				int byStringIndex = input.lastIndexOf("by");
				int onStringIndex = input.lastIndexOf("on");

				if (fromStringIndex != -1) {
					title = input.substring(0, fromStringIndex);
				} else if (byStringIndex != -1) {
					title = input.substring(0, byStringIndex);
				} else if (onStringIndex != -1) {
					title = input.substring(0, onStringIndex);
				}
			}
		}
		return title;
	}

	// does this catch 3/4/14 ?
	public Calendar getDate(String input) {
		String dateString = getDateString(input);
		Calendar cal = formatDateSubstring(dateString);
		return cal;
	}
	
	// returns the first date it reads
	public String getDateString(String input) {
		int dayMonthSlashIndex;
		int monthYearSlashIndex;
		String dateSubstring = input;
		String ERROR_MESSAGE = "DateString does not exist or it was not detected";
		while (dateSubstring.contains("/")) {
			dayMonthSlashIndex = dateSubstring.indexOf("/");
			monthYearSlashIndex = dateSubstring.indexOf("/", dayMonthSlashIndex + 1);
			
			if (isValidDateString(dateSubstring, dayMonthSlashIndex, monthYearSlashIndex)) {
				dateSubstring = dateSubstring.substring(dayMonthSlashIndex - 2, monthYearSlashIndex + 3);
				return dateSubstring.trim();
			}
			dateSubstring = dateSubstring.substring(dayMonthSlashIndex + 1);
		}
		return ERROR_MESSAGE;
	}
	
	public boolean isValidDateString(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		if (isSingleDigitMonth(input, dayMonthSlashIndex, monthYearSlashIndex) ||
			isDoubleDigitMonth(input, dayMonthSlashIndex, monthYearSlashIndex)) {
			try {
				String day = input.substring(dayMonthSlashIndex - 2, dayMonthSlashIndex);
				String month = input.substring(dayMonthSlashIndex + 1, monthYearSlashIndex);
				String year = input.substring(monthYearSlashIndex + 1, monthYearSlashIndex + 3);
				
				int dayInt = Integer.parseInt(day.trim());
				int monthInt = Integer.parseInt(month);
				int yearInt = Integer.parseInt(year);
			} catch (IndexOutOfBoundsException indexOutOfBoundsException) {
				return false;
			} catch (NumberFormatException numberFormatException) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean isSingleDigitMonth(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		if (input.indexOf("/") == dayMonthSlashIndex && 
			dayMonthSlashIndex + 2 == monthYearSlashIndex) { // for 1 digit month
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isDoubleDigitMonth(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		if (input.indexOf("/") == dayMonthSlashIndex && 
			dayMonthSlashIndex + 3 == monthYearSlashIndex) { // for 2 digit month
			return true;
		} else {
			return false;
		}
	}

	public Calendar formatDateSubstring(String input) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date date;

		try {
			date = format.parse(input);
			cal.setTime(date);
		} catch (ParseException parserException) {
			// inputData status fail
			return cal;
		}
		return cal;
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// returns first timing it reads in input
	public Calendar getTime(String input) {
		String timeString = getTimeString(input);
		Calendar cal = formatTimeSubstring(timeString);
		return cal;
	}
	
	//returns the first time it reads
	public String getTimeString(String input) {
		int timeSessionIndex;
		int colonIndex;
		String timeSubstring = input.toUpperCase();
		boolean amString = timeSubstring.contains("AM");
		boolean pmString = timeSubstring.contains("PM");
		String ERROR_MESSAGE = "timeSubstring does not exist or it was not detected";
		while (timeSubstring.contains(":") && (amString || pmString) ) {
			colonIndex = timeSubstring.indexOf(":");
			timeSessionIndex = timeSubstring.indexOf("M");
			
			if (isVerifiedTimeString(timeSubstring, colonIndex, timeSessionIndex)) {
				timeSubstring = timeSubstring.substring(colonIndex - 2, timeSessionIndex + 1);
				return timeSubstring.trim();
			}
			timeSubstring = timeSubstring.substring(colonIndex + 1);
		}
		return ERROR_MESSAGE;
	}
	
	public boolean isVerifiedTimeString(String timeSubstring, int colonIndex, int timeSessionIndex) {
		if (colonIndex + 4 == timeSessionIndex &&
			containsTimeSession(timeSubstring, colonIndex, timeSessionIndex)) {
			try {
				//check for integers
				String hourString = timeSubstring.substring(colonIndex - 2, colonIndex);
				String minString = timeSubstring.substring(colonIndex + 1, colonIndex + 3);
				
				int hours = Integer.parseInt(hourString.trim());
				int mins = Integer.parseInt(minString);				
			} catch (IndexOutOfBoundsException indexOutOfBoundsException) {
				return false;
			} catch (NumberFormatException numberFormatException) {
				return false;
			}
			return true;
		}
		return false;
	}

	// checks if it contains "AM" and "PM" strings
	public boolean containsTimeSession(String timeSubstring, int colonIndex, int timeSessionIndex) {
		int amStringIndex = timeSubstring.indexOf("AM", colonIndex);
		int pmStringIndex = timeSubstring.indexOf("PM", colonIndex);
		
		if ((amStringIndex > 0 && timeSessionIndex - 1 == amStringIndex) ||
			(pmStringIndex > 0 && timeSessionIndex - 1 == pmStringIndex)) {
			return true;
		} else {
			return false;
		}
	}

	public Calendar formatTimeSubstring(String input) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("hh:mma");
		Date date;
		
		try {
			date = format.parse(input);
			cal.setTime(date);
		} catch (ParseException parserException) {
			// inputData status fail
			return cal;
		}
		return cal;
	}

	/**
	 * Returns an ArrayList of tags. Tags do not contain "#"
	 * If no tags are found, retun an empty ArrayList
	 * Tags require a space after them. Eg. #2013<space>
	 * @param input that is directly retrieved from user
	 * @return an ArrayList<String> of tags
	 */
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		if (input.contains("#")) {
			int indexOfSeparator;
			String tag;
			int indexOfHash = input.indexOf("#");

			while (indexOfHash != -1) {
				indexOfSeparator = input.indexOf(" ", indexOfHash);
				tag = input.substring(indexOfHash + 1, indexOfSeparator);
				tagArrayList.add(tag);
				indexOfHash = input.indexOf("#", indexOfSeparator);
			}
		}
		return tagArrayList;
	}

	public InputData list(Command type, String details) {
		InputData listInputData = new InputData(type);
		if (details.contains("#")) {
			ArrayList<String> tags = getTags(details);
			if (tags.size() == 0) {
				listInputData.setStatus(ParsingStatus.FAIL);
				return listInputData;
			} else {
				listInputData.put(Keys.HASHTAG, tags.get(0));
				listInputData.setStatus(ParsingStatus.SUCCESS);
				return listInputData;
			}
		} else {
			listInputData.setStatus(ParsingStatus.SUCCESS);
			return listInputData;
		}
	}

	public InputData delete(Command type, String details) {
		InputData deleteInputData = new InputData(type);
		if (isValidDelete(details)) {
			String deleteIndexString = details.substring(7);
			int deleteIndex = Integer.parseInt(deleteIndexString);
			deleteInputData.put(Keys.DELETE, deleteIndex);
			deleteInputData.setStatus(ParsingStatus.SUCCESS);
			return deleteInputData;
		} else {
			deleteInputData.setStatus(ParsingStatus.FAIL);
			return deleteInputData;
		}
	}

	public boolean isValidDelete(String input) {
		if (input.length() >= 8) {
			String deleteIndexString = input.substring(7);
			if (isInteger(deleteIndexString)) {
				return true;
			}
		}
		return false;
	}

	public InputData undo(Command type, String details) {
		return new InputData(type);
	}

	public InputData save(Command type, String details) {
		return new InputData(type);
	}

	public InputData exit(Command type, String details) {
		return new InputData(type);
	}
}
