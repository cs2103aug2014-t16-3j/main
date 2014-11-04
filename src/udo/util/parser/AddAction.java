//@author A0114847B
package udo.util.parser;

import udo.language.Language.English;
import udo.util.parser.add.AddActionType;
import udo.util.parser.add.AddActionEvent;
import udo.util.parser.add.AddActionPlan;
import udo.util.parser.add.AddActionTask;
import udo.util.shared.Command;
import udo.util.shared.InputData;


/** 
 * This class takes in ADD commands and break them into the following:
 * ADD_EVENT, ADD_TASK, ADD_PLAN
 * <p>
 * ADD_EVENT command is for events. Events contain a starting time and an ending time.
 * ADD_TASK command is for tasks. Tasks contain 1 deadline.
 * ADD_PLAN command is for plans. Plans have no deadlines nor starting and ending time.
 * <p>
 * Input recieve by this class is in the format: 
 * "add <<title>> from <<date>> <<time>> to <<date>> <<time>>"
 * "add <<title>> from <<date>> <<time>> to <<time>>"
 * "add <<title>> by <<date>> <<time>>"
 * "add <<title>>"
 */

public class AddAction implements Action {
	
	public AddAction() {
		
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
			AddActionType event = new AddActionEvent();
			event.fill(details, data);
		} else if (isTask(details)) {
			data = new InputData(Command.ADD_TASK);
			AddActionType task = new AddActionTask();
			task.fill(details, data);
		} else {
			data = new InputData(Command.ADD_PLAN);
			AddActionType plan = new AddActionPlan();
			plan.fill(details, data);
		} 
		return data;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}

	private boolean isEvent(String details) {
		String task = details.toLowerCase();
		task = task.replaceAll("#", "");
		String parts[] = task.split(" ");
		boolean containsKeywordFrom = false;
		boolean containsKeywordTo = false;
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].equals(English.FROM)) {
				containsKeywordFrom = true;
			}
			if (parts[i].equals(English.TO)) {
				containsKeywordTo = true;
			}
		}
		if (containsKeywordFrom && containsKeywordTo) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isTask(String details) {
		String task = details.toLowerCase();
		task = task.replaceAll("#", "");
		String parts[] = task.split(" ");
		boolean containsKeywordBy = false;
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].equals(English.BY)) {
				containsKeywordBy = true;
			}
		}
		if (containsKeywordBy) {
			return true;
		} else {
			return false;
		}
	}

}
