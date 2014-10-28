//@author A0114847B
package udo.util.parser;

import udo.util.parser.add.ParserAddCommand;
import udo.util.parser.add.ParserAddEvent;
import udo.util.parser.add.ParserAddPlan;
import udo.util.parser.add.ParserAddTask;
import udo.util.shared.Command;
import udo.util.shared.InputData;


/** 
 * This class takes in ADD commands and break them into the following:
 * ADD_EVENT, ADD_TASK, ADD_PLAN
 * <p>
 * ADD_EVENT command is for events. Events contain a starting time and an ending time.
 * ADD_TASK command is for tasks. Tasks contain 1 deadline.
 * ADD_PLAN command is for plans. Plans have no deadlines nor starting and ending time.
 */

public class ParserAdd implements ParserCommand {
	
	public ParserAdd() {
		
	}
	
	/**
	 * This method takes in user input and returns an InputData of the following 
	 * CommandType: ADD_EVENT, ADD_TASK, ADD_PLAN
	 * and the stores them in the necessary fields.
	 * 
	 * @param type
	 * @param details
	 * @return an InputData
	 */
	@Override
	public InputData run(Command type, String details) {
		InputData data;
		if (isEvent(details)) {
			data = new InputData(Command.ADD_EVENT);
			ParserAddCommand event = new ParserAddEvent();
			event.fill(details, data);
		} else if (isTask(details)) {
			data = new InputData(Command.ADD_TASK);
			ParserAddCommand task = new ParserAddTask();
			task.fill(details, data);
		} else {
			data = new InputData(Command.ADD_PLAN);
			ParserAddCommand plan = new ParserAddPlan();
			plan.fill(details, data);
		} 
		return data;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}

	// checks if there is 2 date or 2 time in input
	private boolean isEvent(String details) {
		if (details.contains("from") &&
			details.contains("to")) {
			return true;
		} else {
			return false;
		}
	}

	// checks if there is 1 time or 1 date in input
	private boolean isTask(String details) {
		if (details.contains("by")) {
			return true;
		} else {
			return false;
		}
	}

}
