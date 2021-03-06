//@author A0108358B
package udo.engine.util.runners;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.data.ItemData;
import udo.data.OutputData;
import udo.engine.util.Cache;
import udo.engine.util.UndoBin;
import udo.enums.Command;
import udo.enums.ExecutionStatus;
import udo.enums.ItemType;
import udo.enums.ParsingStatus;
import udo.exceptions.CacheAccessException;

public class RunnerAdd extends Runner {
	
	public RunnerAdd(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		// create outputdata with these params
		Command cmd = mInput.getCommand();
		ParsingStatus pStatus = mInput.getStatus();
		OutputData output = new OutputData(cmd, pStatus);
		
		ItemData genericItem = makeGenericItem(mInput);
		ItemData itemToAdd;
		
		// carry out specific add for each itemtype.
		// also adds the final item to the cache.
		switch (cmd) {
			case ADD_EVENT :
				itemToAdd = makeEvent(mInput, genericItem);
				break;
				
			case ADD_TASK :
				itemToAdd = makeTask(mInput, genericItem);
				break;
				
			case ADD_PLAN :
				itemToAdd = makePlan(mInput, genericItem);
				break;
				
			default :
				itemToAdd = null;
				break;
		}
		
		if (itemToAdd == null) {
			// item is null, item type was not correct
			// fail the execution
			output.setExecutionStatus(ExecutionStatus.FAIL);
			
		} else {
			try {
				mCache.addItem(itemToAdd);
				output.setExecutionStatus(ExecutionStatus.SUCCESS);
				output.put(Keys.ITEM, itemToAdd);
				storeUndo(itemToAdd);
				
			} catch (CacheAccessException e) {
				output.setExecutionStatus(ExecutionStatus.FAIL);
			}
			
		}
		
		return output;
	}
	
	private ItemData makeGenericItem(InputData input) {
		// extract data from inputdata to make an event
		ItemData item = new ItemData();
		
		if (input.contains(Keys.UID)) {
			// if the inputdata came from undo, 
			// then it would have the uid
			item.put(Keys.UID, 
					input.get(Keys.UID));
		} else {
			item.put(Keys.UID, 
					mCache.generateUID());
		}
		
		item.put(Keys.TITLE, 
				input.get(Keys.TITLE));
		item.put(Keys.HASHTAGS, 
				input.get(Keys.HASHTAGS));
		
		return item;
	}
	
	private ItemData makeEvent(InputData input, ItemData event) {
		event.setItemType(ItemType.EVENT);
		
		event.put(Keys.START, 
				input.get(Keys.START));
		event.put(Keys.END, 
				input.get(Keys.END));

		return event;
	}

	private ItemData makeTask(InputData input, ItemData task) {
		task.setItemType(ItemType.TASK);
		
		task.put(Keys.DUE, 
				input.get(Keys.DUE));
		task.put(Keys.DONE, 
				false);
		
		return task;
	}
	
	private ItemData makePlan(InputData input, ItemData plan) {
		plan.setItemType(ItemType.PLAN);
		
		plan.put(Keys.DONE, 
				false);
		
		return plan;
	}
	
	private void storeUndo(ItemData item) {
		InputData undoInput = new InputData(Command.DELETE);
		undoInput.put(Keys.UID, item.get(Keys.UID));
		undoInput.setParsingStatus(ParsingStatus.SUCCESS);
		mUndoBin.storeUndo(undoInput);
	}

}
