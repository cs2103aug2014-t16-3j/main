//@author A0114847B
package udo.parser.util.edit;

import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.EditField;
import udo.enums.ParsingStatus;
import udo.parser.util.DateGetter;

public class EditActionDueDate implements EditActionField {

	/**
	 * This class handles the due date field
	 */
	
	public EditActionDueDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		DateGetter date = new DateGetter();
		Calendar dueDate = date.getDate(details);
		if (dueDate != null) {
			data.put(Keys.FIELD, EditField.DUE_DATE);
			data.put(Keys.VALUE, dueDate);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
