//@author A0108358B
package udo.util.engine.runners;

import udo.util.engine.Cache;
import udo.util.engine.UndoBin;
import udo.util.exceptions.CacheAccessException;
import udo.util.exceptions.InvalidUIDException;
import udo.util.exceptions.ItemNotFoundException;
import udo.util.shared.Command;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class RunnerDelete extends Runner {

	public RunnerDelete(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		int uid = (int) mInput.get(Keys.UID);
		ItemData deletedItem;
		OutputData output;
		
		try {
			deletedItem = mCache.deleteItem(uid);
			output = new OutputData(Command.DELETE, 
					ParsingStatus.SUCCESS, 
					ExecutionStatus.SUCCESS);
			output.put(Keys.ITEM, deletedItem);
			storeUndo(deletedItem);
			
		} catch (CacheAccessException e) {
			output = new OutputData(Command.DELETE, 
					ParsingStatus.SUCCESS, 
					ExecutionStatus.FAIL);
			
		} catch (InvalidUIDException e) {
			output = new OutputData(Command.DELETE, 
					ParsingStatus.SUCCESS, 
					ExecutionStatus.FAIL);
			
		} catch (ItemNotFoundException e) {
			output = new OutputData(Command.DELETE, 
					ParsingStatus.SUCCESS, 
					ExecutionStatus.FAIL);
		}

		return output;
	}

	private void storeUndo(ItemData item) {
		InputData undoInput;
		ItemType previousItemType = item.getItemType();
		
		// create the correct inputdata depending on the type of item
		switch (previousItemType) {
			case EVENT :
				undoInput = new InputData(Command.ADD_EVENT);
				break;
				
			case TASK :
				// TODO
				undoInput = new InputData(Command.ADD_TASK);
				break;
				
			case PLAN :
				// TODO
				undoInput = new InputData(Command.ADD_PLAN);
				break;
				
			default:
				// should not
				return;
		}
		
		// copy the data in the item to the inputdata.
		// so that the add command can add like it came from the parser
		for (String key : item.getKeys()) {
			undoInput.put(key, item.get(key));
		}
		
		undoInput.setParsingStatus(ParsingStatus.SUCCESS);
		mUndoBin.storeUndo(undoInput);
	}

}
