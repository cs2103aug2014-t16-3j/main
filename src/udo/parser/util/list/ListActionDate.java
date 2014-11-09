//@author A0114847B
package udo.parser.util.list;

import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;
import udo.parser.util.DateGetter;

public class ListActionDate implements ListActionCommand {

	/**
	 * This class handles the date field
	 */
	
	public ListActionDate() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		DateGetter listDate = new DateGetter();
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
