package udo.util.parser.list;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserListPlan implements ParserListCommand {

	public ParserListPlan() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.PLAN);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}