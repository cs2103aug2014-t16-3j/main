//@author A0108358B
package udo.util.engine.runners;

import udo.util.engine.Cache;
import udo.util.engine.UndoBin;
import udo.util.exceptions.CacheAccessException;
import udo.util.exceptions.InvalidUIDException;
import udo.util.exceptions.ItemNotFoundException;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

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
