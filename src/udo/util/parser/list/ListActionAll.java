//@author A0114847B
package udo.util.parser.list;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

public class ListActionAll implements ListActionCommand {

	/**
	 * This class handles the All field
	 */
	
	public ListActionAll() {
		
	}
	
	@Override
	public void fill(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.ALL);
		data.setParsingStatus(ParsingStatus.SUCCESS);
	}

}
