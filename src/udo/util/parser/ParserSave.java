//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserSave implements ParserCommand {

	public ParserSave() {

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
