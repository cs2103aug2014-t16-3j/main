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
import udo.util.engine.runners.RunnerEdit;
import udo.util.engine.runners.RunnerList;
import udo.util.engine.runners.RunnerSave;
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
		ENGINE_INSTANCE.loadFile();
		return ENGINE_INSTANCE;
	}

	// ****** public methods ****** //
	
	/**
	 * This method returns an arraylist of events that occur on the specified day frame.
	 * @param todayCal the calendar object containing the specified day
	 * @return an arraylist of events that occur on the specified day
	 */
	public ArrayList<ItemData> getTodayScreenItems(Calendar todayCal) {
		return mCache.getAllEventsOn(todayCal);
	}
	
	/**
	 * This method returns an arraylist of tasks that occur between two dates.
	 * @param fromCal one of the dates
	 * @param toCal another one of the dates.
	 * @return an arraylist of tasks that occur between the dates.
	 */
	public ArrayList<ItemData> getTodoScreenItems(Calendar fromCal, Calendar toCal) {
		return mCache.getAllTasksBetween(fromCal, toCal);
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
		
		Command cmd = input.getCommand();
		ParsingStatus parsingStatus = input.getStatus();
		//preconditions, input must have these components
		assert (cmd != null);
		assert (parsingStatus != null);
		
		OutputData output = null;
		// check if parsing success or not
		if (parsingStatus.equals(ParsingStatus.FAIL)) {
			output = new OutputData(cmd, 
					ParsingStatus.FAIL,
					ExecutionStatus.NULL);
			return output;
		}
		
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
				output = new OutputData(cmd, 
						ParsingStatus.SUCCESS,
						ExecutionStatus.NULL);
		}
		
		if (commandRunner != null) {
			output = commandRunner.run();
		}
		
		// postcondition
		assert (output != null);
		return output;
	}
	
	// ********* methods that execute the commands ******* //

	private OutputData runUndo(InputData inputData) {
		/*
		 * the inputdata to be executed is already stored. 
		 * so here just execute that inputdata.
		 */
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

	private boolean loadFile() {
		mCache.clear();
		try {
			ArrayList<ItemData> itemsFromFile = mFileManager.getFromFile();
			mCache.addAll(itemsFromFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
