package udo.util.parser;

import java.util.ArrayList;

import udo.util.parser.edit.ParserEditCommand;
import udo.util.parser.edit.ParserEditDueDate;
import udo.util.parser.edit.ParserEditDueTime;
import udo.util.parser.edit.ParserEditEndDate;
import udo.util.parser.edit.ParserEditEndTime;
import udo.util.parser.edit.ParserEditStartDate;
import udo.util.parser.edit.ParserEditStartTime;
import udo.util.parser.edit.ParserEditTitle;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

/**
 * This class handles all editing done by the user.
 * Input recieve by this class is in the format: 
 * "edit <<uid>> <<field to edit>> <<new info>>"
 */

public class ParserEdit {

	public ParserEdit() {
	
	}
	
	//edit <uid> <field> <new-info>
	public InputData edit(Command type, String details) {
		InputData data = new InputData(type);
		int uid = getUid(details);
		String field = getField(details);
		
		if (uid != -1 && field != null) {
			data.put(Keys.UID, uid);
			updateField(field, data, details);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return data;
	}
	
	private void updateField(String field, InputData data, String details) {
		switch (field) {
		case "title":
			setTitle(data, details);
			break;
		case "start time":
			setStartTime(data, details);
			break;
		case "end time":
			setEndTime(data, details);
			break;
		case "start date":
			setStartDate(data, details);
			break;
		case "end date":
			setEndDate(data, details);
			break;
		case "due time":
			setDueTime(data, details);
			break;
		case "due date":
			setDueDate(data, details);
			break;
		default:
			break;
		}
	}
	// check whether any of the value returned is null
	private void setTitle(InputData data, String details) {
		ParserEditCommand title = new ParserEditTitle();
		title.fill(details, data);
	}
	
	private void setStartTime(InputData data, String details) {
		ParserEditCommand startTime = new ParserEditStartTime();
		startTime.fill(details, data);
	}
	
	private void setEndTime(InputData data, String details) {
		ParserEditCommand endTime = new ParserEditEndTime();
		endTime.fill(details, data);
	}
	
	private void setStartDate(InputData data, String details) {
		ParserEditCommand startDate = new ParserEditStartDate();
		startDate.fill(details, data);
	}
	
	private void setEndDate(InputData data, String details) {
		ParserEditCommand endDate = new ParserEditEndDate();
		endDate.fill(details, data);
	}
	
	private void setDueTime(InputData data, String details) {
		ParserEditCommand dueTime = new ParserEditDueTime();
		dueTime.fill(details, data);
	}
	
	private void setDueDate(InputData data, String details) {
		ParserEditCommand dueDate = new ParserEditDueDate();
		dueDate.fill(details, data);
	}
	// returns uid if it exists
	// otherwise returns -1
	private int getUid(String details) {
		String[] words = details.split(" ");
		if (words.length > 1) {
			String uidString = words[1];
			int uid = -1;
			try {
				 uid = Integer.parseInt(uidString);
			} catch (NumberFormatException nfe) {
				
			}
			return uid;
		} else {
			return -1;
		}
	}
	
	// returns one of the fields if it exists
	// otherwise returns null
	private String getField(String details) {
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
	
}
