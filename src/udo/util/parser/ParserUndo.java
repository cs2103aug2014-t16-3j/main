//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserUndo implements ParserCommand {

	/**
	 * This class undo previous commands
	 * Input recieve by this class is in the format: 
	 * "undo"
	 */
	
	public ParserUndo() {
		
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
