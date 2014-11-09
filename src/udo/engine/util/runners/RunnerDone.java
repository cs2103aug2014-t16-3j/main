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
import udo.exceptions.InvalidUIDException;
import udo.exceptions.ItemNotFoundException;

public class RunnerDone extends Runner {

	public RunnerDone(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		Command cmd = mInput.getCommand();
		int uid = (int) mInput.get(Keys.UID);
		try {
			ItemData itemToMark = mCache.getItem(uid);
			
			// ensure item exists
			if (itemToMark == null) {
				return new OutputData(cmd,
						ParsingStatus.SUCCESS,
						ExecutionStatus.FAIL);
			}
			
			// ensure item is markable (i.e. not event)
			if (itemToMark.getItemType() == ItemType.EVENT) {
				return new OutputData(cmd,
						ParsingStatus.SUCCESS,
						ExecutionStatus.FAIL);
			}
			
			switch (cmd) {
				case MARK_DONE :
					markDone(itemToMark);
					break;
					
				case TOGGLE_DONE :
					toggleDone(itemToMark);
					break;
					
				default:
					break;
			}
			
			OutputData output = new OutputData(cmd,
					ParsingStatus.SUCCESS,
					ExecutionStatus.SUCCESS);
			output.put(Keys.ITEM, itemToMark);
			
			return output;
			
		} catch (CacheAccessException e) {
			return new OutputData(Command.EXIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
			
		} catch (ItemNotFoundException e) {
			return new OutputData(Command.EXIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
			
		} catch (InvalidUIDException e) {
			return new OutputData(Command.EXIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
			
		}
	}

	private void markDone(ItemData itemToMark) {
		itemToMark.put(Keys.DONE, true);
	}
	
	private void toggleDone(ItemData itemToMark) {
		boolean doneValue = (boolean) itemToMark.get(Keys.DONE);
		itemToMark.put(Keys.DONE, !doneValue);
	}

}
