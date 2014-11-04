package udo.util.parser.list;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserListTask implements ParserListCommand {

	/**
	 * This class handles the tasks field
	 */
	
	public ParserListTask() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.TASK);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}
