package udo.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

/**
 * This class parses information from the input string and package it 
 * as an InputData object. Aside from LIST, DELETE, EDIT, SAVE, EXIT, 
 * UNDO commands, Parser also identifies events, tasks and plans from 
 * input string. <Explain critera for event, task and plan> The 
 * information is stored using Keys class constants. 
 * 
 * @author chongjiawei
 * 
 */

public class Parser {
	
	public Parser() {

	}
	/**
	 * Parses input string received and returns it as an InputData object.
	 * Depending on what the Command is, necessary fields are filled.
	 * @param input string 
	 * @return InputData object
	 */
	public InputData getInputData(String input) {
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
			return addEvent(commandType, details);
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

	public InputData addEvent(Command type, String details) {
		InputData addInputData = new InputData(type);
		if (isValidAdd(details)) { 
			String title = getTitle(details);
			ArrayList<String> tags = getTags(details);
			Calendar date = getDate(details);
			Calendar start = setEventStartTime(date, details);
			Calendar end = setEventEndTime(date, details);
			//put into inputdata
			if (hasUnfilledField(title, tags, date, start, end)) {
				// have yet to check for fail status for each field
				addInputData.setParsingStatus(ParsingStatus.FAIL);
			} else {
				addInputData.put(Keys.TITLE, title);
				addInputData.put(Keys.HASHTAGS, tags);
				addInputData.put(Keys.START, start);
				addInputData.put(Keys.END, end);
				addInputData.setParsingStatus(ParsingStatus.SUCCESS);
			}
		} else {
			addInputData.setParsingStatus(ParsingStatus.FAIL);
		}
		return addInputData;
	}

	public boolean hasUnfilledField(String title, ArrayList<String> tags,
			Calendar date, Calendar start, Calendar end) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Calendar setEventStartTime(Calendar date, String details) {
		Calendar start = getTime(details);
		start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		start.set(Calendar.MONTH, date.get(Calendar.MONTH));
		start.set(Calendar.YEAR, date.get(Calendar.YEAR));
		return start;
	}
	
	public Calendar setEventEndTime(Calendar date, String details) {
		int colonIndex = details.indexOf(":");
		String containsEndTime = details.substring(colonIndex + 1);
		Calendar end = getTime(containsEndTime);
		
		end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		end.set(Calendar.MONTH, date.get(Calendar.MONTH));
		end.set(Calendar.YEAR, date.get(Calendar.YEAR));
		return end;
	}
	
	public boolean isValidAdd(String input) {
		if (input.length() < 4) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isEvent(String input) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isValidEvent(String input) { // checks for events. Otherwise,
		// TODO Auto-generated method stub			// we tag it as task
		return false;
	}

	public String getTitle(String input) {
		String title = input;
		if (input.contains("#")) {
			int hashtagIndex = input.indexOf("#");
			title = input.substring(4, hashtagIndex);
		} else {
			int keywordIndex = getSmallestIndex(input);
			if (keywordIndex != 100000000) {
				title = input.substring(4, keywordIndex);
			}
		}
		return title;
	}
	
	public int getSmallestIndex(String input) {
		int fromStringIndex = input.lastIndexOf("from");
		int byStringIndex = input.lastIndexOf("by");
		int onStringIndex = input.lastIndexOf("on");
		int min = -1;
		int keywordIndex = 100000000; // a large positive contant
		
		if (fromStringIndex > min && fromStringIndex < keywordIndex) {
			keywordIndex = fromStringIndex;
		}
		if (byStringIndex > min && byStringIndex < keywordIndex) {
			keywordIndex = byStringIndex;
		}
		if (onStringIndex > min && onStringIndex < keywordIndex) {
			keywordIndex = onStringIndex;
		}
		return keywordIndex;
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
		//boolean amString = timeSubstring.contains("AM");
		//boolean pmString = timeSubstring.contains("PM");
		String ERROR_MESSAGE = "timeSubstring does not exist or it was not detected";
		while (timeSubstring.contains(":")) {
			colonIndex = timeSubstring.indexOf(":");
			timeSessionIndex = timeSubstring.indexOf("M");
			
			if (isVerifiedTimeString(timeSubstring, colonIndex, timeSessionIndex)) {
				timeSubstring = timeSubstring.substring(colonIndex - 2, timeSessionIndex + 1);
				return timeSubstring.trim();
			}
			timeSubstring = timeSubstring.substring(timeSessionIndex + 1);
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
				listInputData.setParsingStatus(ParsingStatus.FAIL);
				return listInputData;
			} else {
				listInputData.put(Keys.HASHTAG, tags.get(0));
				listInputData.put(Keys.QUERY, ListQuery.SINGLE_HASHTAG);
				listInputData.setParsingStatus(ParsingStatus.SUCCESS);
				return listInputData;
			}
		} else {
			listInputData.put(Keys.QUERY, ListQuery.ALL);
			listInputData.setParsingStatus(ParsingStatus.SUCCESS);
			return listInputData;
		}
	}

	public InputData delete(Command type, String details) {
		InputData deleteInputData = new InputData(type);
		if (isValidDelete(details)) {
			String deleteIndexString = details.substring(7);
			int deleteIndex = Integer.parseInt(deleteIndexString);
			deleteInputData.put(Keys.DELETE, deleteIndex);
			deleteInputData.setParsingStatus(ParsingStatus.SUCCESS);
			return deleteInputData;
		} else {
			deleteInputData.setParsingStatus(ParsingStatus.FAIL);
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
