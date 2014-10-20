package udo.util.parser;

import java.util.ArrayList;

import udo.util.shared.Command;
import udo.util.shared.InputData;

public interface ParserAddCommand {

	public void fill(String details, InputData data);
	public String getTitle(String input);
	public ArrayList<String> getTags(String input);
}
