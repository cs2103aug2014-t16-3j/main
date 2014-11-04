package udo.util.parser.list;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserListEvent implements ParserListCommand {

	/**
	 * This class handles the event field
	 */
	
	public ParserListEvent() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.EVENT);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}
