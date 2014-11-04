//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.TimeGetter;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

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
