package udo.util.engine;

import udo.util.shared.Command;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class RunnerAdd extends Runner {
	
	public RunnerAdd(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		Command cmd = mInput.getCommand();

		OutputData output = null;
		
		// decide what function to run.
		switch (cmd) {
			case ADD_EVENT :
				output = runAddEvent(mInput);
				break;
			case ADD_TASK :
				output = runAddTask(mInput);
				break;
			case ADD_PLAN :
				output = runAddPlan(mInput);
				break;
			default :
				break;
		}
		
		return output;
	}
	
	private OutputData runAddEvent(InputData input) {
		Command cmd = input.getCommand();
		ParsingStatus pStatus = input.getStatus();
		OutputData output = new OutputData(cmd, pStatus);
		// extract data from inputdata to make an event
		// assume all data fields are present
		ItemData event = new ItemData(ItemType.EVENT);
		if (input.contains(Keys.UID)) {
			// if the inputdata came from undo, then it would have the uid
			event.put(Keys.UID, 
					input.get(Keys.UID));
		} else {
			event.put(Keys.UID, 
					mCache.generateUID());
		}
		event.put(Keys.TITLE, 
				input.get(Keys.TITLE));
		event.put(Keys.START, 
				input.get(Keys.START));
		event.put(Keys.END, 
				input.get(Keys.END));
		event.put(Keys.HASHTAGS, 
				input.get(Keys.HASHTAGS));

		boolean addOK = mCache.addItem(event);
		
		if (addOK) {
			// if added item successfully
			// make output object with the event data inside
			output.setExecutionStatus(ExecutionStatus.SUCCESS);
			output.put(Keys.ITEM, event);
			//storeAddDeleteUndo(Command.ADD_EVENT, event);
		} else {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		}

		return output;
	}

	private OutputData runAddTask(InputData input) {
		Command cmd = input.getCommand();
		ParsingStatus pStatus = input.getStatus();
		OutputData output = new OutputData(cmd, pStatus);
		
		// extract data from inputdata to make an task
		// assume all data fields are present
		// TODO handle data fields missing
		ItemData task = new ItemData(ItemType.TASK);
		if (input.contains(Keys.UID)) {
			// if the inputdata came from undo, then it would have the uid
			task.put(Keys.UID, 
					input.get(Keys.UID));
		} else {
			task.put(Keys.UID, 
					mCache.generateUID());
		}
		task.put(Keys.TITLE, 
				input.get(Keys.TITLE));
		task.put(Keys.DUE, 
				input.get(Keys.DUE));
		task.put(Keys.DONE, 
				false);
		task.put(Keys.HASHTAGS, 
				input.get(Keys.HASHTAGS));
		
		//add to cache
		boolean addOK = mCache.addItem(task);
		if (addOK) {
			output.put(Keys.ITEM, task);
			output.setExecutionStatus(ExecutionStatus.SUCCESS);
			//storeAddDeleteUndo(Command.ADD_TASK, task);
		} else {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		}
		
		return output;
	}
	
	private OutputData runAddPlan(InputData input) {
		Command cmd = input.getCommand();
		ParsingStatus pStatus = input.getStatus();
		OutputData output = new OutputData(cmd, pStatus);
		
		// extract data from inputdata to make an task
		// assume all data fields are present
		// TODO handle data fields missing
		ItemData plan = new ItemData(ItemType.PLAN);
		if (input.contains(Keys.UID)) {
			// if the inputdata came from undo, then it would have the uid
			plan.put(Keys.UID, 
					input.get(Keys.UID));
		} else {
			plan.put(Keys.UID, 
					mCache.generateUID());
		}
		plan.put(Keys.TITLE, 
				input.get(Keys.TITLE));
		plan.put(Keys.DONE, 
				false);
		plan.put(Keys.HASHTAGS, 
				input.get(Keys.HASHTAGS));
		
		//add to cache
		boolean addOK = mCache.addItem(plan);
		if (addOK) {
			output.put(Keys.ITEM, plan);
			output.setExecutionStatus(ExecutionStatus.SUCCESS);
			//storeAddDeleteUndo(Command.ADD_PLAN, plan);
		} else {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		}
		
		return output;
	}

}
