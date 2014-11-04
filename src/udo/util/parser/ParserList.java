//@author A0114847B
package udo.util.parser;

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

public class ParserList implements ParserCommand {

	@Override
	public InputData run(Command type, String details) {
		InputData data = new InputData(type);
		String lowerCaseDetails = details.toLowerCase();
		ParserListCommand list;
		
		if (details.contains("#")) {
			list = new ParserListHashtag();
			list.fill(type, details, data);
		} else if (details.contains("/") 
				|| lowerCaseDetails.contains("day") 
				|| lowerCaseDetails.contains("tomorrow")) {
			list = new ParserListDate();
			list.fill(type, details, data);
		} else if (lowerCaseDetails.contains("all")){
			list = new ParserListAll();
			list.fill(type, details, data);
		} else if (lowerCaseDetails.contains("done")){
			list = new ParserListDone();
			list.fill(type, details, data);
		} else if (lowerCaseDetails.contains("event")) {
			list = new ParserListEvent();
			list.fill(type, details, data);
		} else if (lowerCaseDetails.contains("task")) {
			list = new ParserListTask();
			list.fill(type, details, data);
		} else if (lowerCaseDetails.contains("plan")) {
			list = new ParserListPlan();
			list.fill(type, details, data);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return data;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}

}
