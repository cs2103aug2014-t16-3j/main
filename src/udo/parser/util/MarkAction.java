//@author A0114847B
package udo.parser.util;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ParsingStatus;

public class MarkAction implements Action {

	/**
	 * This class takes in the items that marked completed.
	 * Input recieve by this class is in the format: 
	 * "done <<uid>>"
	 */
	
	public MarkAction() {
		
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
