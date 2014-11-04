//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ExitAction  implements Action {

	/**
	 * This class handles the exit command.
	 * Input recieve by this class is in the format: 
	 * "exit"
	 */
	
	public ExitAction() {

	}

	@Override
	public InputData run(Command type, String input) {
		return null;
	}

	@Override
	public InputData run(Command type) {
		InputData exitInputData = new InputData(type);
		exitInputData.setParsingStatus(ParsingStatus.SUCCESS);
		return exitInputData;
	}

}
