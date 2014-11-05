//@author A0114847B
package udo.util.parser;

import udo.language.LanguagePack;
import udo.util.shared.Command;
import udo.util.shared.InputData;

public interface Action {
	final LanguagePack mLang = LanguagePack.getInstance();
	public InputData run(Command type, String input);
	public InputData run(Command type);
}
