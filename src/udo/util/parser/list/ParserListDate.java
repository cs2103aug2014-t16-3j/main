//@author A0114847B
package udo.util.parser.list;

import java.util.Calendar;

import udo.util.parser.ParserDate;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

public class ParserListDate implements ParserListCommand {

	/**
	 * This class handles the date field
	 */
	
	public ParserListDate() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		ParserDate listDate = new ParserDate();
		Calendar date = listDate.getDate(details);
		if (date != null) {
			data.put(Keys.QUERY_TYPE, ListQuery.DATE);
			data.put(Keys.QUERY_VALUE, date);	
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

}
