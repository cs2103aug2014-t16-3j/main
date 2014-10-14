package udo.util.parser;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.InputData;

/** 
 * This class takes in ADD commands and break them into the following:
 * ADD_EVENT, ADD_TASK, ADD_PLAN
 * <p>
 * ADD_EVENT command is for events. Events contain a starting time and an ending time.
 * ADD_TASK command is for tasks. Tasks contain 1 deadline.
 * ADD_PLAN command is for plans. Plans have no deadlines nor starting and ending time.
 * 
 * @author chongjiawei
 *
 */
public class ParserAdd {
	
	private int largePositiveInt = 100000000;
	private int indexDoesNotExist = -1;
	private int empty = 0;
	
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
		InputData add = null;
		if (isEvent(details)) {
			add = addEvent(Command.ADD_EVENT, details);
			// tags
		} else if (isTask(details)) {
			add = addTask(Command.ADD_TASK, details);
			// tags
			// check if valid task
		} else if (isPlan(details)) {
			add = addPlan(Command.ADD_PLAN, details);
			// a plan is the title itself, no need to get title
			// tags
			// check if valid plan
		} else {
			// Parsing Status failed
		}
		return add;
	}

	public InputData addEvent(Command addEvent, String details) {
		// TODO Auto-generated method stub
		return null;
	}

	public InputData addTask(Command addTask, String details) {
		// TODO Auto-generated method stub
		return null;
	}

	public InputData addPlan(Command addPlan, String details) {
		// TODO Auto-generated method stub
		// a plan is the title itself, no need to get title
		return null;
	}
	
	// --- Start of title methods --- //
	
	// This method can only be used for events and tasks
	public String getTitle(String input) {
		String title = input.replaceAll("#", "");
		int startingIndex = 4;	// assume title starts after add command, e.g. "add <title>"
		int endingIndex = getEndingTitleIndex(input) - 1; // trim ending white space
		title = title.substring(startingIndex, endingIndex);
		return title;
	}
	
	public int getEndingTitleIndex(String input) {
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
	
	// --- End of title methods --- //
	
	/**
	 * Returns an ArrayList of tags. Tags do not contain "#"
	 * If no tags are found, retun an empty ArrayList
	 * @param input that is directly retrieved from user
	 * @return an ArrayList<String> of tags
	 */
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		String tag;
		String words[] = input.split(" ");
		for (String word : words) {
			if (word.startsWith("#")) {
				tag = word.replaceFirst("#", "");
				tagArrayList.add(tag);
			}
		}
		return tagArrayList;
	}

	// checks if there is 2 date or 2 time in input
	public boolean isEvent(String details) {
		ParserDate date = new ParserDate();
		ParserTime time = new ParserTime();
		int toStringIndex = details.indexOf("to");
		String firstDateAndTimeInString = details.substring(0, toStringIndex);
		String secondDateAndTimeInString = details.substring(toStringIndex);
		
		if (date.containsDate(firstDateAndTimeInString) &&
			date.containsDate(secondDateAndTimeInString)) {
			return true;
		} else if (time.containsTime(firstDateAndTimeInString) &&
					time.containsTime(secondDateAndTimeInString)) {
			return true;
		} else {
			return false;
		}
	}

	// checks if there is 1 time or 1 date in input
	public boolean isTask(String details) {
		ParserDate date = new ParserDate();
		ParserTime time = new ParserTime();
		if (date.containsDate(details) ||
			time.containsTime(details)) {
			return true;
		} else {
			return false;
		}
	}

	// checks that there are no time and date in input
	public boolean isPlan(String details) {
		ParserDate date = new ParserDate();
		ParserTime time = new ParserTime();
		if ((date.containsDate(details) == false) &&
			(time.containsTime(details) == false)) {
			return true;
		} else {
			return false;
		}
	}
}
