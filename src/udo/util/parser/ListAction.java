//@author A0114847B
package udo.util.parser;

import udo.language.Language.English;
import udo.util.parser.list.ParserListAll;
import udo.util.parser.list.ParserListCommand;
import udo.util.parser.list.ParserListDate;
import udo.util.parser.list.ParserListDone;
import udo.util.parser.list.ParserListEvent;
import udo.util.parser.list.ParserListHashtag;
import udo.util.parser.list.ParserListPlan;
import udo.util.parser.list.ParserListTask;
import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ListAction implements Action {
	
	/**
	 * This class takes in the field to be displayed.
	 * Input recieve by this class is in the format: 
	 * "list <<field>>"
	 */
	
	String mFields[] = {"#", English.ALL, English.DONE, 
						English.EVENT, English.TASK, 
						English.PLAN, "/", English.DAY, English.TOMORROW};

	@Override
	public InputData run(Command type, String details) {
		InputData data = new InputData(type);
		int field = getField(details);
		ParserListCommand list;
		switch (field) {
			case 0 :
				list = new ParserListHashtag();
				list.fill(type, details, data);
				break;
			case 1 :
				list = new ParserListAll();
				list.fill(type, details, data);
				break;
			case 2 :
				list = new ParserListDone();
				list.fill(type, details, data);
				break;
			case 3 :
				list = new ParserListEvent();
				list.fill(type, details, data);
				break;
			case 4 :
				list = new ParserListTask();
				list.fill(type, details, data);
				break;
			case 5 :
				list = new ParserListPlan();
				list.fill(type, details, data);
				break;
			case 6 :
				list = new ParserListDate();
				list.fill(type, details, data);
				break;
			case 7 :
				list = new ParserListDate();
				list.fill(type, details, data);
				break;
			case 8 :
				list = new ParserListDate();
				list.fill(type, details, data);
				break;
			default:
				data.setParsingStatus(ParsingStatus.FAIL);
				break;
		}
		return data;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}
	
	private int getField(String input) {
		String field = input.toLowerCase();
		for (int i = 0; i < mFields.length; i++) {
			if (field.contains(mFields[i])) {
				return i;
			}
		}
		return 0;
	}

}
