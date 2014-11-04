//@author A0114847B
package udo.main;

import udo.language.Language.EnglishCommand;
import udo.util.parser.ParserAdd;
import udo.util.parser.ParserCommand;
import udo.util.parser.ParserDelete;
import udo.util.parser.ParserEdit;
import udo.util.parser.ParserExit;
import udo.util.parser.ParserList;
import udo.util.parser.ParserMark;
import udo.util.parser.ParserSave;
import udo.util.parser.ParserToggleDone;
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
		input = input.toLowerCase();
		String parts[] = input.split(" ");
		String command = parts[0];
		command = command.toLowerCase();
		switch (command) {
			case EnglishCommand.ADD:
				return Command.ADD;
			case EnglishCommand.LIST:
				return Command.LIST;
			case EnglishCommand.DELETE:
				return Command.DELETE;
			case EnglishCommand.SAVE:
				return Command.SAVE;
			case EnglishCommand.EXIT:
				return Command.EXIT;
			case EnglishCommand.UNDO:
				return Command.UNDO;
			case EnglishCommand.EDIT:
				return Command.EDIT;
			case EnglishCommand.DONE:
				return Command.MARK_DONE;
			case EnglishCommand.TOGGLE:
				return Command.TOGGLE_DONE;
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
			case TOGGLE_DONE:
				return toggle_done(commandType, details);
			case MARK_DONE:
				return mark(commandType, details);
			default:
				return trash(commandType, details);
			}
	}
	
	private InputData mark(Command type, String details) {
		ParserCommand mark = new ParserMark();
		InputData data = mark.run(type, details);
		return data;
	}

	private InputData toggle_done(Command type, String details) {
		ParserCommand toggleDone = new ParserToggleDone();
		InputData data = toggleDone.run(type, details);
		return data;
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
	
	private InputData trash(Command type, String details) {
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
