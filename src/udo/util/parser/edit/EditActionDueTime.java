//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.TimeGetter;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

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
