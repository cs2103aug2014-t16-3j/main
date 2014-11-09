//@author A0114847B
package udo.parser.util;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ParsingStatus;

public class DeleteAction implements Action {
	
	/**
	 * This class reads in the uid to be deleted.
	 * Input recieved by this class is in the format: "delete <<uid>>"
	 */

	public DeleteAction() {
		
	}

	@Override
	public InputData run(Command type) {
		return null;
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
