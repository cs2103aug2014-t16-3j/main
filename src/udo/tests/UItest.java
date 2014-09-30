package udo.tests;

import udo.main.Engine;
import udo.main.UserInterface;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;

public class UItest {

	public static void main(String[] args) {

		UserInterface ui = new UserInterface();
		Engine engine = new Engine();
		engine.loadFile();
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY, ListQuery.ALL);
		OutputData output = engine.execute(input);
		ui.show(output);

	}

}
