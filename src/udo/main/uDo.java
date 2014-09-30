package udo.main;

import udo.util.shared.Command;
import udo.util.shared.InputData;
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

	public static final int EXIT_STATUS = 0;

	private UserInterface ui;
	private Parser parser;
	private Engine engine;

	boolean isRunning;

	public uDo() {
		ui = new UserInterface();
		parser = new Parser();
		engine = new Engine();
		isRunning = true;
	}

	private boolean run(String[] args) {

		manageArgs(args);

		engine.loadFile();

		runMainLoop();

		return true;
	}

	private boolean manageArgs(String[] args) {
		// in case we decide to handle any arguments
		return true;
	}

	private void runMainLoop() {
		while (isRunning) {
			String inputString = ui.getInput();

			OutputData outputData = parseAndExecute(inputString);

			CheckForExitCommand(outputData);

			ui.show(outputData);
		}
		System.exit(EXIT_STATUS);
	}

	private void CheckForExitCommand(OutputData outputData) {
		if (outputData.getCommand() == Command.EXIT) {
			if (outputData.getStatus() == ExecutionStatus.SUCCESS) {
				isRunning = false;
			}
		}
	}

	OutputData parseAndExecute(String input) {
		InputData inputData = parser.getInputData(input);
		OutputData outputData = engine.execute(inputData);
		return outputData;
	}

}
