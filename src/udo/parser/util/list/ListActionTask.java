package udo.parser.util.list;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;

public class ListActionTask implements ListActionCommand {

	/**
	 * This class handles the tasks field
	 */
	
	public ListActionTask() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.TASK);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}
