//@author A0114847B
package udo.parser;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import udo.constants.Constants.LoggingStrings;
import udo.data.InputData;
import udo.enums.Command;
import udo.language.LanguagePack;
import udo.parser.util.Action;
import udo.parser.util.AddAction;
import udo.parser.util.DeleteAction;
import udo.parser.util.EditAction;
import udo.parser.util.ExitAction;
import udo.parser.util.ListAction;
import udo.parser.util.MarkAction;
import udo.parser.util.SaveAction;
import udo.parser.util.SearchAction;
import udo.parser.util.ToggleDoneAction;
import udo.parser.util.TrashAction;
import udo.parser.util.UndoAction;

/**
 * This class parses information from the input string and package it 
 * as an InputData object. 
 * <p>
 * It reads all enum Command commands.
 * Parser stores the keys using Keys class constants. 
 */

public class Parser {
	
	private LanguagePack mLang;
	private Logger mLogger;
	
	public Parser() {
		mLang = LanguagePack.getInstance();
		mLogger = Logger.getLogger(Parser.class.getSimpleName());
		try {
			new File(LoggingStrings.LOGPATH_PARSER).mkdirs();
			mLogger.addHandler(new FileHandler(LoggingStrings.LOGFILE_PARSER));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mLogger.setLevel(Level.ALL);
	}
	
	/**
	 * Parses raw user's input and return it as an InputData object.
	 * Fields are filled accordingly to commands, which is the first word in the string
	 * 
	 * @param input string 
	 * @return InputData object
	 */
	public InputData getInputData(String input) {
		assert(input != null);
		Command type = determineCommandType(input);
		InputData data = processCommandType(type, input);
		assert(data != null);
		assert(data.getStatus() != null);
		return data;
	}
	
	private Command determineCommandType(String input) {
		input = input.toLowerCase();
		String parts[] = input.split(" ");
		String command = parts[0];
		command = command.toLowerCase();
		
		if (command.equals(mLang.getADD())) {
			return Command.ADD;
			
		} else if (command.equals(mLang.getLIST())) {
			return Command.LIST;
			
		} else if (command.equals(mLang.getDELETE())) {
			return Command.DELETE;
			
		} else if (command.equals(mLang.getSAVE())) {
			return Command.SAVE;
			
		} else if (command.equals(mLang.getEXIT())) {
			return Command.EXIT;
			
		} else if (command.equals(mLang.getUNDO())) {
			return Command.UNDO;
			
		} else if (command.equals(mLang.getEDIT())) {
			return Command.EDIT;
			
		} else if (command.equals(mLang.getMARK_DONE())) {
			return Command.MARK_DONE;
			
		} else if (command.equals(mLang.getTOGGLE_DONE())) {
			return Command.TOGGLE_DONE;
			
		} else if (command.equals(mLang.getSEARCH())) {
			return Command.SEARCH;
			
		} else {
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
				
			case SEARCH :
				return search(commandType, details);
				
			default:
				mLogger.log(Level.WARNING, "Command not typed", details);
				return trash(commandType, details);
			}
	}
	
	private InputData search(Command type, String details) {
		Action search = new SearchAction();
		InputData data = search.run(type, details);
		return data;
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
