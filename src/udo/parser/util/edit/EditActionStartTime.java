//@author A0114847B
package udo.parser.util.edit;

import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.EditField;
import udo.enums.ParsingStatus;
import udo.parser.util.TimeGetter;

public class EditActionStartTime implements EditActionField {

	/**
	 * This class handles the start time field
	 */
	
	public EditActionStartTime() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		TimeGetter time = new TimeGetter();
		Calendar startTime = time.getTime(details);
		if (startTime != null) {
			data.put(Keys.FIELD, EditField.START_TIME);
			data.put(Keys.VALUE, startTime);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}


}
