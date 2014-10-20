package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

public class ParserListAll implements ParserListCommand {

	public ParserListAll() {
		
	}
	
	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.ALL);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}