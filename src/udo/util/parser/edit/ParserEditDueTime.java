//@author A0114847B
package udo.util.parser.edit;

import java.util.Calendar;

import udo.util.parser.ParserTime;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditDueTime implements ParserEditCommand {

	public ParserEditDueTime() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		int startingIndex = 20; // new info starts after "edit 12345 due time "
		if (details.length() > startingIndex) {
			ParserTime time = new ParserTime();
			Calendar dueTime = time.getTime(details);
			data.put(Keys.FIELD, EditField.DUE_TIME);
			data.put(Keys.VALUE, dueTime);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
