//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.DateGetter;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

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
