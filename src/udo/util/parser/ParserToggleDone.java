//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserToggleDone implements ParserCommand {

	/**
	 * This class takes in the items that marked as not completed from the completed list.
	 * Input recieve by this class is in the format: 
	 * "toggle done <<uid>>"
	 */
	
	public ParserToggleDone() {
		
	}

	@Override
	public InputData run(Command type, String input) {
		InputData data = new InputData(type);
		int uid = extractUid(input);
		if (uid == -1) {
			data.setParsingStatus(ParsingStatus.FAIL);	
		} else {
			data.put(Keys.UID, uid);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		}
		return data;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}

	private boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private int extractUid(String input) {
		String parts[] = input.split(" ");
		if (parts.length >= 3) {
			String uid = parts[2];
			if (isInteger(uid)) {
				int id = Integer.parseInt(uid);
				return id;
			}
		} 
		return -1;
	}
}
