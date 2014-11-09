//@author A0114847B
package udo.parser.util.edit;

import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.EditField;
import udo.enums.ParsingStatus;
import udo.parser.util.DateGetter;

public class EditActionStartDate implements EditActionField {

	/**
	 * This class handles the start date field
	 */
	
	public EditActionStartDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		DateGetter d = new DateGetter();
		Calendar startDate = d.getDate(details);
		if (startDate != null) {
			data.put(Keys.FIELD, EditField.START_DATE);
			data.put(Keys.VALUE, startDate);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
