//@author A0114847B
package udo.parser.util.list;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;

public class ListActionDone implements ListActionCommand {

	/**
	 * This class handles the done field
	 */
	
	public ListActionDone() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.DONE);
		data.setParsingStatus(ParsingStatus.SUCCESS);

	}

}
