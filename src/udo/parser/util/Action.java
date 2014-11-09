//@author A0114847B
package udo.parser.util;

import udo.data.InputData;
import udo.enums.Command;
import udo.language.LanguagePack;

public interface Action {
	public final LanguagePack mLang = LanguagePack.getInstance();
	public InputData run(Command type, String input);
	public InputData run(Command type);
}
