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
		InputData data = new InputData(type);
		if (isValidDelete(input)) {
			int uid = extractUid(input);
			data.put(Keys.UID, uid);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return data;
	}

	// standardLenght is the assumed length the input without space should be. "delete12345"
	private boolean isValidDelete(String input) {
		String deleteString = input.replaceAll("\\s","");
		int standardLength = 11;
		if (deleteString.length() == standardLength) {
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
