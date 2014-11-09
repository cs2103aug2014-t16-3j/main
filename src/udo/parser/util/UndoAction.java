//@author A0114847B
package udo.parser.util;

import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ParsingStatus;

public class UndoAction implements Action {

	/**
	 * This class undo previous commands
	 * Input recieve by this class is in the format: 
	 * "undo"
	 */
	
	public UndoAction() {
		
	}

	@Override
	public InputData run(Command type) {
		InputData undoInputData = new InputData(type);
		undoInputData.setParsingStatus(ParsingStatus.SUCCESS);
		return undoInputData;
	}

	@Override
	public InputData run(Command type, String input) {
		return null;
	}

}
