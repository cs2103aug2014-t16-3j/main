package udo.main;

public class uDo {
	
	public static void main(String[] args) {
		uDo udo = new uDo();
		udo.run(args);
	}
	
	UserInterface ui;
	Parser parser;
	Engine engine;
	
	public uDo() {
		ui = new UserInterface();
		parser = new Parser();
		engine = new Engine();
	}

	boolean run(String[] args) {
		try {
			
			String filename = getFilename(args);
			engine.loadFile(filename);
			mainLoop();
			
		} catch (NoFilenameException e) {
			ui.showNoFilename();
		}
	}
	
	String getFilename(String[] args) throws NoFilenameException {
		if (args.length < 1) {
			throw new NoFilenameException();
		} else {
			// the filename should be the first argument
			String filename = args[0]; 
			return filename;
		}
	}

	private void mainLoop() {
		while (isRunning) {
			String inputString = ui.getInput();
			String outputString = parseAndExecute(input);
			ui.show(outputString);
		}
	}
	
	String parseAndExecute(String input) {
		Command cmd = parser.getCmd(input);
		InputData inputData = parser.getData(cmd, input);
		OutputData outputData = engine.execute(cmd, inputData);
		String output = feedback.format(outputData);
	}

}
