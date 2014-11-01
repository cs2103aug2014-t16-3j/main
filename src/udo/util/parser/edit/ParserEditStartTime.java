//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserTime;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditStartTime implements ParserEditCommand {

	public ParserEditStartTime() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		int startingIndex = 22; // new info starts after "edit 12345 start time "
		if (details.length() > startingIndex) {
			ParserTime time = new ParserTime();
			Calendar startTime = time.getTime(details);
			if (startTime != null) {
				data.put(Keys.FIELD, EditField.START_TIME);
				data.put(Keys.VALUE, startTime);
				data.setParsingStatus(ParsingStatus.SUCCESS);
			} else {
				data.setParsingStatus(ParsingStatus.FAIL);
			}
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}


}
