package udo.main;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.parser.ParserAdd;
import udo.util.parser.ParserCommand;
import udo.util.parser.ParserDate;
import udo.util.parser.ParserDelete;
import udo.util.parser.ParserEdit;
import udo.util.parser.ParserExit;
import udo.util.parser.ParserList;
import udo.util.parser.ParserSave;
import udo.util.parser.ParserTime;
import udo.util.parser.ParserTrash;
import udo.util.parser.ParserUndo;
import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

/**
 * This class parses information from the input string and package it 
 * as an InputData object. 
 * <p>
 * It reads in ADD, LIST, DELETE, EDIT, SAVE, EXIT and UNDO commands.
 * Parser stores the keys using Keys class constants. 
 */

public class Parser {
	
	private int largePositiveInt = 100000000;
	private int indexDoesNotExist = -1;
	private int empty = 0;
	// these numbers are constants
	
	// need to refactor the following back into method
	private int determineCommandTypeCommandPart = 0;
	// retrives the first word of input. Assumes first word of input is command word
	
	private int isValidAddint = 4;
	// checks if there is input after "add "
	
	private int getTitleStartingIndex = 4;
	// assume title starts after add command, e.g. "add <title>"
	private int getTitleOffset = -1;
	// removes "#" in title
	
	private int getTagsOffsetIndex = 1; 
	// removes the "#" character from input
	
	private int deleteStartingIndex = 7; 
	// get input after "delete "
	//private int isValidDeleteInt = 8; 
	// checks if there is input after "delete "
	
	public Parser() {

	}
	
	/**
	 * Parses raw user's input and return it as an InputData object.
	 * Fields are filled accordingly to it's Commands 
	 * 
	 * @param input string 
	 * @return InputData object
	 */
	public InputData getInputData(String input) {
		Command type = determineCommandType(input);
		InputData data = processCommandType(type, input);
		return data;
	}
	
	public Command determineCommandType(String input) {
		String parts[] = input.split(" ");
		String command = parts[determineCommandTypeCommandPart];
		switch (command) {
			case "add":
				return Command.ADD;
			case "list":
				return Command.LIST;
			case "delete":
				return Command.DELETE;
			case "save":
				return Command.SAVE;
			case "exit":
				return Command.EXIT;
			case "undo":
				return Command.UNDO;
			case "edit":
				return Command.EDIT;
			default:
				return Command.NULL;
			}
	}

	public InputData processCommandType(Command commandType, String details) {
		switch (commandType) {
			case ADD:
				return add(commandType, details);
			case LIST:
				return list(commandType, details);
			case DELETE:
				return delete(commandType, details);
			case SAVE:
				return save(commandType, details);
			case EXIT:
				return exit(commandType, details);
			case UNDO:
				return undo(commandType, details);
			case EDIT:
				return edit(commandType, details);
			case NULL:
				return parsingTrash(commandType, details);
			default:
				return null; // parsing status fail
			}
	}
	
	public InputData add(Command type, String details) {
		ParserAdd activity = new ParserAdd();
		return activity.add(type, details);
	}
	
	public InputData list(Command type, String details) {
		ParserCommand list = new ParserList();
		InputData data = list.run(type, details);
		return data;
	}

	public InputData edit(Command type, String details) {
		ParserEdit activity = new ParserEdit();
		return activity.edit(type, details);
	}
	
	public InputData delete(Command type, String details) {
		ParserCommand delete = new ParserDelete();
		InputData data = delete.run(type, details);
		return data;
	}
	
	public InputData parsingTrash(Command type, String details) {
		ParserCommand trash = new ParserTrash();
		InputData data = trash.run(type);
		return data;
	}

	public InputData undo(Command type, String details) {
		ParserCommand undo = new ParserUndo();
		InputData data = undo.run(type);
		return data;
	}

	public InputData save(Command type, String details) {
		ParserCommand save = new ParserSave();
		InputData data = save.run(type);
		return data;
	}

	public InputData exit(Command type, String details) {
		ParserCommand exit = new ParserExit();
		InputData data = exit.run(type);
		return data;
	}
}
