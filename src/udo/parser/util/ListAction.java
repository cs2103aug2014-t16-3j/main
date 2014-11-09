//@author A0114847B
package udo.parser.util;

import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ParsingStatus;
import udo.parser.util.list.ListActionAll;
import udo.parser.util.list.ListActionCommand;
import udo.parser.util.list.ListActionDate;
import udo.parser.util.list.ListActionDone;
import udo.parser.util.list.ListActionEvent;
import udo.parser.util.list.ListActionHashtag;
import udo.parser.util.list.ListActionPlan;
import udo.parser.util.list.ListActionTask;

public class ListAction implements Action {
	
	/**
	 * This class takes in the field to be displayed.
	 * Input recieve by this class is in the format: 
	 * "list <<field>>"
	 */
	
	String mFields[] = {"#", 
			mLang.getALL(), 
			mLang.getDONE(), 
			mLang.getEVENT(), 
			mLang.getTASK(), 
			mLang.getPLAN(), 
			"/", 
			mLang.getDAY(), 
			mLang.getTOMORROW()};

	@Override
	public InputData run(Command type, String details) {
		InputData data = new InputData(type);
		int field = getField(details);
		ListActionCommand list;
		switch (field) {
			case 0 :
				list = new ListActionHashtag();
				list.fill(type, details, data);
				break;
			case 1 :
				list = new ListActionAll();
				list.fill(type, details, data);
				break;
			case 2 :
				list = new ListActionDone();
				list.fill(type, details, data);
				break;
			case 3 :
				list = new ListActionEvent();
				list.fill(type, details, data);
				break;
			case 4 :
				list = new ListActionTask();
				list.fill(type, details, data);
				break;
			case 5 :
				list = new ListActionPlan();
				list.fill(type, details, data);
				break;
			case 6 :
				list = new ListActionDate();
				list.fill(type, details, data);
				break;
			case 7 :
				list = new ListActionDate();
				list.fill(type, details, data);
				break;
			case 8 :
				list = new ListActionDate();
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
