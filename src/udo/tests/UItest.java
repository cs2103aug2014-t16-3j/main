package udo.tests;

import java.util.ArrayList;
import java.util.Calendar;

import udo.main.Engine;
import udo.main.UserInterface;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class UItest {

	private static UserInterface ui = new UserInterface();
	public static void main(String[] args) {
		

		
		testListAll();
		
		testPermaToDoScreen();
		
		testPermaTodayScreen();
		
		/**
		 * Testing list single date
		 */
//		testSingleView();
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
	
	private static void testSingleView() {
		OutputData output = new OutputData(Command.ADD_PLAN, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.PLAN);
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample SingleItem");
//		item.put(Keys.DUE, Calendar.getInstance());
//		item.put(Keys.START, Calendar.getInstance());
//		item.put(Keys.END, Calendar.getInstance());
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		output.put(Keys.ITEM, item);
		ui.show(output);
		
	}

	public static void testPermaTodayScreen() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		Calendar date = Calendar.getInstance();
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("Asd");
		tags.add("jkl");
		for(int i = 0; i<14; i++) {
			ItemData input = new ItemData(ItemType.EVENT);
			input.put(Keys.UID, i);
			input.put(Keys.TITLE, "dummyEvent" + i);
			input.put(Keys.START, date);
			input.put(Keys.END, date);
			input.put(Keys.HASHTAGS, tags);
			data.add(input);
		}
		ui.updateTodayScreen(data );
	}
	
	public static void testPermaToDoScreen() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		Calendar date = Calendar.getInstance();
		for(int i = 0; i<2; i++) {
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
		Engine engine = Engine.getInstance();
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY_TYPE, ListQuery.ALL);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData output = engine.execute(input);
		ui.show(output);
	}

}
