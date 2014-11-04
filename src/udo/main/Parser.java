//@author A0114847B
package udo.main;

import udo.language.Language.English;
import udo.util.parser.Action;
import udo.util.parser.AddAction;
import udo.util.parser.DeleteAction;
import udo.util.parser.EditAction;
import udo.util.parser.ExitAction;
import udo.util.parser.ListAction;
import udo.util.parser.MarkAction;
import udo.util.parser.SaveAction;
import udo.util.parser.ToggleDoneAction;
import udo.util.parser.TrashAction;
import udo.util.parser.UndoAction;
import udo.util.shared.Command;
import udo.util.shared.InputData;

/**
 * This class parses information from the input string and package it 
 * as an InputData object. 
 * <p>
 * It reads all enum Command commands.
 * Parser stores the keys using Keys class constants. 
 */

public class Parser {
	
	public Parser() {

	}
	
	/**
	 * Parses raw user's input and return it as an InputData object.
	 * Fields are filled accordingly to commands, which is the first word in the string
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
			case English.ADD :
				return Command.ADD;
			case English.LIST :
				return Command.LIST;
			case English.DELETE :
				return Command.DELETE;
			case English.SAVE :
				return Command.SAVE;
			case English.EXIT :
				return Command.EXIT;
			case English.UNDO :
				return Command.UNDO;
			case English.EDIT :
				return Command.EDIT;
			case English.DONE :
				return Command.MARK_DONE;
			case English.TOGGLE :
				return Command.TOGGLE_DONE;
			default:
				return Command.NULL;
		}
	}

	private InputData processCommandType(Command commandType, String details) {
		switch (commandType) {
			case ADD :
				return add(commandType, details);
			case LIST :
				return list(commandType, details);
			case DELETE :
				return delete(commandType, details);
			case SAVE :
				return save(commandType, details);
			case EXIT :
				return exit(commandType, details);
			case UNDO :
				return undo(commandType, details);
			case EDIT :
				return edit(commandType, details);
			case TOGGLE_DONE :
				return toggle_done(commandType, details);
			case MARK_DONE :
				return mark(commandType, details);
			default:
				return trash(commandType, details);
			}
	}
	
	private InputData mark(Command type, String details) {
		Action mark = new MarkAction();
		InputData data = mark.run(type, details);
		return data;
	}

	private InputData toggle_done(Command type, String details) {
		Action toggleDone = new ToggleDoneAction();
		InputData data = toggleDone.run(type, details);
		return data;
	}

	private InputData add(Command type, String details) {
		Action add = new AddAction();
		InputData data = add.run(type, details);
		return data;
	}
	
	private InputData list(Command type, String details) {
		Action list = new ListAction();
		InputData data = list.run(type, details);
		return data;
	}

	private InputData edit(Command type, String details) {
		Action edit = new EditAction();
		InputData data = edit.run(type, details);
		return data;
	}
	
	private InputData delete(Command type, String details) {
		Action delete = new DeleteAction();
		InputData data = delete.run(type, details);
		return data;
	}
	
	private InputData trash(Command type, String details) {
		Action trash = new TrashAction();
		InputData data = trash.run(type);
		return data;
	}

	private InputData undo(Command type, String details) {
		Action undo = new UndoAction();
		InputData data = undo.run(type);
		return data;
	}

	private InputData save(Command type, String details) {
		Action save = new SaveAction();
		InputData data = save.run(type);
		return data;
	}

	private InputData exit(Command type, String details) {
		Action exit = new ExitAction();
		InputData data = exit.run(type);
		return data;
	}
}
