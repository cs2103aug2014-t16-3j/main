//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

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