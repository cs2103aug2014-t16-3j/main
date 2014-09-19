package udo.main;

import udo.util.Command;
import udo.util.OutputData;

/**
 * This is the main class that will coordinate the other components.
 * The user launches the program by typing "uDo <filename>"
 * No, why should he care about the filename??
 * He should just type "uDo" and launch it. 
 * The backing file should be hidden.
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
