package udo.util.parser;

import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.InputData;

/**
 * This class handles all editing done by the user.
 * 
 */

public class ParserEdit {
	
	private int startingIndex = 6;
	// assume edit details starts after edit command, e.g. "edit <fields>"

	public ParserEdit() {
		
	}
	//edit <uid> <field> <new-info>
	public InputData edit(Command type, String details) {
		InputData data = new InputData(type);
		
		return data;
	}
	
	public int getUid(String details) {
		return 0;
	}
	
	public String getField(String details) {
		return null;
	}
	
	public String getTitle(String details) {
		return null;
	}
	
	public Calendar getStartTime(String details) {
		return null;
	}
	
	public Calendar getEndTime(String details) {
		return null;
	}
	
	public Calendar getStartDate(String details) {
		return null;
	}
	
	public Calendar getEndDate(String details) {
		return null;
	}
	
	public Calendar getDueTime(String details) {
		return null;
	}
	
	public Calendar getDueDate(String details) {
		return null;
	}
}
