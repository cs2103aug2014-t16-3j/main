package udo.main;

import udo.util.parser.ParserAdd;
import udo.util.parser.ParserCommand;
import udo.util.parser.ParserDelete;
import udo.util.parser.ParserEdit;
import udo.util.parser.ParserExit;
import udo.util.parser.ParserList;
import udo.util.parser.ParserSave;
import udo.util.parser.ParserTrash;
import udo.util.parser.ParserUndo;
import udo.util.shared.Command;
import udo.util.shared.InputData;

/**
 * This class parses information from the input string and package it 
 * as an InputData object. 
 * <p>
 * It reads in ADD, LIST, DELETE, EDIT, SAVE, EXIT and UNDO commands.
 * Parser stores the keys using Keys class constants. 
 */

public class Parser {
	
	public Parser() {
		//implement singleton pattern
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
	
	private Command determineCommandType(String input) {
		String parts[] = input.split(" ");
		String command = parts[0];
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

	private InputData processCommandType(Command commandType, String details) {
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
	
	private InputData add(Command type, String details) {
		ParserCommand add = new ParserAdd();
		InputData data = add.run(type, details);
		return data;
	}
	
	private InputData list(Command type, String details) {
		ParserCommand list = new ParserList();
		InputData data = list.run(type, details);
		return data;
	}

	private InputData edit(Command type, String details) {
		ParserEdit activity = new ParserEdit();
		return activity.edit(type, details);
	}
	
	private InputData delete(Command type, String details) {
		ParserCommand delete = new ParserDelete();
		InputData data = delete.run(type, details);
		return data;
	}
	
	private InputData parsingTrash(Command type, String details) {
		ParserCommand trash = new ParserTrash();
		InputData data = trash.run(type);
		return data;
	}

	private InputData undo(Command type, String details) {
		ParserCommand undo = new ParserUndo();
		InputData data = undo.run(type);
		return data;
	}

	private InputData save(Command type, String details) {
		ParserCommand save = new ParserSave();
		InputData data = save.run(type);
		return data;
	}

	private InputData exit(Command type, String details) {
		ParserCommand exit = new ParserExit();
		InputData data = exit.run(type);
		return data;
	}
}
