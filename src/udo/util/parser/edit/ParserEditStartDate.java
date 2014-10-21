package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserDate;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditStartDate implements ParserEditCommand {

	public ParserEditStartDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		int startingIndex = 22; // new info starts after "edit 12345 start date "
		if (details.length() > startingIndex) {
			ParserDate d = new ParserDate();
			Calendar startDate = d.getDate(details);
			data.put(Keys.FIELD, EditField.START_DATE);
			data.put(Keys.VALUE, startDate);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
