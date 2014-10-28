//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserToggleDone implements ParserCommand {

	public ParserToggleDone() {
		
	}

	@Override
	public InputData run(Command type, String input) {
		InputData data = new InputData(type);
		if (isValidDone(input)) {
			int uid = extractUid(input);
			data.put(Keys.UID, uid);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return data;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}
	
	// check if user wrote any uid (5 digit)
	// check if uid is an integer
	// standardLenght is the assumed length the input without space should be. "toggledone12345"
	private boolean isValidDone(String input) {
		String doneString = input.replaceAll("\\s","");
		int standardLength = 15;
		if (doneString.length() == standardLength) {
			int uid = extractUid(input);
			if (uid != -1) {
				return true;
			} 
		} 
		return false;
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
