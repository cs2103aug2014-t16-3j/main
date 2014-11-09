//@author A0114847B
package udo.parser.util.list;

import udo.data.InputData;
import udo.enums.Command;

public interface ListActionCommand {

	public void fill(Command type, String details, InputData data);
}
