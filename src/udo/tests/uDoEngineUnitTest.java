package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import udo.main.Engine;
import udo.util.engine.Cache;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class uDoEngineUnitTest {
	
	private static final int TEST_UID = 12345;
	
	@Test
	public void testEngineEditItemEndTime() {
		Engine e = new Engine();
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, 12345);
		inputEdit.put("field", EditField.END_TIME);
		inputEdit.put("value", Calendar.getInstance());
		OutputData output = e.execute(inputEdit);
		assertFalse("output not null",
				null == output);
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		ItemData edited = (ItemData) output.get(Keys.ITEM);
		assertFalse("edited item in output not null",
				null == edited);
		Calendar editedCal = (Calendar) edited.get(Keys.END);
		assertEquals("ensure edited item actaully got edited",
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 
				editedCal.get(Calendar.HOUR_OF_DAY));
		System.out.println(edited);
	}
	
	@Test
	public void testEngineEditItemDueTime() {
		Engine e = new Engine();
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, 12346);
		inputEdit.put("field", EditField.DUE_TIME);
		inputEdit.put("value", Calendar.getInstance());
		OutputData output = e.execute(inputEdit);
		assertFalse("output not null",
				null == output);
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		ItemData edited = (ItemData) output.get(Keys.ITEM);
		assertFalse("edited item in output not null",
				null == edited);
		Calendar editedCal = (Calendar) edited.get(Keys.DUE);
		assertEquals("ensure edited item actaully got edited",
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 
				editedCal.get(Calendar.HOUR_OF_DAY));
		System.out.println(edited);
	}

	@Test
	public void testEngineEditItemDueDate() {
		Engine e = new Engine();
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, 12346);
		inputEdit.put("field", EditField.DUE_DATE);
		inputEdit.put("value", Calendar.getInstance());
		OutputData output = e.execute(inputEdit);
		assertFalse("output not null",
				null == output);
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		ItemData edited = (ItemData) output.get(Keys.ITEM);
		assertFalse("edited item in output not null",
				null == edited);
		Calendar editedCal = (Calendar) edited.get(Keys.DUE);
		assertEquals("ensure edited item actaully got edited",
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 
				editedCal.get(Calendar.DAY_OF_MONTH));
		System.out.println(edited);
	}
	
	@Test
	public void testEngineEditItemTitle() {
		Engine e = new Engine();
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, TEST_UID);
		inputEdit.put("field", EditField.TITLE);
		inputEdit.put("value", "dummy title");
		OutputData output = e.execute(inputEdit);
		assertFalse("output not null",
				null == output);
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		ItemData edited = (ItemData) output.get(Keys.ITEM);
		assertFalse("edited item in output not null",
				null == edited);
		assertEquals("ensure edited item actaully got edited",
				"dummy title", 
				edited.get(Keys.TITLE));
		System.out.println(edited);
	}
	
	@Test
	public void testEngineAddPlan() {
		Engine e = new Engine();
		InputData inputPlan = new InputData(Command.ADD_PLAN, ParsingStatus.SUCCESS);
		inputPlan.put(Keys.TITLE, "test plan");
		inputPlan.put(Keys.DUE, Calendar.getInstance());
		OutputData output = e.execute(inputPlan);
		assertFalse("output not null",
				null == output);
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		ItemData addedPlan = (ItemData) output.get(Keys.ITEM);
		assertFalse("added item in output not null",
				null == addedPlan);
		assertEquals("added item is a plan",
				ItemType.PLAN, 
				addedPlan.getItemType());
	}
	
	@Test
	public void testEngineAddTask() {
		Engine e = new Engine();
		InputData inputTask = new InputData(Command.ADD_TASK, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test task");
		inputTask.put(Keys.DUE, Calendar.getInstance());
		OutputData output = e.execute(inputTask);
		assertFalse("output not null",
				null == output);
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		ItemData addedTask = (ItemData) output.get(Keys.ITEM);
		assertFalse("added item in output not null",
				null == addedTask);
		assertEquals("added item is a task",
				ItemType.TASK,
				addedTask.getItemType());
	}
	
	@Test
	public void testEngineGetUpcomingTasks() {
		Engine e = new Engine();
		Calendar from = Calendar.getInstance();
		from.setLenient(true);
		Calendar to = Calendar.getInstance();
		to.set(Calendar.DAY_OF_YEAR, to.get(Calendar.DAY_OF_YEAR) + 3);
		ArrayList<ItemData> itemsToShow = e.getTodoScreenItems(from, to);
		assertFalse("todayList is not null",
				null == itemsToShow);
		System.out.println(itemsToShow);
	}

	@Test
	public void testEngineGetTodayItems() {
		Engine e = new Engine();
		ArrayList<ItemData> todayList = e.getTodayScreenItems(Calendar.getInstance());
		assertFalse("todayList is not null",
				null == todayList);
	}

	@Test
	public void testEngineDelete() {
		Engine e = new Engine();
		InputData input = new InputData(Command.DELETE);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		input.put(Keys.UID, TEST_UID);
		OutputData output = e.execute(input);
		assertFalse("Output must not be null",
				null == output);
		assertEquals("Execution must be successful",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
	}
	
	@Test
	public void testEngineExecuteListHash() {
		Engine e = new Engine();
		InputData input = new InputData(Command.LIST);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
		input.put(Keys.HASHTAG, "meeting");
		OutputData o = e.execute(input);
		
		assertFalse("output object cant be null",
				o == null);
		
		assertEquals("the output status shud be success",
				ExecutionStatus.SUCCESS,
				o.getExecutionStatus());
		
		assertEquals("the output command should be list",
				Command.LIST,
				o.getCommand());
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> s = (ArrayList<ItemData>) o.get(Keys.ITEMS);
		System.out.println(s.toString());
	}

	@Test
	public void testEngineExecuteListAll() {
		Engine e = new Engine();
		InputData input = new InputData(Command.LIST);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.ALL);
		OutputData o = e.execute(input);
		
		assertFalse("output object cant be null",
				o == null);
		
		assertEquals("the output status shud be success",
				ExecutionStatus.SUCCESS,
				o.getExecutionStatus());
		
		assertEquals("the output command should be list",
				Command.LIST,
				o.getCommand());
		
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> s = (ArrayList<ItemData>) o.get(Keys.ITEMS);
		
		assertFalse("there should be items",
				null == s);
	}

	@Test // contains buggy save
	public void testEngineExecuteExit() {
		Engine e = new Engine();
		 
		OutputData o = e.execute(new InputData(Command.EXIT, ParsingStatus.SUCCESS));
		assertEquals("the output status shud be success",
				ExecutionStatus.SUCCESS,
				o.getExecutionStatus());
		assertEquals("the output command should be exit",
				Command.EXIT,
				o.getCommand());
	}

	@Test
	public void testEngineExecuteAddEventNotNull() {
		Engine e = new Engine();
		 
		InputData in = new InputData(Command.ADD_EVENT);
		in.setParsingStatus(ParsingStatus.SUCCESS);
		assertFalse("", null == e.execute(in));
	}

	@Test // save is still buggy
	public void testEngineExecuteSave() {
		Engine e = new Engine();
		InputData in = new InputData(Command.SAVE);
		in.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData out = e.execute(in);
		assertFalse("out should not be null", null == out);
		assertEquals("", ExecutionStatus.SUCCESS, out.getExecutionStatus());
	}

	@Test
	public void testCacheAddItemSizeIncrease() {
		Cache c = new Cache();
		int oldSize = c.size();
		ItemData i = new ItemData(ItemType.EVENT);
		i.put("title", "asd");
		ItemData ii = new ItemData(ItemType.EVENT);
		ii.put("title", "asdasd");
		assertTrue("add success", c.addItem(i));
		assertTrue("add success", c.addItem(ii));
		assertEquals("the new size should be larger than old size by 2",
				c.size(), oldSize + 2);
	}

	@Test
	public void testItemDataMatch() {
		ItemData i1 = new ItemData(ItemType.EVENT);
		i1.put("a", "a");
		i1.put("b", "ajshdgf");
		ItemData i2 = new ItemData(ItemType.EVENT);
		i2.put("a", "a");
		i2.put("b", "ajshdgf");
		assertTrue(i1.equals(i2));
	}

}
