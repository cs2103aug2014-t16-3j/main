//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserDate;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditStartDate implements ParserEditCommand {

	/**
	 * This class handles the start date field
	 */
	
	public ParserEditStartDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		ParserDate d = new ParserDate();
		Calendar startDate = d.getDate(details);
		if (startDate != null) {
			data.put(Keys.FIELD, EditField.START_DATE);
			data.put(Keys.VALUE, startDate);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
