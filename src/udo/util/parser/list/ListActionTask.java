package udo.util.parser.list;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

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
