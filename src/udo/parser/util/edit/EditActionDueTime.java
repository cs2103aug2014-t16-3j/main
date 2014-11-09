//@author A0114847B
package udo.parser.util.edit;

import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.EditField;
import udo.enums.ParsingStatus;
import udo.parser.util.TimeGetter;

public class EditActionDueTime implements EditActionField {

	/**
	 * This class handles the due time field
	 */
	
	public EditActionDueTime() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		TimeGetter time = new TimeGetter();
		Calendar dueTime = time.getTime(details);
		if (dueTime != null) {
			data.put(Keys.FIELD, EditField.DUE_TIME);
			data.put(Keys.VALUE, dueTime);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
