//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserDate;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditEndDate implements ParserEditCommand {

	/**
	 * This class handles the end date field
	 */
	
	public ParserEditEndDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		ParserDate d = new ParserDate();
		Calendar endDate = d.getDate(details);
		if (endDate != null) {
			data.put(Keys.FIELD, EditField.END_DATE);
			data.put(Keys.VALUE, endDate);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);	
		}
	}

}
