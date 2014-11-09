package udo.parser.util.list;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;

public class ListActionEvent implements ListActionCommand {

	/**
	 * This class handles the event field
	 */
	
	public ListActionEvent() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.EVENT);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}
