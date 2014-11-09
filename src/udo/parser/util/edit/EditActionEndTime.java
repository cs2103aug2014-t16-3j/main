//@author A0114847B
package udo.parser.util.edit;

import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.EditField;
import udo.enums.ParsingStatus;
import udo.parser.util.TimeGetter;

public class EditActionEndTime implements EditActionField {

	/**
	 * This class handles the end time field
	 */
	
	public EditActionEndTime() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		TimeGetter time = new TimeGetter();
		Calendar endTime = time.getTime(details);
		if (endTime != null) {
			data.put(Keys.FIELD, EditField.END_TIME);
			data.put(Keys.VALUE, endTime);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
