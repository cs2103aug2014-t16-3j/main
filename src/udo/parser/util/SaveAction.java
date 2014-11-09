//@author A0114847B
package udo.parser.util;

import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ParsingStatus;

public class SaveAction implements Action {

	/**
	 * This class handles the save command.
	 * Input recieve by this class is in the format: 
	 * "save"
	 */
	
	public SaveAction() {

	}

	@Override
	public InputData run(Command type) {
		InputData saveInputData = new InputData(type);
		saveInputData.setParsingStatus(ParsingStatus.SUCCESS);
		return saveInputData;
	}

	@Override
	public InputData run(Command type, String input) {
		return null;
	}

}
