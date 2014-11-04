//@author A0114847B
package udo.util.parser.list;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

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
