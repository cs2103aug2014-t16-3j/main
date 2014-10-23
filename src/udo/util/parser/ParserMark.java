package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserMark implements ParserCommand {

	public ParserMark() {
		
	}

	@Override
	public InputData run(Command type, String input) {
		InputData data = new InputData(type);
		if (isValidMark(input)) {
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
	
	// standardLenght is the assumed length the input without space should be. "mark12345"
	private boolean isValidMark(String input) {
		String details = input.replaceAll("\\s","");
		int standardLength = 9;
		if (details.length() == standardLength) {
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
		if (parts.length >= 2) {
			String uid = parts[1];
			if (isInteger(uid)) {
				int id = Integer.parseInt(uid);
				return id;
			}
		} 
		return -1;
	}

}
