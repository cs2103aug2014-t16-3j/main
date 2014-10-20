package udo.util.engine;

import java.util.Calendar;

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
			EditField field = (EditField) mInput.get(Keys.FIELD);
			Object value = mInput.get(Keys.VALUE);
			ExecutionStatus eStatus = ExecutionStatus.FAIL;
			switch (field) {
				case DUE_DATE :
					eStatus = runEditDueDate(itemToEdit, (Calendar) value);
					break;
				case DUE_TIME :
					eStatus = runEditDueTime(itemToEdit, (Calendar) value);
					break;
				case END_DATE :
					eStatus = runEditEndDate(itemToEdit, (Calendar) value);
					break;
				case END_TIME :
					eStatus = runEditEndTime(itemToEdit, (Calendar) value);
					break;
				case START_DATE :
					eStatus = runEditStartDate(itemToEdit, (Calendar) value);
					break;
				case START_TIME :
					eStatus = runEditStartTime(itemToEdit, (Calendar) value);
					break;
				case TITLE :
					eStatus = runEditTitle(itemToEdit, (String) value);
					break;
				default:
					eStatus = ExecutionStatus.FAIL;
			}
			OutputData output = new OutputData(Command.EDIT, 
					ParsingStatus.SUCCESS,
					eStatus);
			output.put(Keys.ITEM, itemToEdit);
			return output;
			
		} catch (ItemNotFoundException e) {
			return new OutputData(Command.EXIT, 
					ParsingStatus.SUCCESS,
					ExecutionStatus.FAIL);
		}
	}

	private ExecutionStatus runEditTitle(ItemData item, String title) {
		String oldTitle = (String) item.get(Keys.TITLE);
		item.put(Keys.TITLE, title);
		storeUndo(EditField.TITLE, oldTitle);
		return ExecutionStatus.SUCCESS;
	}
	
	private ExecutionStatus runEditDueTime(ItemData item, Calendar timeCal) {
		if (item.getItemType() != ItemType.TASK) {
			return ExecutionStatus.FAIL;
		}
		Calendar dueCal = (Calendar) item.get(Keys.DUE);
		Calendar calToStore = (Calendar) dueCal.clone();
		setTime(dueCal, timeCal);
		storeUndo(EditField.DUE_TIME, calToStore);
		return ExecutionStatus.SUCCESS;
	}
	
	private ExecutionStatus runEditStartTime(ItemData item, Calendar timeCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return ExecutionStatus.FAIL;
		}
		Calendar startCal = (Calendar) item.get(Keys.START);
		Calendar calToStore = (Calendar) startCal.clone();
		setTime(startCal, timeCal);
		storeUndo(EditField.START_TIME, calToStore);
		return ExecutionStatus.SUCCESS;
	}
	
	private ExecutionStatus runEditEndTime(ItemData item, Calendar timeCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return ExecutionStatus.FAIL;
		}
		Calendar endCal = (Calendar) item.get(Keys.END);
		Calendar calToStore = (Calendar) endCal.clone();
		setTime(endCal, timeCal);
		storeUndo(EditField.END_TIME, calToStore);
		return ExecutionStatus.SUCCESS;
	}

	private ExecutionStatus setTime(Calendar itemCal, Calendar timeCal) {
		itemCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		itemCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		return ExecutionStatus.SUCCESS;
	}

	private ExecutionStatus runEditDueDate(ItemData item, Calendar dateCal) {
		if (item.getItemType() != ItemType.TASK) {
			return ExecutionStatus.FAIL;
		}
		Calendar dueCal = (Calendar) item.get(Keys.DUE);
		Calendar calToStore = (Calendar) dueCal.clone();
		setDate(dueCal, dateCal);
		storeUndo(EditField.DUE_DATE, calToStore);
		return ExecutionStatus.SUCCESS;
	}

	private ExecutionStatus runEditEndDate(ItemData item, Calendar dateCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return ExecutionStatus.FAIL;
		}
		Calendar dueCal = (Calendar) item.get(Keys.END);
		Calendar calToStore = (Calendar) dueCal.clone();
		setDate(dueCal, dateCal);
		storeUndo(EditField.END_DATE, calToStore);
		return ExecutionStatus.SUCCESS;
	}
	
	private ExecutionStatus runEditStartDate(ItemData item, Calendar dateCal) {
		if (item.getItemType() != ItemType.EVENT) {
			return ExecutionStatus.FAIL;
		}
		Calendar dueCal = (Calendar) item.get(Keys.START);
		Calendar calToStore = (Calendar) dueCal.clone();
		setDate(dueCal, dateCal);
		storeUndo(EditField.START_DATE, calToStore);
		return ExecutionStatus.SUCCESS;
	}

	private void setDate(Calendar itemCal, Calendar dateCal) {
		itemCal.set(Calendar.YEAR, dateCal.get(Calendar.YEAR));
		itemCal.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
		itemCal.set(Calendar.DAY_OF_MONTH, dateCal.get(Calendar.DAY_OF_MONTH));
	}

	private void storeUndo(EditField field, Object oldValue) {
		InputData undoInput = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		undoInput.put(Keys.FIELD, field);
		undoInput.put(Keys.VALUE, oldValue);
		mUndoBin.putInputData(undoInput);
	}

}
