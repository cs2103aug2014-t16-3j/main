//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.DateGetter;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

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
