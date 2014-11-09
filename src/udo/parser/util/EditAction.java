//@author A0114847B
package udo.parser.util;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ParsingStatus;
import udo.parser.util.edit.EditActionDueDate;
import udo.parser.util.edit.EditActionDueTime;
import udo.parser.util.edit.EditActionEndDate;
import udo.parser.util.edit.EditActionEndTime;
import udo.parser.util.edit.EditActionField;
import udo.parser.util.edit.EditActionStartDate;
import udo.parser.util.edit.EditActionStartTime;
import udo.parser.util.edit.EditActionTitle;

/**
 * This class handles all editing done by the user.
 * Input recieve by this class is in the format: 
 * "edit <<uid>> <<field to edit>> <<new info>>"
 */

public class EditAction implements Action {
	
	String[] mFields = {
			mLang.getTITLE(),
			mLang.getSTART_TIME(), 
			mLang.getEND_TIME(), 
			mLang.getSTART_DATE(), 
			mLang.getEND_DATE(),
			mLang.getDUE_TIME(), 
			mLang.getDUE_DATE()};
	
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
		if (field.equals(mLang.getTITLE())) {
			setTitle(data, details);
			
		} else if (field.equals(mLang.getSTART_TIME())) {
			setStartTime(data, details);
			
		} else if (field.equals(mLang.getEND_TIME())) {
			setEndTime(data, details);
			
		} else if (field.equals(mLang.getSTART_DATE())) {
			setStartDate(data, details);
			
		} else if (field.equals(mLang.getEND_DATE())) {
			setEndDate(data, details);
			
		} else if (field.equals(mLang.getDUE_TIME())) {
			setDueTime(data, details);
			
		} else if (field.equals(mLang.getDUE_DATE())) {
			setDueDate(data, details);
			
		} else {
			// do nothing
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
