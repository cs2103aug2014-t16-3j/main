package udo.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import udo.util.engine.Cache;
import udo.util.engine.FileManager;
import udo.util.engine.UndoBin;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class Engine {

	private FileManager mFileManager;
	private Cache mCache;
	private UndoBin mUndoBin;

	public Engine() {
		mFileManager = new FileManager();
		mCache = new Cache();
		mUndoBin = new UndoBin();
	}

	// ****** public methods ******//

	public boolean loadFile() {
		mCache.clear();
		try {
			ArrayList<ItemData> itemsFromFile = mFileManager.getFromFile();
			mCache.addAll(itemsFromFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public ArrayList<ItemData> getTodayScreenItems(Calendar todayCal) {
		return mCache.getAllEventsOn(todayCal);
	}
	
	public ArrayList<ItemData> getTodoScreenItems(Calendar fromCal, Calendar toCal) {
		return mCache.getAllTodoBetween(fromCal, toCal);
	}

	public OutputData execute(InputData input) {
		Command cmd = input.getCommand();
		ParsingStatus parsingStatus = input.getStatus();
		OutputData output;
		
		
		// check if parsing success or not
		if (parsingStatus.equals(ParsingStatus.FAIL)) {
			output = new OutputData(cmd, 
					ParsingStatus.FAIL,
					ExecutionStatus.NULL);
			return output;
		}
		
		
		// decide what function to run.
		switch (cmd) {
			case ADD_EVENT:
				output = runAddEvent(input);
				break;
			case LIST:
				output = runList(input);
				break;
			case DELETE:
				output = runDelete(input);
				break;
			case UNDO:
				output = runUndo(input);
				break;
			case SAVE:
				output = runSave(input);
				break;
			case EXIT:
				output = runExit(input);
				break;
			default:
				output = new OutputData(cmd, 
						ParsingStatus.SUCCESS,
						ExecutionStatus.NULL);
		}
		output.setParsingStatus(ParsingStatus.SUCCESS);
		return output;
	}

	// ********* methods that execute the commands ******* //

	private OutputData runAddEvent(InputData inputData) {
		ItemData event = new ItemData(ItemType.EVENT);
		// extract data from inputdata to make an event
		// assume all data fields are present
		event.put(Keys.UID, mCache.generateUID());

		event.put(Keys.TITLE, inputData.get(Keys.TITLE));

		event.put(Keys.START, inputData.get(Keys.START));

		event.put(Keys.END, inputData.get(Keys.END));

		event.put(Keys.HASHTAGS, inputData.get(Keys.HASHTAGS));

		boolean addOK = mCache.add(event);
		Command cmd = inputData.getCommand();
		OutputData output = new OutputData(cmd);
		
		if (addOK) {
			// if added item successfully
			// make output object with the event data inside
			output.setExecutionStatus(ExecutionStatus.SUCCESS);
			output.put(Keys.ITEM, event);
			storeUndo(Command.ADD_EVENT, event);
		} else {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		}

		return output;
	}

	private OutputData runList(InputData input) {
		/*
		 * 1. build a list of all items 2. select only the items we want. 3.
		 * sort the items according to date/time 4. put the items in the
		 * outputdata. 5. return output
		 */
		ArrayList<ItemData> listOfAllItems = mCache.getAllItems();
		ArrayList<ItemData> result;

		ListQuery query = (ListQuery) input.get(Keys.QUERY_TYPE);
		String queryString = "";
		switch (query) {
		case ALL:
			result = listOfAllItems;
			break;
		case SINGLE_HASHTAG:
			queryString = (String) input.get(Keys.HASHTAG);
			result = trimList(listOfAllItems, queryString);
			break;
		default:
			return null;
		}

		OutputData output = new OutputData(Command.LIST,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.SUCCESS);
		output.put(Keys.QUERY_TYPE, query);
		output.put(Keys.QUERY, queryString);
		output.put(Keys.ITEMS, result);

		return output;
	}

	private OutputData runDelete(InputData inputData) {
		int uid = (int) inputData.get(Keys.UID);
		ItemData deletedItem = mCache.getItem(uid);
		boolean deleteOK = mCache.deleteItem(uid);
		OutputData output;
		if (deleteOK) {
			output = new OutputData(Command.DELETE, 
					ParsingStatus.SUCCESS, 
					ExecutionStatus.SUCCESS);
			output.put(Keys.ITEM, deletedItem);
			storeUndo(Command.DELETE, deletedItem);
		} else {
			output = new OutputData(Command.DELETE, 
					ParsingStatus.SUCCESS, 
					ExecutionStatus.FAIL);
		}

		return output;
	}

	private OutputData runEdit(InputData inputData) {

		return null;
	}

	private OutputData runUndo(InputData inputData) {
		/*
		 * store the inputdata to be executed. so here just execute that
		 * inputdata.
		 */
		InputData undoInput = mUndoBin.getInputData();
		return execute(undoInput);
	}

	private OutputData runSave(InputData inputData) {
		boolean writeOK = writeCacheToFile();
		if (!writeOK) {
			return null;
		}
		OutputData output = new OutputData(Command.SAVE,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.SUCCESS);
		return output;
	}

	private OutputData runExit(InputData inputData) {
		runSave(null);
		OutputData output = new OutputData(Command.EXIT,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.SUCCESS);
		return output;
	}

	// ********* private abstracted helper methods ******* //

	private void storeUndo(Command previousCommand, ItemData previousItem) {
		InputData undoInput;
		switch (previousCommand) {
			case ADD_EVENT:
				undoInput = new InputData(Command.DELETE);
				undoInput.put(Keys.UID, previousItem.get(Keys.UID));
				break;
			case DELETE:
				ItemType previousItemType = previousItem.getItemType();
				// decide which kind of command based on the kind of item deleted.
				switch (previousItemType) {
					case EVENT:
						undoInput = new InputData(Command.ADD_EVENT);
						break;
					case TASK:
						// TODO
						undoInput = new InputData(Command.ADD_TASK);
						break;
					case PLAN:
						// TODO
						undoInput = new InputData(Command.ADD_PLAN);
						break;
					default:
						// should not
						return;
				}
				// copy the data in the item to the inputdata.
				// so that the add command can add like it came from the parser
				// it will also copy the uid field
				// but it will be ignored in the add execution.
				for (String key : previousItem.getKeys()) {
					undoInput.put(key, previousItem.get(key));
				}
				break;
			case EDIT:
				// TODO
				undoInput = null;
				break;
			default:
				// do nothing. this shouldnt happen
				return;

		}
		undoInput.setParsingStatus(ParsingStatus.SUCCESS);
		mUndoBin.putInputData(undoInput);
	}

	private boolean writeCacheToFile() {
		/*
		 * boolean filePrepared = mFileManager.startWriteMode(); boolean
		 * cacheLocked = mCache.lock(); if (!filePrepared || !cacheLocked) {
		 * return false; }
		 * 
		 * while (mCache.hasNextItem()) { ItemData item = mCache.getNextItem();
		 * mFileManager.write(item); }
		 * 
		 * mFileManager.closeWriteMode();
		 */
		mFileManager.writeToFile(mCache.getAllItems());
		return true;
	}

	private ArrayList<ItemData> trimList(ArrayList<ItemData> list, String tag) {
		ArrayList<ItemData> result = new ArrayList<ItemData>();
		for (ItemData item : list) {
			@SuppressWarnings("unchecked")
			ArrayList<String> tags = (ArrayList<String>) item
					.get(Keys.HASHTAGS);
			if (tags.contains(tag)) {
				result.add(item);
			}
		}
		Collections.sort(result);
		return result;
	}
}
