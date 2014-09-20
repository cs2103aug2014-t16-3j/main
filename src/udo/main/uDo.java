package udo.main;

import udo.util.Command;
import udo.util.OutputData;

/**
 * This is the main class that the user will run.
 * This class will also coordinate the other components.
 *
 */
public class uDo {
	
	public static void main(String[] args) {
		uDo udo = new uDo();
		udo.run(args);
	}
	
	UserInterface ui;
	Parser parser;
	Engine engine;
	
	boolean isRunning;
	
	public uDo() {
		ui = new UserInterface();
		parser = new Parser();
		engine = new Engine();
		isRunning = true;
	}

	boolean run(String[] args) {
		
		manageArgs(args);
		
		engine.loadFile();
		
		runMainLoop();
	}
	
	boolean manageArgs(String[] args) {
		// in case we decide to handle any arguments 
	}

	private void runMainLoop() {
		while (isRunning) {
			String inputString = ui.getInput();
			OutputData outputData = parseAndExecute(inputString);
			ui.show(outputData);
		}
	}
	
	OutputData parseAndExecute(String input) {
		InputData inputData = parser.getInputData(input);
		OutputData outputData = engine.execute(inputData);
		return outputData;
	}

}
