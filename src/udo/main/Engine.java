package udo.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import udo.util.engine.Cache;
import udo.util.engine.FileManager;
import udo.util.engine.UndoBin;
import udo.util.engine.runners.Runner;
import udo.util.engine.runners.RunnerAdd;
import udo.util.engine.runners.RunnerDelete;
import udo.util.engine.runners.RunnerDone;
import udo.util.engine.runners.RunnerEdit;
import udo.util.engine.runners.RunnerList;
import udo.util.engine.runners.RunnerSave;
import udo.util.exceptions.AddToCacheException;
import udo.util.exceptions.CacheAccessException;
import udo.util.exceptions.ReadingFromStorageException;
import udo.util.shared.Command;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

/**
 * This is a facade class for the engine component.<br>
 * It shields and controls the cache, undobin and filemanager.<br>
 * It also creates command runners that execute the commands (command pattern).<br>
 * Only one instance of the Engine can exist (singleton).
 */
public class Engine {
	
	private static Engine ENGINE_INSTANCE = null;
	
	private FileManager mFileManager;
	private Cache mCache;
	private UndoBin mUndoBin;

	/**
	 * private constructor. to instantiate this class, use the static getInstance() method.
	 */
	private Engine() {
		mFileManager = new FileManager();
		mCache = new Cache();
		mUndoBin = new UndoBin();
	}
	
	/**
	 * This method returns the Engine. It restricts the caller to only one instance
	 * of the Engine. This follows the Singleton Pattern.
	 * @return The one and only instance of the Engine.
	 */
	public static Engine getInstance() {
		if (ENGINE_INSTANCE == null) {
			ENGINE_INSTANCE = new Engine();
		}
		
		try {
			ENGINE_INSTANCE.loadFile();
			
		} catch (ReadingFromStorageException e) {
			ENGINE_INSTANCE = null;
			return getInstance();
			
		} catch (IOException e) {
			ENGINE_INSTANCE = null;
			return getInstance();
			
		} catch (CacheAccessException e) {
			ENGINE_INSTANCE = null;
			return getInstance();
		}
		return ENGINE_INSTANCE;
	}

	// ****** public methods ****** //
	
	/**
	 * This method returns an arraylist of events that occur on the specified day frame.
	 * @param todayCal the calendar object containing the specified day
	 * @return an arraylist of events that occur on the specified day
	 */
	public ArrayList<ItemData> getTodayScreenItems(Calendar todayCal) {
		try {
			return mCache.getAllEventsOn(todayCal);
		} catch (CacheAccessException e) {
			return null;
		}
	}
	
	/**
	 * This method returns an arraylist of tasks that occur between two dates.
	 * @param fromCal one of the dates
	 * @param toCal another one of the dates.
	 * @return an arraylist of tasks that occur between the dates.
	 */
	public ArrayList<ItemData> getTodoScreenItems(Calendar fromCal, Calendar toCal) {
		try {
			return mCache.getAllTasksBetween(fromCal, toCal);
		} catch (CacheAccessException e) {
			return null;
		}
	}

	/**
	 * This method returns an outputdata object that contains the execution result
	 * and additional information.
	 * @param input The inputdata object to execute 
	 * @return the outputdata object containing execution result.
	 */
	public OutputData execute(InputData input) {
		// precondition, input cannot be null
		assert (input != null);

		//preconditions, input must have these components
		Command cmd = input.getCommand();
		ParsingStatus parsingStatus = input.getStatus();
		assert (cmd != null);
		assert (parsingStatus != null);
		
		// check if parsing success or not
		if (parsingStatus.equals(ParsingStatus.FAIL)) {
			OutputData output = new OutputData(cmd, 
					ParsingStatus.FAIL,
					ExecutionStatus.NULL);
			return output;
		}
		
		OutputData output = null;
		Runner commandRunner = null;
		
		// decide what function to run.
		switch (cmd) {
			case ADD_EVENT :
			case ADD_TASK :
			case ADD_PLAN :
				commandRunner = new RunnerAdd(input, mUndoBin, mCache);
				break;
				
			case LIST :
				commandRunner = new RunnerList(input, mUndoBin, mCache);
				break;
				
			case EDIT :
				commandRunner = new RunnerEdit(input, mUndoBin, mCache);
				break;
				
			case DELETE :
				commandRunner = new RunnerDelete(input, mUndoBin, mCache);
				break;
				
			case MARK_DONE :
			case TOGGLE_DONE :
				commandRunner = new RunnerDone(input, mUndoBin, mCache);
				break;
				
			case UNDO :
				// undo uses engine logic hence cannot be abstracted out.
				output = runUndo(input);
				break;
				
			case SAVE :
				commandRunner = new RunnerSave(mFileManager, mCache);
				break;
				
			case EXIT :
				// exit uses the save runner so cannot be abstracted
				// into a runner without violating demeter princlepl
				output = runExit();
				break;
				
			default:
				return new OutputData(cmd, 
						ParsingStatus.SUCCESS,
						ExecutionStatus.NULL);
		}
		
		// to filter the commands that need the runner to execute
		if (commandRunner != null && output == null) {
			output = commandRunner.run();
		}
		
		// postcondition
		assert (output != null);
		return output;
	}
	
	
	
	
	// ********* methods that execute the commands ******* //

	private OutputData runUndo(InputData inputData) {
		 // the inputdata to be executed is already stored. 
		 // so here just execute that inputdata.
		InputData undoInput = mUndoBin.getUndoInputData();
		return execute(undoInput);
	}

	/**
	 * will save before sending the exit output.
	 * @return the output stating whether it saved or nots
	 */
	private OutputData runExit() {
		OutputData saveOutput = new RunnerSave(mFileManager, mCache).run();
		OutputData output = new OutputData(Command.EXIT,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.FAIL);
		output.setExecutionStatus(saveOutput.getExecutionStatus());
		return output;
	}
	
	
	

	// ********* helper methods ******* //

	private void loadFile() throws ReadingFromStorageException, IOException, CacheAccessException {
		mCache.clear();
		ArrayList<ItemData> itemsFromFile = mFileManager.getFromFile();
		mCache.addAll(itemsFromFile);
	}
}
