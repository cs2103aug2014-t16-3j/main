//@author A0108358B
package udo.util.engine.runners;

import udo.util.engine.Cache;
import udo.util.engine.UndoBin;
import udo.util.exceptions.AddToCacheException;
import udo.util.exceptions.CacheAccessException;
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
		// create outputdata with these params
		Command cmd = mInput.getCommand();
		ParsingStatus pStatus = mInput.getStatus();
		OutputData output = new OutputData(cmd, pStatus);
		
		boolean cacheAddOK = false;
		
		ItemData genericItem = makeGenericItem(mInput);
		ItemData itemToAdd;
		
		// carry out specific add for each itemtype.
		// also adds the final item to the cache.
		switch (cmd) {
			case ADD_EVENT :
				itemToAdd = addEvent(mInput, genericItem);
				break;
				
			case ADD_TASK :
				itemToAdd = addTask(mInput, genericItem);
				break;
				
			case ADD_PLAN :
				itemToAdd = addPlan(mInput, genericItem);
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
	
	private ItemData addEvent(InputData input, ItemData event) {
		event.setItemType(ItemType.EVENT);
		
		event.put(Keys.START, 
				input.get(Keys.START));
		event.put(Keys.END, 
				input.get(Keys.END));

		return event;
	}

	private ItemData addTask(InputData input, ItemData task) {
		task.setItemType(ItemType.TASK);
		
		task.put(Keys.DUE, 
				input.get(Keys.DUE));
		task.put(Keys.DONE, 
				false);
		
		//add to cache
		return task;
	}
	
	private ItemData addPlan(InputData input, ItemData plan) {
		plan.setItemType(ItemType.PLAN);
		
		plan.put(Keys.DONE, 
				false);
		
		//add to cache
		return plan;
	}
	
	private void storeUndo(ItemData item) {
		InputData undoInput = new InputData(Command.DELETE);
		undoInput.put(Keys.UID, item.get(Keys.UID));
		undoInput.setParsingStatus(ParsingStatus.SUCCESS);
		mUndoBin.putInputData(undoInput);
	}

}
