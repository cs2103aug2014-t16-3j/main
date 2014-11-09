//@author A0108358B
package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

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

public class EngineUnitTest {
	
	private static final int EVENT_UID = 12345;
	private static final int TASK_UID = 12346;
	private static final int PLAN_UID = 12347;

	@Test
	public void testEngineAddEventNotNull() {
		Engine e = Engine.getInstance();
		InputData inputTask = new InputData(Command.ADD_EVENT, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test event");
		inputTask.put(Keys.START, Calendar.getInstance());
		inputTask.put(Keys.END, Calendar.getInstance());
		OutputData output = e.execute(inputTask);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		ItemData addedEvent = (ItemData) output.get(Keys.ITEM);
		
		assertFalse("added item in output not null",
				null == addedEvent);
		
		assertEquals("added item is a event",
				ItemType.EVENT,
				addedEvent.getItemType());
	}

	@Test
	public void testEngineAddTask() {
		Engine e = Engine.getInstance();
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
	public void testEngineAddPlan() {
		Engine e = Engine.getInstance();
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
	public void testEngineListEvents() {
		Engine e = Engine.getInstance();
		InputData input = new InputData(Command.LIST, ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.EVENT);
		OutputData output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> list = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		
		assertFalse("list not null",
				null == list);
		
		System.out.println("List Events " + list.toString());
	}

	@Test
	public void testEngineListTasks() {
		Engine e = Engine.getInstance();
		InputData input = new InputData(Command.LIST, ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.TASK);
		OutputData output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> list = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		
		assertFalse("list not null",
				null == list);
		
		System.out.println("List tasks " + list.toString());
	}
	
	@Test
	public void testEngineListPlans() {
		Engine e = Engine.getInstance();
		InputData input = new InputData(Command.LIST, ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.PLAN);
		OutputData output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> list = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		
		assertFalse("list not null",
				null == list);
		
		System.out.println("List plans " + list.toString());
	}
	
	@Test
	public void testEngineListByDate() {
		Engine e = Engine.getInstance();
		Calendar queryCal = Calendar.getInstance();
		InputData input = new InputData(Command.LIST, ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.DATE);
		input.put(Keys.QUERY_VALUE, queryCal);
		OutputData output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> list = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		
		assertFalse("list not null",
				null == list);
		
		System.out.println("list by date " + list);
	}
	
	
	@Test
	public void testEngineListHashtag() {
		Engine e = Engine.getInstance();
		InputData input = new InputData(Command.LIST);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
		input.put(Keys.QUERY_VALUE, "meeting");
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
	public void testEngineListAll() {
		Engine e = Engine.getInstance();
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

	@Test
	public void testEngineListDone() {
		Engine e = Engine.getInstance();
		InputData input = new InputData(Command.MARK_DONE, ParsingStatus.SUCCESS);
		input.put(Keys.UID, TASK_UID);
		e.execute(input);
		
		input = new InputData(Command.LIST, ParsingStatus.SUCCESS);
		input.put(Keys.QUERY_TYPE, ListQuery.DONE);
		OutputData output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> items = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		
		assertNotEquals("items in output not null",
				null,
				items);
	}
	
	@Test
	public void testEngineMarkAndToggleDone() {
		Engine e = Engine.getInstance();
		
		InputData inputTask = new InputData(Command.ADD_TASK, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test task");
		inputTask.put(Keys.UID, TASK_UID);
		inputTask.put(Keys.HASHTAGS, new ArrayList<ItemData>());
		inputTask.put(Keys.DUE, Calendar.getInstance());
		e.execute(inputTask);
		
		InputData input = new InputData(Command.MARK_DONE, ParsingStatus.SUCCESS);
		input.put(Keys.UID, TASK_UID);
		OutputData output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		ItemData item = (ItemData) output.get(Keys.ITEM);
		
		assertFalse("edited item in output not null",
				null == item);
		
		assertTrue("ensure edited item marked", 
				(boolean)item.get(Keys.DONE));
		
		System.out.println(item);
		
		input = new InputData(Command.TOGGLE_DONE, ParsingStatus.SUCCESS);
		input.put(Keys.UID, TASK_UID);
		output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
		
		item = (ItemData) output.get(Keys.ITEM);
		
		assertFalse("edited item in output not null",
				null == item);
		
		assertFalse("ensure edited item unmarked", 
				(boolean)item.get(Keys.DONE));
		
		System.out.println(item);
	}
	
	@Test
	public void testEngineEditItemEndTime() {
		Engine e = Engine.getInstance();
		
		InputData inputTask = new InputData(Command.ADD_EVENT, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test event");
		inputTask.put(Keys.UID, EVENT_UID);
		inputTask.put(Keys.HASHTAGS, new ArrayList<ItemData>());
		inputTask.put(Keys.START, Calendar.getInstance());
		inputTask.put(Keys.END, Calendar.getInstance());
		e.execute(inputTask);
		
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, EVENT_UID);
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
		Engine e = Engine.getInstance();
		
		InputData inputTask = new InputData(Command.ADD_TASK, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test task");
		inputTask.put(Keys.UID, TASK_UID);
		inputTask.put(Keys.HASHTAGS, new ArrayList<ItemData>());
		inputTask.put(Keys.DUE, Calendar.getInstance());
		e.execute(inputTask);
		
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, TASK_UID);
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
		Engine e = Engine.getInstance();
		
		InputData inputTask = new InputData(Command.ADD_TASK, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test task");
		inputTask.put(Keys.UID, TASK_UID);
		inputTask.put(Keys.HASHTAGS, new ArrayList<ItemData>());
		inputTask.put(Keys.DUE, Calendar.getInstance());
		e.execute(inputTask);
		
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, TASK_UID);
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
		Engine e = Engine.getInstance();
		
		InputData inputTask = new InputData(Command.ADD_TASK, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test task");
		inputTask.put(Keys.UID, TASK_UID);
		inputTask.put(Keys.HASHTAGS, new ArrayList<ItemData>());
		inputTask.put(Keys.DUE, Calendar.getInstance());
		e.execute(inputTask);
		
		InputData inputEdit = new InputData(Command.EDIT, ParsingStatus.SUCCESS);
		inputEdit.put(Keys.UID, TASK_UID);
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
	public void testEngineGetUpcomingTasks() {
		Engine e = Engine.getInstance();
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
		Engine e = Engine.getInstance();
		ArrayList<ItemData> todayList = e.getTodayScreenItems(Calendar.getInstance());
		
		assertFalse("todayList is not null",
				null == todayList);
	}

	@Test
	public void testEngineDelete() {
		Engine e = Engine.getInstance();
		
		InputData inputTask = new InputData(Command.ADD_EVENT, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test event");
		inputTask.put(Keys.UID, EVENT_UID);
		inputTask.put(Keys.HASHTAGS, new ArrayList<ItemData>());
		inputTask.put(Keys.START, Calendar.getInstance());
		inputTask.put(Keys.END, Calendar.getInstance());
		e.execute(inputTask);
		
		InputData input = new InputData(Command.DELETE);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		input.put(Keys.UID, EVENT_UID);
		OutputData output = e.execute(input);
		
		assertFalse("Output must not be null",
				null == output);
		
		assertEquals("Execution must be successful",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
	}
	
	@Test
	// delete uid is split into:
		// positive uid (valid)
		// negative uid (invalid)
	// within the valid range, it is also partitioned into: 
		// 1. uid exist 
		// 2. uid do not exist
	public void testEngineDeleteBoundaryCasesForUID() {
		Engine e = Engine.getInstance();
		
		// set up item
		InputData inputTask = new InputData(Command.ADD_EVENT, ParsingStatus.SUCCESS);
		inputTask.put(Keys.TITLE, "test event");
		inputTask.put(Keys.UID, EVENT_UID);
		inputTask.put(Keys.HASHTAGS, new ArrayList<ItemData>());
		inputTask.put(Keys.START, Calendar.getInstance());
		inputTask.put(Keys.END, Calendar.getInstance());
		e.execute(inputTask);
		
		InputData input = new InputData(Command.DELETE, ParsingStatus.SUCCESS);
		
		// this is the boundary case for negative uid partition 
		input.put(Keys.UID, -1);
		OutputData output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution should be fail",
				ExecutionStatus.FAIL,
				output.getExecutionStatus());
		
		// we cannot confirm if a uid does not exist
		// because the data file can be modified 
		// before the test case is run
	
		// this is a case for the positive and exists partition
		input.put(Keys.UID, EVENT_UID);
		output = e.execute(input);
		
		assertFalse("output not null",
				null == output);
		
		assertEquals("execution should be success",
				ExecutionStatus.SUCCESS,
				output.getExecutionStatus());
	}

	@Test // save is still buggy
	public void testEngineSave() {
		Engine e = Engine.getInstance();
		InputData in = new InputData(Command.SAVE);
		in.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData out = e.execute(in);
		
		assertFalse("out should not be null", null == out);
		
		assertEquals("", ExecutionStatus.SUCCESS, out.getExecutionStatus());
	}

	@Test 
	public void testEngineExit() {
		Engine e = Engine.getInstance();
		OutputData o = e.execute(new InputData(Command.EXIT, ParsingStatus.SUCCESS));
		
		assertEquals("the output status shud be success",
				ExecutionStatus.SUCCESS,
				o.getExecutionStatus());
		
		assertEquals("the output command should be exit",
				Command.EXIT,
				o.getCommand());
	}

}
