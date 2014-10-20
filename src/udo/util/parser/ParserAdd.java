package udo.util.parser;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

/** 
 * This class takes in ADD commands and break them into the following:
 * ADD_EVENT, ADD_TASK, ADD_PLAN
 * <p>
 * ADD_EVENT command is for events. Events contain a starting time and an ending time.
 * ADD_TASK command is for tasks. Tasks contain 1 deadline.
 * ADD_PLAN command is for plans. Plans have no deadlines nor starting and ending time.
 */

public class ParserAdd {
	
	private int startingIndex = 4;
	// assume title starts after add command, e.g. "add <title>"
	
	public ParserAdd() {
		
	}
	
	/**
	 * This method takes in user input and returns an InputData of the following 
	 * CommandType: ADD_EVENT, ADD_TASK, ADD_PLAN
	 * and the stores them in the necessary fields.
	 * 
	 * @param type
	 * @param details
	 * @return an InputData
	 */
	public InputData add(Command type, String details) {
		InputData data;
		if (isEvent(details)) {
			data = new InputData(Command.ADD_EVENT);
			ParserAddCommand event = new ParserAddEvent();
			event.fill(type, details, data);
		} else if (isTask(details)) {
			data = new InputData(Command.ADD_TASK);
			ParserAddCommand task = new ParserAddEvent();
			task.fill(type, details, data);
		} else {
			data = new InputData(Command.ADD_PLAN);
			//plan
		} 
		return data;
	}

	public InputData addEvent(Command type, String details) {
		InputData eventInputData = new InputData(type);
		String title = getEventTitle(details);
		ArrayList<String> tags = getTags(details);
		Calendar start = setFirstTimeAndDate(details);
		Calendar end = setSecondTimeAndDate(details);
		if (title.isEmpty()) {
			eventInputData.setParsingStatus(ParsingStatus.FAIL);
		} else {
			assert(start != null);
			assert(end != null);
			eventInputData.put(Keys.TITLE, title);
			eventInputData.put(Keys.HASHTAGS, tags);
			eventInputData.put(Keys.START, start);
			eventInputData.put(Keys.END, end);
			eventInputData.setParsingStatus(ParsingStatus.SUCCESS);
		}
		return eventInputData;
	}

	public InputData addTask(Command type, String details) {
		InputData taskInputData = new InputData(type);
		String title = getTaskTitle(details);
		ArrayList<String> tags = getTags(details);
		Calendar end = setFirstTimeAndDate(details);
		if (title.isEmpty()) {
			taskInputData.setParsingStatus(ParsingStatus.FAIL);
		} else {
			assert(end != null);
			taskInputData.put(Keys.TITLE, title);
			taskInputData.put(Keys.HASHTAGS, tags);
			taskInputData.put(Keys.DUE, end);
			taskInputData.setParsingStatus(ParsingStatus.SUCCESS);
		}
		return taskInputData;
	}

	public InputData addPlan(Command type, String details) {
		InputData planInputData = new InputData(type);
		String title = details.substring(startingIndex);
		title = title.replaceAll("#", "");
		ArrayList<String> tags = getTags(details);
		if (title.isEmpty()) {
			planInputData.setParsingStatus(ParsingStatus.FAIL);
		} else {
			planInputData.put(Keys.TITLE, title);
			planInputData.put(Keys.HASHTAGS, tags);
			planInputData.setParsingStatus(ParsingStatus.SUCCESS);
		}
		return planInputData;
	}
	
	// This method can only be used for events and tasks
	public String getEventTitle(String input) {
		String title = input.replaceAll("#", "");
		int endingIndex = title.indexOf("from") - 1; // trim ending white space
		title = title.substring(startingIndex, endingIndex);
		return title;
	}
	
	public String getTaskTitle(String input) {
		String title = input.replaceAll("#", "");
		int endingIndex = title.indexOf("by") - 1; // trim ending white space
		title = title.substring(startingIndex, endingIndex);
		return title;
	}
	
	public Calendar getDate(String input) {
		ParserDate date = new ParserDate();
		return date.getDate(input);
	}
	
	public Calendar getTime(String input) {
		ParserTime time = new ParserTime();
		return time.getTime(input);
	}
	
	public Calendar setFirstTimeAndDate(String details) {
		Calendar start = getTime(details);
		Calendar date = getDate(details);
		assert(start != null);
		assert(date != null);
		start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		start.set(Calendar.MONTH, date.get(Calendar.MONTH));
		start.set(Calendar.YEAR, date.get(Calendar.YEAR));
		return start;
	}
	
	public Calendar setSecondTimeAndDate(String details) {
		int toStringIndex = details.indexOf("to");
		assert(toStringIndex != -1);
		String endingTimeDateString = details.substring(toStringIndex);
		Calendar end = getTime(endingTimeDateString);
		Calendar date = getDate(endingTimeDateString);
		assert(end != null);
		assert(date != null);
		end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		end.set(Calendar.MONTH, date.get(Calendar.MONTH));
		end.set(Calendar.YEAR, date.get(Calendar.YEAR));
		return end;
	}
	
	// Returns an ArrayList of tags. Tags do not contain "#"
	// If no tags are found, retun an empty ArrayList
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		String tag;
		String words[] = input.split(" ");
		for (String word : words) {
			if (word.startsWith("#")) {
				tag = word.replaceFirst("#", "");
				assert(!tag.isEmpty());
				tagArrayList.add(tag);
			}
		}
		return tagArrayList;
	}

	// checks if there is 2 date or 2 time in input
	public boolean isEvent(String details) {
		if (details.contains("from") &&
			details.contains("to")) {
			return true;
		} else {
			return false;
		}
	}

	// checks if there is 1 time or 1 date in input
	public boolean isTask(String details) {
		if (details.contains("by")) {
			return true;
		} else {
			return false;
		}
	}

}
