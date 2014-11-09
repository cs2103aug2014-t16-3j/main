//@author A0108358B
package udo.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import udo.constants.Constants.MainVars;
import udo.data.InputData;
import udo.data.ItemData;
import udo.data.OutputData;
import udo.engine.Engine;
import udo.enums.Command;
import udo.enums.ExecutionStatus;
import udo.parser.Parser;
import udo.ui.UserInterface;

/**
 * This is the main class that the user will run. This class will also
 * coordinate the other components.
 * 
 */
public class uDo {

	private UserInterface mUI;
	private Parser mParser;
	private Engine mEngine;

	private boolean mIsRunning;

	public uDo() {
		try {
			mUI = UserInterface.getInstance(); // the UI is shown when init-ed, singleton pattern
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(MainVars.EXIT_STATUS_NOT_OK);
		} 
		mParser = new Parser();
		mEngine = Engine.getInstance(); // Engine is a Singleton pattern
		mIsRunning = true;
	}

	private void run(String[] args) {

		manageArgs(args);
		
		try {
			
			runMainLoop();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(MainVars.EXIT_STATUS_NOT_OK);
		}
	}

	private void manageArgs(String[] args) {
		// in case we decide to handle any arguments
	}

	private void runMainLoop() {
		while (mIsRunning) {
			
			updateTodayScreen();
			updateTodoScreen();
			
			String inputString = mUI.getInput();
			OutputData outputData = parseAndExecute(inputString);
			mUI.show(outputData);
			checkForExitCommand(outputData);
		}
		System.exit(MainVars.EXIT_STATUS_OK);
	}

	private void updateTodoScreen() {
		Calendar from = Calendar.getInstance();
		from.setLenient(true);
		Calendar to = Calendar.getInstance();
		to.set(Calendar.DAY_OF_YEAR, to.get(Calendar.DAY_OF_YEAR) + MainVars.DAYS_IN_ADVANCE);
		ArrayList<ItemData> itemsToShow = mEngine.getTodoScreenItems(from, to);
		mUI.updateTodoScreen(itemsToShow);
	}

	private void updateTodayScreen() {
		Calendar today = Calendar.getInstance();
		ArrayList<ItemData> itemsToShow = mEngine.getTodayScreenItems(today);
		mUI.updateTodayScreen(itemsToShow);
	}

	private void checkForExitCommand(OutputData outputData) {
		if (outputData.getCommand() == Command.EXIT) {
			if (outputData.getExecutionStatus() == ExecutionStatus.SUCCESS) {
				mIsRunning = false;
			}
		}
	}

	private OutputData parseAndExecute(String input) {
		InputData inputData = mParser.getInputData(input);
		OutputData outputData = mEngine.execute(inputData);
		return outputData;
	}
	
	//@author A0114088H
	public OutputData testParseAndExecute(String input) {
		OutputData output = parseAndExecute(input);
		return output;
	}

	public static void main(String[] args) {
		uDo udo = new uDo();
		udo.run(args);
	}

}
