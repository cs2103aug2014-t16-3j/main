package udo.util.parser;

import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.InputData;

/**
 * This class handles all editing done by the user.
 * 
 */

public class ParserEdit {
	
	// private int startingIndex = 6;
	// assume edit details starts after edit command, e.g. "edit <fields>"

	public ParserEdit() {
		
	}
	//edit <uid> <field> <new-info>
	public InputData edit(Command type, String details) {
		InputData data = new InputData(type);
		// check for null
		// put tag
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
		for (int i = 0; i < fields.length; i++) {
			if (details.contains(fields[i])) {
				return fields[i];
			}
		}
		return null;
	}
	
	// returns the new title if it exists
	// otherwise returns null
	public String getTitle(String details) {
		int startingIndex = 11; // new info starts after "edit title "
		try {
			String title = details.substring(startingIndex);
			return title;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Calendar getStartTime(String details) {
		int startingIndex = 16; // new info starts after "edit start time "
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
		int startingIndex = 14; // new info starts after "edit end time "
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
		int startingIndex = 16; // new info starts after "edit start date "
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
		int startingIndex = 14; // new info starts after "edit end date "
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
		int startingIndex = 14; // new info starts after "edit due time "
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
		int startingIndex = 14; // new info starts after "edit due date "
		try {
			String dateString = details.substring(startingIndex);
			ParserDate dueDate = new ParserDate();
			Calendar cal = dueDate.getDate(dateString);
			return cal;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}
