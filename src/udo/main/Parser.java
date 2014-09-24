package udo.main;

import java.util.ArrayList;

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
	private int[] mDateFormat;
	private String mDate;
	private String mStartTime;
	private String mEndTime;
	private ArrayList<String> mTags; // null or an ArrayList of tags, including "#" character in it
	private int mDeleteIndex;
	private String mDetails;

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
			mTitle = getTitle(details);
			mDate = getDate(details);
			mStartTime = getStartTime(details);
			mEndTime = getEndTime(details);
			mTags = getTags(details);
			
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
		} else if (getTitle(input).isEmpty() ||
					getDate(input).isEmpty()) {
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

	public String getDate(String input) {
		int startingPoint = input.lastIndexOf("on") + 3;
		int endPoint = input.lastIndexOf("from") - 1;
		return input.substring(startingPoint, endPoint);
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
			
			return tagArrayList;
		
		} else {
			return null;
		}
	}

	public String getStartTime(String input) {
		int startingPoint = input.lastIndexOf("from") + 5;
		int endPoint = input.lastIndexOf("to") - 1;
		return input.substring(startingPoint, endPoint);
	}

	public String getEndTime(String input) {
		int startingPoint = input.lastIndexOf("to") + 3;
		return input.substring(startingPoint);
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
	
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	public InputData save(Command type, String details) {
		return new InputData(type);
	}
	
	public InputData exit(Command type, String details) {
		return new InputData(type);
	}
}
