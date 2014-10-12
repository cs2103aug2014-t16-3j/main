package udo.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import udo.util.parser.ParserDate;
import udo.util.parser.ParserTime;
import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

/**
 * This class parses information from the input string and package it 
 * as an InputData object. 
 * <p>
 * Aside from LIST, DELETE, EDIT, SAVE, EXIT and UNDO commands, 
 * Parser also identifies events, tasks and plans from 
 * input string.
 * <p>
 * ADD_EVENT command is for events. Events contain a starting time and an ending time.
 * ADD_TASK command is for tasks. Tasks contain 1 deadline.
 * ADD_PLAN command is for plans. Plans have no deadlines nor starting and ending time.
 * <p>
 * Parser stores the keys using Keys class constants. 
 * 
 * @author chongjiawei
 * 
 */

public class Parser {
	
	private int largePositiveInt = 100000000;
	private int indexDoesNotExist = -1;
	private int empty = 0;
	// these numbers are constants
	
	private int determineCommandTypeCommandPart = 0;
	// retrives the first word of input. Assumes first word of input is command word
	
	private int isValidAddint = 4;
	// checks if there is input after "add "
	
	private int getTitleStartingIndex = 4;
	// assume title starts after add command, e.g. "add <title>"
	private int getTitleOffset = -1;
	// removes "#" in title
	
	private int getTagsOffsetIndex = 1; 
	// removes the "#" character from input
	
	private int deleteStartingIndex = 7; 
	// get input after "delete "
	private int isValidDeleteInt = 8; 
	// checks if there is input after "delete "
	
	public Parser() {

	}
	
	/**
	 * Parses input string received and returns it as an InputData object.
	 * Depending on what the Command is, necessary fields are filled.
	 * 
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
		String command = parts[determineCommandTypeCommandPart];
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
			case "undo":
				return Command.UNDO;
			case "edit":
				return Command.EDIT;
			default:
				return Command.NULL;
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
			case UNDO:
				return undo(commandType, details);
			case EDIT:
				return edit(commandType, details);
			case NULL:
				return parsingTrash(commandType, details);
			default:
				return null; // parsing status fail
			}
	}

	// add <title> <hashTags, if any> on <date> from <start time> to <end time>
	// whether it is an event or a task
	public InputData addEvent(Command type, String details) {
		InputData addInputData = new InputData(type);
		if (isValidAdd(details)) { 
			String title = getTitle(details);
			ArrayList<String> tags = getTags(details);
			Calendar date = getDate(details);
			Calendar start = setEventStartTime(details);
			Calendar end = setEventEndTime(details);
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
	
	public Calendar setEventStartTime(String details) {
		Calendar start = getTime(details);
		Calendar date = getDate(details);
		start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		start.set(Calendar.MONTH, date.get(Calendar.MONTH));
		start.set(Calendar.YEAR, date.get(Calendar.YEAR));
		return start;
	}

	public Calendar setEventEndTime(String details) {
		int colonIndex = details.indexOf(":");
		String containsEndTime = details.substring(colonIndex + 1);
		Calendar end = getTime(containsEndTime);
		int toStringIndex = details.indexOf("to");
		Calendar date = getDate(details.substring(toStringIndex));
		end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		end.set(Calendar.MONTH, date.get(Calendar.MONTH));
		end.set(Calendar.YEAR, date.get(Calendar.YEAR));
		return end;
	}
	
	public boolean isValidAdd(String input) {
		if (input.length() < isValidAddint) {
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
		int keywordIndex = getSmallestIndex(input);
		if (keywordIndex != largePositiveInt) {
			String title = input.substring(getTitleStartingIndex, keywordIndex + getTitleOffset);
			title = title.replaceAll("#", "");
			return title;
		}
		return input;
	}
	
	public int getSmallestIndex(String input) {
		int fromStringIndex = input.lastIndexOf("from");
		int byStringIndex = input.lastIndexOf("by");
		int onStringIndex = input.lastIndexOf("on");
		int min = -1;
		int keywordIndex = largePositiveInt; 
		
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
		ParserDate date = new ParserDate();
		date.decipherText(input);
		return date.getDate();
	}

	// returns first timing it reads in input
	public Calendar getTime(String input) {
		ParserTime time = new ParserTime();
		time.decipherText(input);
		return time.getTime();
	}
	
	/*
	 * edit <uid> <field> <newinfo>
	 */
	private InputData edit(Command commandType, String details) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns an ArrayList of tags. Tags do not contain "#"
	 * If no tags are found, retun an empty ArrayList
	 * <p>
	 * Tags require a space after them. Eg. "#2013 "
	 * @param input that is directly retrieved from user
	 * @return an ArrayList<String> of tags
	 */
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		if (input.contains("#")) {
			int indexOfSeparator;
			String tag;
			int indexOfHash = input.indexOf("#");
			boolean hashIndexExist = (indexOfHash != indexDoesNotExist);
			while (hashIndexExist) {
				indexOfSeparator = input.indexOf(" ", indexOfHash);
				tag = input.substring(indexOfHash + getTagsOffsetIndex, indexOfSeparator);
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
			if (tags.size() == empty) {
				listInputData.setParsingStatus(ParsingStatus.FAIL);
				return listInputData;
			} else {
				assert(tags.size() > empty);
				assert(!tags.get(0).isEmpty());
				
				listInputData.put(Keys.HASHTAG, tags.get(0));
				listInputData.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
				listInputData.setParsingStatus(ParsingStatus.SUCCESS);
				return listInputData;
			}
		} else {
			listInputData.put(Keys.QUERY_TYPE, ListQuery.ALL);
			listInputData.setParsingStatus(ParsingStatus.SUCCESS);
			return listInputData;
		}
	}

	public InputData delete(Command type, String details) {
		InputData deleteInputData = new InputData(type);
		if (isValidDelete(details)) {
			String deleteIndexString = details.substring(deleteStartingIndex);
			int deleteIndex = Integer.parseInt(deleteIndexString);
			deleteInputData.put(Keys.UID, deleteIndex);
			deleteInputData.setParsingStatus(ParsingStatus.SUCCESS);
			return deleteInputData;
		} else {
			deleteInputData.setParsingStatus(ParsingStatus.FAIL);
			return deleteInputData;
		}
	}
	
	public boolean isValidDelete(String input) {
		if (input.length() >= isValidDeleteInt) {
			String deleteIndexString = input.substring(deleteStartingIndex);
			if (isInteger(deleteIndexString)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public InputData parsingTrash(Command type, String details) {
		InputData trashInputData = new InputData(type);
		trashInputData.setParsingStatus(ParsingStatus.FAIL);
		return trashInputData;
	}

	public InputData undo(Command type, String details) {
		InputData undoInputData = new InputData(type);
		undoInputData.setParsingStatus(ParsingStatus.SUCCESS);
		return undoInputData;
	}

	public InputData save(Command type, String details) {
		InputData saveInputData = new InputData(type);
		saveInputData.setParsingStatus(ParsingStatus.SUCCESS);
		return saveInputData;
	}

	public InputData exit(Command type, String details) {
		InputData exitInputData = new InputData(type);
		exitInputData.setParsingStatus(ParsingStatus.SUCCESS);
		return exitInputData;
	}
}
