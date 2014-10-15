package udo.tests;

import java.util.ArrayList;

import udo.main.Engine;
import udo.main.UserInterface;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class UItest {

	public static void main(String[] args) {

		
		testPermaTodayScreen();
		
		testPermaToDoScreen();

		testListAll();
		/**
		 * Testing list single date
		 */
		
		/**
		 * Testing list single hashtag
		 */
		
		/**
		 * Testing add item
		 */
		
		/**
		 * Testing delete item
		 */
		
		/**
		 * Testing edit item
		 */
	}
	
	public static void testPermaTodayScreen() {
		UserInterface ui = new UserInterface();
		Engine engine = new Engine();
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		ui.updateTodayScreen(data );
	}
	
	public static void testPermaToDoScreen() {
		UserInterface ui = new UserInterface();
		Engine engine = new Engine();
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		ui.updateTodoScreen(data );
	}
	
	public static void testListAll() {
		UserInterface ui = new UserInterface();
		Engine engine = new Engine();
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY_TYPE, ListQuery.ALL);
		
		OutputData output = engine.execute(input);
		ui.show(output);
	}

}
