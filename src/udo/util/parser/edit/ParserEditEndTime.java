package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserTime;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditEndTime implements ParserEditCommand {

	public ParserEditEndTime() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		int startingIndex = 20; // new info starts after "edit 12345 end time "
		if (details.length() > startingIndex) {
			ParserTime time = new ParserTime();
			Calendar endTime = time.getTime(details);
			if (endTime != null) {
				data.put(Keys.FIELD, EditField.END_TIME);
				data.put(Keys.VALUE, endTime);
				data.setParsingStatus(ParsingStatus.SUCCESS);
			} else {
				data.setParsingStatus(ParsingStatus.FAIL);
			}
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
