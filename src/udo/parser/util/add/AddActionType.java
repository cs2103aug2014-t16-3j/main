//@author A0114847B
package udo.parser.util.add;

import java.util.ArrayList;

import udo.data.InputData;
import udo.language.LanguagePack;

public interface AddActionType {
	public final LanguagePack mLang = LanguagePack.getInstance();
	public void fill(String details, InputData data);
	public String getTitle(String input);
	public ArrayList<String> getTags(String input);
	
}
