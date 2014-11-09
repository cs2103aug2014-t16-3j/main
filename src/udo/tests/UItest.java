//@author A0114088H
package udo.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.data.ItemData;
import udo.data.OutputData;
import udo.engine.Engine;
import udo.enums.Command;
import udo.enums.EditField;
import udo.enums.ExecutionStatus;
import udo.enums.ItemType;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;
import udo.ui.UserInterface;

public class UItest {

	private static UserInterface UI = null;
	{
		try {
			UI = UserInterface.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static void main(String[] args) {
		

		/**
		 * Testing listing items in main screen
		 */
//		testListAll();
//		testListDone();
//		testListSingleDate();
//		testListSingleHashtag();
	
		/**
		 * Testing single views in main screen
		 */
//		testAddEvent();
//		testAddTask();
//		testAddPlan();
//		testEditItem();
//		testDeleteItem();
//		testMarkAsDone();
//		testToggleDone();	
		
		/**
		 * Testing side screens
		 */
		testPermaToDoScreen();
		testPermaTodayScreen();
	}
	
	private static void testToggleDone() {
		OutputData output = new OutputData(Command.TOGGLE_DONE, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.EVENT);
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample SingleEVENT");
		item.put(Keys.START, Calendar.getInstance());
		item.put(Keys.END, Calendar.getInstance());
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		item.put(Keys.DONE, true);
		output.put(Keys.ITEM, item);
		UI.show(output);
	}

	private static void testMarkAsDone() {
		OutputData output = new OutputData(Command.MARK_DONE, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.EVENT);
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample SingleEVENT");
		item.put(Keys.START, Calendar.getInstance());
		item.put(Keys.END, Calendar.getInstance());
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		output.put(Keys.ITEM, item);
		UI.show(output);
	}

	private static void testAddEvent() {
		OutputData output = new OutputData(Command.ADD_EVENT, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.EVENT);
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample SingleEVENT");
		item.put(Keys.START, Calendar.getInstance());
		item.put(Keys.END, Calendar.getInstance());
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		output.put(Keys.ITEM, item);
		UI.show(output);
	}

	private static void testAddTask() {
		OutputData output = new OutputData(Command.ADD_TASK, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.TASK);
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample SingleTASK");
		item.put(Keys.DUE, Calendar.getInstance());
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		output.put(Keys.ITEM, item);
		UI.show(output);
	}

	private static void testAddPlan() {
		OutputData output = new OutputData(Command.ADD_PLAN, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.PLAN);
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample SinglePLAN");
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		output.put(Keys.ITEM, item);
		UI.show(output);	
	}

	private static void testDeleteItem() {
		OutputData output = new OutputData(Command.DELETE, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.PLAN);
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample SingleItem");
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		output.put(Keys.ITEM, item);
		UI.show(output);
		
	}

	private static void testListSingleHashtag() {
		// TODO Auto-generated method stub
		Engine engine = Engine.getInstance();
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
		input.put(Keys.QUERY_VALUE, "test");
		input.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData output = engine.execute(input);
		UI.show(output);
	}

	private static void testListSingleDate() {
		// TODO Auto-generated method stub
		OutputData output = new OutputData(Command.LIST, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		Calendar date = Calendar.getInstance();
		output.put(Keys.QUERY_TYPE, ListQuery.DATE);
		output.put(Keys.QUERY_VALUE, Calendar.getInstance());
		for(int i = 0; i<9; i++) {
			ItemData input = new ItemData(ItemType.EVENT);
			input.put(Keys.UID, i);
			input.put(Keys.TITLE, "dummyEvent" + i);
			input.put(Keys.START, date);
			input.put(Keys.END, date);
			input.put(Keys.HASHTAGS, new ArrayList<String>());
			data.add(input);
		}
		output.put(Keys.ITEMS, data);
		UI.show(output);
	}

	private static void testEditItem() {
		OutputData output = new OutputData(Command.EDIT, ParsingStatus.SUCCESS, ExecutionStatus.SUCCESS);
		ItemData item = new ItemData(ItemType.TASK);
		Calendar oldDate = Calendar.getInstance();
		Calendar newDate = Calendar.getInstance();
		item.put(Keys.UID, 1);
		item.put(Keys.TITLE, "sample EditedItem");
//		item.put(Keys.START, date);
//		item.put(Keys.END, date);
		item.put(Keys.DUE, oldDate);
		item.put(Keys.HASHTAGS, new ArrayList<String>());
		output.put(Keys.FIELD, EditField.DUE_TIME);
		newDate.set(2014, Calendar.DECEMBER, 14);
		output.put(Keys.OLD_VALUE, newDate);
		output.put(Keys.ITEM, item);
		UI.show(output);
		
	}


	public static void testPermaTodayScreen() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		Calendar date = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("Asd");
		tags.add("jkl");
		for(int i = 0; i<12; i++) {
			ItemData input = new ItemData(ItemType.EVENT);
			input.put(Keys.UID, i);
			input.put(Keys.TITLE, "dummyEvent" + i);
			input.put(Keys.START, date);
			date2.set(2104,Calendar.NOVEMBER,1,23,00);
			input.put(Keys.END, date2);
			input.put(Keys.HASHTAGS, tags);
			data.add(input);
		}
		UI.updateTodayScreen(data );
	}
	
	public static void testPermaToDoScreen() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		Calendar date = Calendar.getInstance();
		for(int i = 0; i<12; i++) {
			ItemData input = new ItemData(ItemType.TASK);
			input.put(Keys.UID, i);
			input.put(Keys.TITLE, "dummyTask" + i);
			input.put(Keys.DUE, date);
			input.put(Keys.HASHTAGS, new ArrayList<String>());
			data.add(input);
		}
		UI.updateTodoScreen(data );
	}
	
	public static void testListAll() {
		Engine engine = Engine.getInstance();
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY_TYPE, ListQuery.ALL);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData output = engine.execute(input);
		UI.show(output);
	}
	
	public static void testListDone() {
		Engine engine = Engine.getInstance();
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY_TYPE, ListQuery.DONE);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData output = engine.execute(input);
		UI.show(output);
	}

}

