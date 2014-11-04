//@author A0114847B
package udo.util.parser;

import udo.language.Language.English;
import udo.util.parser.edit.EditActionDueDate;
import udo.util.parser.edit.EditActionDueTime;
import udo.util.parser.edit.EditActionEndDate;
import udo.util.parser.edit.EditActionEndTime;
import udo.util.parser.edit.EditActionField;
import udo.util.parser.edit.EditActionStartDate;
import udo.util.parser.edit.EditActionStartTime;
import udo.util.parser.edit.EditActionTitle;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

/**
 * This class handles all editing done by the user.
 * Input recieve by this class is in the format: 
 * "edit <<uid>> <<field to edit>> <<new info>>"
 */

public class EditAction implements Action {
	
	String[] mFields = {English.TITLE,
			English.START_TIME, English.END_TIME, 
			English.START_DATE, English.END_DATE,
			English.DUE_TIME, English.DUE_DATE};

	public EditAction() {
	
	}
	
	@Override
	public InputData run(Command type, String details) {
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

	@Override
	public InputData run(Command type) {
		return null;
	}
	
	private void updateField(String field, InputData data, String details) {
		switch (field) {
		case English.TITLE :
			setTitle(data, details);
			break;
		case English.START_TIME :
			setStartTime(data, details);
			break;
		case English.END_TIME :
			setEndTime(data, details);
			break;
		case English.START_DATE :
			setStartDate(data, details);
			break;
		case English.END_DATE :
			setEndDate(data, details);
			break;
		case English.DUE_TIME :
			setDueTime(data, details);
			break;
		case English.DUE_DATE :
			setDueDate(data, details);
			break;
		default:
			break;
		}
	}
	// check whether any of the value returned is null
	private void setTitle(InputData data, String details) {
		EditActionField title = new EditActionTitle();
		title.fill(details, data);
	}
	
	private void setStartTime(InputData data, String details) {
		EditActionField startTime = new EditActionStartTime();
		startTime.fill(details, data);
	}
	
	private void setEndTime(InputData data, String details) {
		EditActionField endTime = new EditActionEndTime();
		endTime.fill(details, data);
	}
	
	private void setStartDate(InputData data, String details) {
		EditActionField startDate = new EditActionStartDate();
		startDate.fill(details, data);
	}
	
	private void setEndDate(InputData data, String details) {
		EditActionField endDate = new EditActionEndDate();
		endDate.fill(details, data);
	}
	
	private void setDueTime(InputData data, String details) {
		EditActionField dueTime = new EditActionDueTime();
		dueTime.fill(details, data);
	}
	
	private void setDueDate(InputData data, String details) {
		EditActionField dueDate = new EditActionDueDate();
		dueDate.fill(details, data);
	}

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
	
	private String getField(String details) {
		details = details.toLowerCase();
		for (int i = 0; i < mFields.length; i++) {
			if (details.contains(mFields[i])) {
				return mFields[i];
			}
		}
		return null;
	}
	
}
