//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserDate;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditEndDate implements ParserEditCommand {

	public ParserEditEndDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		int startingIndex = 20; // new info starts after "edit 12345 end date "
		if (details.length() > startingIndex) {
			ParserDate d = new ParserDate();
			Calendar endDate = d.getDate(details);
			data.put(Keys.FIELD, EditField.END_DATE);
			data.put(Keys.VALUE, endDate);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
