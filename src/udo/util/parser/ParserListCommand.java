package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;

public interface ParserListCommand {

	public void fill(Command type, String details, InputData data);
}
