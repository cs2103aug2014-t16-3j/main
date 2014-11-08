//@author A0108358B
package udo.util.engine.runners;

import java.util.Calendar;

import udo.util.engine.Cache;
import udo.util.engine.UndoBin;
import udo.util.exceptions.CacheAccessException;
import udo.util.exceptions.InvalidUIDException;
import udo.util.exceptions.ItemNotFoundException;
import udo.util.shared.Command;
import udo.util.shared.EditField;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class RunnerEdit extends Runner {

	public RunnerEdit(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		int uid = (int) mInput.get(Keys.UID);
		try {
			ItemData itemToEdit = mCache.getItem(uid);
			Object value = mInput.get(Keys.VALUE);
			OutputData output;

			EditField field = (EditField) mInput.get(Keys.FIELD);
			
			switch (field) {
				case DUE_DATE :
					output = runEditDueDate(itemToEdit, (Calendar) value);
					break;
					
				case DUE_TIME :
					output = runEditDueTime(itemToEdit, (Calendar) value);
					break;
					
				case END_DATE :
					output = runEditEndDate(itemToEdit, (Calendar) value);
					break;
				case END_TIME :
					output = runEditEndTime(itemToEdit, (Calendar) value);
					break;
					
				case START_DATE :
					output = runEditStartDate(itemToEdit, (Calendar) value);
					break;
				case START_TIME :
					output = runEditStartTime(itemToEdit, (Calendar) value);
					break;
					
				case TITLE :
					output = runEditTitle(itemToEdit, (String) value);
					break;
					
				default:
					output = new OutputData(Command.EDIT, 
							ParsingStatus.SUCCESS,
							ExecutionStatus.FAIL);
			}
			
			output.put(Keys.ITEM, itemToEdit);
			output.put(Keys.FIELD, field);
			
			return output;
			
		} catch (CacheAccessException e) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
			
		} catch (ItemNotFoundException e) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
			
		} catch (InvalidUIDException e) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
			
		}
	}

	private OutputData runEditTitle(ItemData item, String title) {
		String oldTitle = (String) item.get(Keys.TITLE);
		item.put(Keys.TITLE, title);
		int uid = (int) item.get(Keys.UID);
		storeUndo(uid, EditField.TITLE, oldTitle);
		
		OutputData output = new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
		output.put(Keys.OLD_VALUE, oldTitle);
		
		return output;
	}
	
	private OutputData runEditStartTime(ItemData item, Calendar timeCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
		}
		Calendar startCal = (Calendar) item.get(Keys.START);
		Calendar calToStore = (Calendar) startCal.clone();
		setTime(startCal, timeCal);
		int uid = (int) item.get(Keys.UID);
		storeUndo(uid, EditField.START_TIME, calToStore);
		OutputData output = new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
		output.put(Keys.OLD_VALUE, calToStore);
		return output;
	}
	
	private OutputData runEditEndTime(ItemData item, Calendar timeCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
		}
		Calendar endCal = (Calendar) item.get(Keys.END);
		Calendar calToStore = (Calendar) endCal.clone();
		setTime(endCal, timeCal);
		int uid = (int) item.get(Keys.UID);
		storeUndo(uid, EditField.END_TIME, calToStore);
		OutputData output = new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
		output.put(Keys.OLD_VALUE, calToStore);
		return output;
	}

	private OutputData runEditStartDate(ItemData item, Calendar dateCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
		}
		Calendar dueCal = (Calendar) item.get(Keys.START);
		Calendar calToStore = (Calendar) dueCal.clone();
		setDate(dueCal, dateCal);
		int uid = (int) item.get(Keys.UID);
		storeUndo(uid, EditField.START_DATE, calToStore);
		OutputData output = new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
		output.put(Keys.OLD_VALUE, calToStore);
		return output;
	}

	private OutputData runEditEndDate(ItemData item, Calendar dateCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
		}
		Calendar dueCal = (Calendar) item.get(Keys.END);
		Calendar calToStore = (Calendar) dueCal.clone();
		setDate(dueCal, dateCal);
		int uid = (int) item.get(Keys.UID);
		storeUndo(uid, EditField.END_DATE, calToStore);
		OutputData output = new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
		output.put(Keys.OLD_VALUE, calToStore);
		return output;
	}
	
	private OutputData runEditDueTime(ItemData item, Calendar timeCal) {
		if (item.getItemType() != ItemType.TASK) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
		}
		Calendar dueCal = (Calendar) item.get(Keys.DUE);
		Calendar calToStore = (Calendar) dueCal.clone();
		setTime(dueCal, timeCal);
		int uid = (int) item.get(Keys.UID);
		storeUndo(uid, EditField.DUE_TIME, calToStore);
		OutputData output = new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
		output.put(Keys.OLD_VALUE, calToStore);
		return output;
	}

	private OutputData runEditDueDate(ItemData item, Calendar dateCal) {
		if (item.getItemType() != ItemType.TASK) {
			return new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
		}
		Calendar dueCal = (Calendar) item.get(Keys.DUE);
		Calendar calToStore = (Calendar) dueCal.clone();
		setDate(dueCal, dateCal);
		int uid = (int) item.get(Keys.UID);
		storeUndo(uid, EditField.DUE_DATE, calToStore);
		OutputData output = new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
		output.put(Keys.OLD_VALUE, calToStore);
		return output;
	}

	private OutputData setTime(Calendar itemCal, Calendar timeCal) {
		itemCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		itemCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		return new OutputData(Command.EDIT, 
				ParsingStatus.SUCCESS,
				ExecutionStatus.SUCCESS);
	}

	private void setDate(Calendar itemCal, Calendar dateCal) {
		itemCal.set(Calendar.YEAR, dateCal.get(Calendar.YEAR));
		itemCal.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
		itemCal.set(Calendar.DAY_OF_MONTH, dateCal.get(Calendar.DAY_OF_MONTH));
	}

	private void storeUndo(int uid, EditField field, Object oldValue) {
		InputData undoInput = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		undoInput.put(Keys.UID, uid);
		undoInput.put(Keys.FIELD, field);
		undoInput.put(Keys.VALUE, oldValue);
		mUndoBin.storeUndo(undoInput);
	}

}
