//@author A0114847B
package udo.parser.util.edit;

import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.EditField;
import udo.enums.ParsingStatus;
import udo.parser.util.DateGetter;

public class EditActionEndDate implements EditActionField {

	/**
	 * This class handles the end date field
	 */
	
	public EditActionEndDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		DateGetter d = new DateGetter();
		Calendar endDate = d.getDate(details);
		if (endDate != null) {
			data.put(Keys.FIELD, EditField.END_DATE);
			data.put(Keys.VALUE, endDate);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);	
		}
	}

}
