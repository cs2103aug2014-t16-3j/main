//@author A0114847B
package udo.util.parser.add;

import java.util.ArrayList;

import udo.util.shared.InputData;

public interface AddActionType {

	public void fill(String details, InputData data);
	public String getTitle(String input);
	public ArrayList<String> getTags(String input);
	
}
