package udo.tests;

import java.util.ArrayList;
import java.util.Calendar;

import udo.main.Engine;
import udo.main.UserInterface;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class UItest {

	private static UserInterface ui = new UserInterface();
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
		Engine engine = new Engine();
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		Calendar date = Calendar.getInstance();
		for(int i = 0; i<3; i++) {
			InputData input = new InputData(Command.ADD_EVENT);
			input.put(Keys.TITLE, "dummyEvent" + i);
			input.put(Keys.START, date);
			input.put(Keys.END, date);
			input.put(Keys.HASHTAGS, new ArrayList<String>());
			input.setParsingStatus(ParsingStatus.SUCCESS);
			OutputData output = engine.execute(input);
			data.add((ItemData) output.get(Keys.ITEM));
		}
		
		ui.updateTodayScreen(data );
	}
	
	public static void testPermaToDoScreen() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		Calendar date = Calendar.getInstance();
		for(int i = 0; i<3; i++) {
			ItemData input = new ItemData(ItemType.TASK);
			input.put(Keys.UID, i);
			input.put(Keys.TITLE, "dummyTask" + i);
			input.put(Keys.DUE, date);
			input.put(Keys.HASHTAGS, new ArrayList<String>());
			data.add(input);
		}
		ui.updateTodoScreen(data );
	}
	
	public static void testListAll() {
		Engine engine = new Engine();
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY_TYPE, ListQuery.ALL);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData output = engine.execute(input);
		ui.show(output);
	}

}
