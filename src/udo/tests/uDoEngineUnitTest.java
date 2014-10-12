package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import udo.main.Engine;
import udo.util.engine.Cache;
import udo.util.engine.FileManager;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class uDoEngineUnitTest {
	
	@Test
	public void testEngineGetTodayItems() {
		Engine e = new Engine();
		 
		ArrayList<ItemData> todayList = e.getTodayScreenItems(Calendar.getInstance());
		assertEquals("length of todayList is currently 2",
				2,
				todayList.size());
	}
	
	@Test
	public void testEngineUndoDelete() {
		Engine e = new Engine();
		 
		
		InputData input1 = new InputData(Command.DELETE);
		input1.setParsingStatus(ParsingStatus.SUCCESS);
		input1.put(Keys.UID, 12345);
		e.execute(input1);
		
		InputData listInput = new InputData(Command.LIST);
		listInput.put(Keys.QUERY_TYPE, ListQuery.ALL);
		listInput.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData o = e.execute(listInput);
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> a = (ArrayList<ItemData>) o.get(Keys.ITEMS);
		assertEquals("num of items should be 1 after delete",
				1,
				a.size());
		
		InputData input2 = new InputData(Command.UNDO);
		input2.setParsingStatus(ParsingStatus.SUCCESS);
		e.execute(input2);
		
		OutputData o2 = e.execute(listInput);
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> a2 = (ArrayList<ItemData>) o2.get(Keys.ITEMS);
		assertEquals("num of items should still be 2 after undo",
				2,
				a2.size());
	}

	@Test
	public void testEngineDelete() {
		Engine e = new Engine();
		 
		InputData input = new InputData(Command.DELETE);
		input.setParsingStatus(ParsingStatus.SUCCESS);
		input.put(Keys.UID, 12345);
		OutputData o = e.execute(input);
		assertFalse("cannot be null", null == o);
		assertEquals("o success", ExecutionStatus.SUCCESS, o.getExecutionStatus());
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
		
		assertEquals("list size should be 2",
				2,
				s.size());
	}

	@Test
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

	@Test
	public void testEngineExecuteSave() {
		Engine e = new Engine();
		InputData in = new InputData(Command.SAVE);
		in.setParsingStatus(ParsingStatus.SUCCESS);
		OutputData out = e.execute(in);
		assertFalse("out should not be null", null == out);
		assertEquals("", ExecutionStatus.SUCCESS, out.getExecutionStatus());
	}

	/*@Test
	public void testEngineLoadFile() {
		assertTrue("the loading of the file should return true if successful",
				new Engine().loadFile());
	}*/

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

	/*
	 * @Test public void testFileManagerGetItemData() { FileManager fm = new
	 * FileManager(); ItemData iExp = new ItemData(ItemType.EVENT);
	 * iExp.put("title", "meeting"); iExp.put("date", "23/01/14");
	 * iExp.put("start time", "09.23am"); iExp.put("end time", "10.30pm");
	 * iExp.put("tags", "meeting, boss, work"); ItemData i = fm.getItemData(
	 * "event|||meeting|||23/01/14|||09.23am|||10.30pm|||meeting, boss, work");
	 * assertTrue("same item", iExp.equals(i)); }
	 */

	/*@Test
	public void testEngineLoadFileTrue() {
		Engine e = new Engine();
		assertTrue("the loading of the file should return true when successful",
				e.loadFile());
	}*/

	/*@Test
	public void testFileManagerReadFileOutput() {
		FileManager fm = new FileManager();
		String itemString = new String();
		try {
			ArrayList<ItemData> items = fm.getFromFile();
			for (ItemData item : items) {
				itemString = itemString.concat(item.toString());

			}
		} catch (IOException e) {
			itemString = "failed";
		}
		assertEquals(
				"the fileoutput should look like that",
				"12345|||EVENT|||meeting|||23/1/14|||9:23|||23/1/14|||22:30|||meeting,boss,work"
						+ "12346|||EVENT|||bowling|||24/1/14|||13:30|||24/1/14|||15:45|||boss,play,fun,date",
				itemString);
	}*/

}
