//@author A0114847B
package udo.util.parser.list;

import udo.util.shared.Command;
import udo.util.shared.InputData;

public interface ListActionCommand {

	public void fill(Command type, String details, InputData data);
}
