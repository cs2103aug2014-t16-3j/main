package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserDelete implements ParserCommand {

	public ParserDelete() {
		
	}

	@Override
	public InputData run(Command type) {
		return null;
	}
	
	@Override
	public InputData run(Command type, String input) {
		InputData data = delete(type, input);
		return data;
	}
	
	private InputData delete(Command type, String details) {
		InputData data = new InputData(type);
		if (isValidDelete(details)) {
			String uidString = extractUid(details);
			int uid = Integer.parseInt(uidString);
			data.put(Keys.UID, uid);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return null;
	}
	
	// check if user wrote any uid (5 digit)
	// check if uid is an integer
	// assumes delete command is all in small letters
	private boolean isValidDelete(String input) {
		String deleteString = input.trim();
		int standardLength = 11;
		if (deleteString.length() == standardLength) {
			deleteString = extractUid(deleteString);
			if (isInteger(deleteString)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private String extractUid(String input) {
		String deleteString = input.trim();
		deleteString = deleteString.replace("delete", "");
		return deleteString;
	}
}
