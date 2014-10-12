package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.InputData;

/** 
 * This class takes in ADD commands and break them into the following:
 * ADD_EVENT, ADD_TASK, ADD_PLAN
 * <p>
 * ADD_EVENT command is for events. Events contain a starting time and an ending time.
 * ADD_TASK command is for tasks. Tasks contain 1 deadline.
 * ADD_PLAN command is for plans. Plans have no deadlines nor starting and ending time.
 * 
 * @author chongjiawei
 *
 */
public class ParserAdd {

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
	public InputData add(Command type, String details) {
		InputData add = null;
		if (isEvent(details)) {
			add = new InputData(Command.ADD_EVENT);
			// tags
		} else if (isTask(details)) {
			add = new InputData(Command.ADD_TASK);
			// tags
			// check if valid task
		} else if (isPlan(details)) {
			add = new InputData(Command.ADD_PLAN);
			// tags
			// check if valid plan
		} else {
			// Parsing Status failed
		}
		return add;
	}

	// checks if there is 2 date and 2 time
	// 
	private boolean isEvent(String details) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isTask(String details) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isPlan(String details) {
		// TODO Auto-generated method stub
		return false;
	}
}
