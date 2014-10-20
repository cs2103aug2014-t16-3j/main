package udo.main;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.shared.ExecutionStatus;

/**
 * This is the main class that the user will run. This class will also
 * coordinate the other components.
 * 
 */
public class uDo {

	public static void main(String[] args) {
		uDo udo = new uDo();
		udo.run(args);
	}

	public static final int EXIT_STATUS_OK = 0;
	public static final int DAYS_IN_ADVANCE = 3;

	private UserInterface mUI;
	private Parser mParser;
	private Engine mEngine;

	boolean mIsRunning;

	public uDo() {
		mUI = UserInterface.getInstance(); // the UI is shown when init-ed, singleton pattern
		mParser = new Parser();
		mEngine = Engine.getInstance(); // Engine is a Singleton pattern
		mIsRunning = true;
	}

	private boolean run(String[] args) {

		manageArgs(args);
		
		runMainLoop();

		return true;
	}

	private boolean manageArgs(String[] args) {
		// in case we decide to handle any arguments
		return true;
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
		System.exit(EXIT_STATUS_OK);
	}

	private void updateTodoScreen() {
		Calendar from = Calendar.getInstance();
		from.setLenient(true);
		Calendar to = Calendar.getInstance();
		to.set(Calendar.DAY_OF_YEAR, to.get(Calendar.DAY_OF_YEAR) + DAYS_IN_ADVANCE);
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

}
