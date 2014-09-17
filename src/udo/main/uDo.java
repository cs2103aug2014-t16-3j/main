package udo.main;

public class uDo {
	
	public static void main(String[] args) {
		uDo udo = new uDo();
		udo.run(args);
	}

	void run(String[] args) {
		
		String filename = getFilename(args);
		engine.loadFile(filename);
		
		while (isRunning) {
			String inputString = ui.getInput();
			String outputString = udo.parseAndExecute(input);
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
