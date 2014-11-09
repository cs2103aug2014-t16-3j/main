package udo.parser.util.list;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;

public class ListActionPlan implements ListActionCommand {

	/**
	 * This class handles the plans field
	 */
	public ListActionPlan() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.PLAN);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}
