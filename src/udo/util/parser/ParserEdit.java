package udo.util.parser;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ParsingStatus;

/**
 * This class handles all editing done by the user.
 * Input recieve by this class is in the format: 
 * "edit <<uid>> <<field to edit>> <<new info>>"
 */

public class ParserEdit {
	
	private boolean isValidEdit = false;

	public ParserEdit() {
		
	}
	//edit <uid> <field> <new-info>
	public InputData edit(Command type, String details) {
		InputData data = new InputData(type);
		int uid = getUid(details);
		String field = getField(details);
		boolean uidExist = (uid != -1);
		if (uidExist) {
			data.put(Keys.UID, uid);
			updateField(field, data, details);
			if (!isValidEdit) {
				data.setParsingStatus(ParsingStatus.FAIL);
			} else {
				assert(isValidEdit == true);
				data.setParsingStatus(ParsingStatus.SUCCESS);
			}
		} else {
			assert(!uidExist);
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return data;
	}
	
	public InputData updateField(String field, InputData data, String details) {
		switch (field) {
		case "title":
			return setTitle(data, details);
		case "start time":
			return setStartTime(data, details);
		case "end time":
			return setEndTime(data, details);
		case "start date":
			return setStartDate(data, details);
		case "end date":
			return setEndDate(data, details);
		case "due time":
			return setDueTime(data, details);
		case "due date":
			return setDueDate(data, details);
		default:
			return null;
		}
	}
	// check whether any of the value returned is null
	public InputData setTitle(InputData data, String details) {
		String title = getTitle(details);
		ArrayList<String> tags = getTags(details);
		if (!title.isEmpty()) {
			data.put(Keys.FIELD, EditField.TITLE);
			data.put(Keys.VALUE, title);
			data.put(Keys.HASHTAGS, tags);
			isValidEdit = true;
		} 
		return data;
	}
	public InputData setStartTime(InputData data, String details) {
		Calendar startTime = getStartTime(details);
		if (startTime != null) {
			data.put(Keys.FIELD, EditField.START_TIME);
			data.put(Keys.VALUE, startTime);
			isValidEdit = true;
		}
		return data;
	}
	public InputData setEndTime(InputData data, String details) {
		Calendar endTime = getEndTime(details);
		if (endTime != null) {
			data.put(Keys.FIELD, EditField.END_TIME);
			data.put(Keys.VALUE, endTime);
			isValidEdit = true;
		}
		return data;
	}
	public InputData setStartDate(InputData data, String details) {
		Calendar startDate = getStartDate(details);
		if (startDate != null) {
			data.put(Keys.FIELD, EditField.START_DATE);
			data.put(Keys.VALUE, startDate);
			isValidEdit = true;
		}
		return data;
	}
	public InputData setEndDate(InputData data, String details) {
		Calendar endDate = getEndDate(details);
		if (endDate != null) {
			data.put(Keys.FIELD, EditField.END_DATE);
			data.put(Keys.VALUE, endDate);
			isValidEdit = true;
		}
		return data;
	}
	public InputData setDueTime(InputData data, String details) {
		Calendar dueTime = getDueTime(details);
		if (dueTime != null) {
			data.put(Keys.FIELD, EditField.DUE_TIME);
			data.put(Keys.VALUE, dueTime);
			isValidEdit = true;
		}
		return data;
	}
	public InputData setDueDate(InputData data, String details) {
		Calendar dueDate = getDueDate(details);
		if (dueDate != null) {
			data.put(Keys.FIELD, EditField.DUE_DATE);
			data.put(Keys.VALUE, dueDate);
			isValidEdit = true;
		}
		return data;
	}
	// returns uid if it exists
	// otherwise returns -1
	public int getUid(String details) {
		String[] words = details.split(" ");
		String uidString = words[1];
		int uid = -1;
		try {
			 uid = Integer.parseInt(uidString);
		} catch (NumberFormatException nfe) {
			
		}
		return uid;
	}
	
	// returns one of the fields if it exists
	// otherwise returns null
	public String getField(String details) {
		String[] fields = {"title",
				"start time", "end time", 
				"start date", "end date",
				"due time", "due date"};
		details = details.toLowerCase();
		for (int i = 0; i < fields.length; i++) {
			if (details.contains(fields[i])) {
				return fields[i];
			}
		}
		return null;
	}
	
	// need to take in tags as well
	// returns the new title if it exists
	// otherwise returns null
	public String getTitle(String details) {
		int startingIndex = 17; // new info starts after "edit 12345 title "
		try {
			String title = details.substring(startingIndex);
			title.replaceAll("#", "");
			return title;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Calendar getStartTime(String details) {
		int startingIndex = 22; // new info starts after "edit 12345 start time "
		try {
			String timeString = details.substring(startingIndex);
			ParserTime startTime = new ParserTime();
			Calendar cal = startTime.getTime(timeString);
			return cal;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Calendar getEndTime(String details) {
		int startingIndex = 20; // new info starts after "edit 12345 end time "
		try {
			String timeString = details.substring(startingIndex);
			ParserTime endTime = new ParserTime();
			Calendar cal = endTime.getTime(timeString);
			return cal;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Calendar getStartDate(String details) {
		int startingIndex = 22; // new info starts after "edit 12345 start date "
		try {
			String dateString = details.substring(startingIndex);
			ParserDate startDate = new ParserDate();
			Calendar cal = startDate.getDate(dateString);
			return cal;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Calendar getEndDate(String details) {
		int startingIndex = 20; // new info starts after "edit 12345 end date "
		try {
			String dateString = details.substring(startingIndex);
			ParserDate endDate = new ParserDate();
			Calendar cal = endDate.getDate(dateString);
			return cal;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Calendar getDueTime(String details) {
		int startingIndex = 20; // new info starts after "edit 12345 due time "
		try {
			String timeString = details.substring(startingIndex);
			ParserTime dueTime = new ParserTime();
			Calendar cal = dueTime.getTime(timeString);
			return cal;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Calendar getDueDate(String details) {
		int startingIndex = 20; // new info starts after "edit 12345 due date "
		try {
			String dateString = details.substring(startingIndex);
			ParserDate dueDate = new ParserDate();
			Calendar cal = dueDate.getDate(dateString);
			return cal;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
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
				tagArrayList.add(tag);
			}
		}
		return tagArrayList;
	}
}
