//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;

public interface ParserCommand {
	
	public InputData run(Command type, String input);
	public InputData run(Command type);
}
