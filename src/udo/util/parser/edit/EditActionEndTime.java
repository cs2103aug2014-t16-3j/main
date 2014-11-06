//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.TimeGetter;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

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
