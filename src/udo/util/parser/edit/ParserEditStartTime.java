//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserTime;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditStartTime implements ParserEditCommand {

	/**
	 * This class handles the start time field
	 */
	
	public ParserEditStartTime() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		ParserTime time = new ParserTime();
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
