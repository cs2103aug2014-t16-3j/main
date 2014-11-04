//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserDate;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditDueDate implements ParserEditCommand {

	public ParserEditDueDate() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		ParserDate date = new ParserDate();
		Calendar dueDate = date.getDate(details);
		if (dueDate != null) {
			data.put(Keys.FIELD, EditField.DUE_DATE);
			data.put(Keys.VALUE, dueDate);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
