package udo.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import udo.util.shared.Command;
import udo.util.shared.InputData;

/**
 * (Class description)
 * @author chongjiawei
 *
 */

public class Parser {
	
	private String mType; //event or task
	private String mTitle;
	private Calendar mDate;
	private Calendar mStartDate;
	private Calendar mEndDate;
	private Calendar mStartTime;
	private Calendar mEndTime;
	private ArrayList<Calendar> mListOfDates;
	private ArrayList<Calendar> mListOfTime;
	private ArrayList<String> mTags; // null or an ArrayList of tags, including "#" character in it
	private int mDeleteIndex;

	public Parser() {
		
	}
	
	public InputData getInputData(String input) { // InputData inputData = parser.getInputData(input);
		Command type = determineCommandType(input);
		InputData data = processCommandType(type, input);
		return data;
	}
	
	public Command determineCommandType(String input) {
		String parts[] = input.split(" ");
		String command = parts[0];
		switch(command) {
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
		switch(commandType) {
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
	
	//add <title> <hashTags, if any> on <date> from <start time> to <end time>
	//whether it is an event or a task
	public InputData add(Command type, String details) {
		if (isValidAdd(details)) { 				//to do: check whether it is an event or a task
			//mType
			/*
			mTitle = getTitle(details);
			mDate = getDate(details);
			mStartTime = getStartTime(details);
			mEndTime = getEndTime(details);
			mTags = getTags(details);
			*/
			InputData addInputData = new InputData(type);
			addInputData.put("type", mType);
			addInputData.put("title", mTitle);
			addInputData.put("date", mDate);
			addInputData.put("startTime", mStartTime);
			addInputData.put("endTime", mEndTime);
			addInputData.put("tags", mTags);
			
			return addInputData;
		} else {
			return null;
		}
	}
	
	public boolean isValidAdd(String input) {
		if (input.substring(4).isEmpty()) {
			return false;
		} else if (getTitle(input).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isValidEvent(String input) { //checks for events. Otherwise, we tag it as task
		return false;
	}

	public String getTitle(String input) { // includes hashtag for now
		int endPoint = input.lastIndexOf("on") - 1; 
		return input.substring(4, endPoint); 
	}

	//add <title> <hashTags, if any> on <date> 
	//from <start date and/or time> to <end date and/or time>
	//date format: dd/mm/yyyy

	// returns an empty ArrayList id no date is found
	// returns an ArrayList of dates according to first occurance in input string
	// focus on getting the right date format from input
	
	// does this catch 3/4/14 ?
	public ArrayList<Calendar> getDates(String input) {
		ArrayList<Calendar> listOfDates = new ArrayList<Calendar>();
		Calendar cal = Calendar.getInstance();
		if (input.contains("/")) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			Date date;
	
			int dayMonthSlashIndex;
			int monthYearSlashIndex;
			String dateSubstring = input;
			
			while (dateSubstring.contains("/")) {
				dayMonthSlashIndex = dateSubstring.indexOf("/");
				monthYearSlashIndex = dateSubstring.indexOf("/", dayMonthSlashIndex + 1);
				
				if (dateSubstring.indexOf("/") == dayMonthSlashIndex &&
					dayMonthSlashIndex + 3 == monthYearSlashIndex) {
					// problematic area
					dateSubstring = dateSubstring.substring(dayMonthSlashIndex - 2, monthYearSlashIndex + 5);
					dateSubstring = formatDateSubstring(dateSubstring);
					try {
						date = format.parse(dateSubstring);
						date.setMonth(date.getMonth() - 1);
					} catch (ParseException pe) {
						// Should I throw exception for this? If I shouldn't what should I do instead?
						 throw new IllegalArgumentException("The date entered, " + dateSubstring + " is invalid.", pe);
					}
					cal.setTime(date);
					listOfDates.add(cal);
				} 
				dateSubstring = dateSubstring.substring(dayMonthSlashIndex + 1);
			}
		}
		return listOfDates;
	}
	
	public String formatDateSubstring(String input) {
		String date = input.trim();
		int lastLetterIndex = date.length() - 1;
		String lastLetter = date.substring(lastLetterIndex);
		
		while (!isInteger(lastLetter)) {
			date = date.substring(0, lastLetterIndex);
			lastLetterIndex = lastLetterIndex - 1;
			lastLetter = date.substring(lastLetterIndex);
		}
		date.concat(" ");
		return date;
	}
	
	public boolean isInteger(String input) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	//time format: hh:mm a(12 hours)	
	// does it catch 9:00 AM
	// does it catch 9 AM / 9am?
	public ArrayList<Calendar> getTime(String input) {
		ArrayList<Calendar> listOfTime = new ArrayList<Calendar>();
		Calendar time = Calendar.getInstance();
		if (input.contains(":")) {
			SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
			format.setLenient(false);
			Date date;
			
			int colonIndex;
			String timeSubstring = input;
			
			while (timeSubstring.contains(":")) {
				colonIndex = timeSubstring.indexOf(":");
				
				/*
				if (dateSubstring.indexOf("/") == dayMonthSlashIndex &&
					dayMonthSlashIndex + 3 == monthYearSlashIndex) {
					dateSubstring = dateSubstring.substring(dayMonthSlashIndex - 2, monthYearSlashIndex + 5);
					try {
						date = format.parse(dateSubstring);
						date.setMonth(date.getMonth() + 1);
					} catch (ParseException pe) {
						// Should I throw exception for this? If I shouldn't what should I do instead?
						 throw new IllegalArgumentException("The date entered, " + dateSubstring + " is invalid.", pe);
					}
					cal.setTime(date);
					listOfDates.add(cal);
				} 
				dateSubstring = dateSubstring.substring(dayMonthSlashIndex + 1);
				*/
			}
		}
		return listOfTime;
	}

	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		if (input.contains("#")) {
			int indexOfSeparator;
			String tag;
			int indexOfHash = input.indexOf("#");
			
			while (indexOfHash != -1) {
				indexOfSeparator = input.indexOf(" ", indexOfHash);
				tag = input.substring(indexOfHash, indexOfSeparator);
				tagArrayList.add(tag);
				indexOfHash = input.indexOf("#", indexOfSeparator);
			}
		} 
		return tagArrayList;
	}
	
	public InputData list(Command type, String details) {
		if (!details.isEmpty()) {
			return new InputData(type);
		} else if (details.contains("#")) { // prepare for hashtag intake
			return null;	
		} else {
			return null;
		}
	}
	
	public InputData delete(Command type, String details) {
		if (isValidDelete(details)) {
			mDeleteIndex = Integer.parseInt(details);
			InputData deleteInputData = new InputData(type);
			deleteInputData.put("deleteIndex", mDeleteIndex);
			return deleteInputData;
		} else {
			return null;
		}
	}
	
	public boolean isValidDelete(String input) {
		if (!input.isEmpty() || !isInteger(input)) {
			return false;
		} else {
			return true;
		}
	}
	
	public InputData save(Command type, String details) {
		return new InputData(type);
	}
	
	public InputData exit(Command type, String details) {
		return new InputData(type);
	}
}
