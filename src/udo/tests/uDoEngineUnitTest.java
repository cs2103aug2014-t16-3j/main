package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

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

public class uDoEngineUnitTest {
	
	@Test
	public void testEngineUndoDelete() {
		Engine e = new Engine();
		e.loadFile();
		InputData input = new InputData(Command.DELETE);
		input.put(Keys.UID, 12345);
		e.execute(input);
	}
	
	@Test
	public void testEngineDelete() {
		Engine e = new Engine();
		e.loadFile();
		InputData input = new InputData(Command.DELETE);
		input.put(Keys.UID, 12345);
		OutputData o = e.execute(input);
		assertFalse("cannot be null",
				null == o);
		assertEquals("o success",
				ExecutionStatus.SUCCESS,
				o.getStatus());
	}
	
	@Test
	public void testEngineExecuteListAll() {
		Engine e = new Engine();
		if (!e.loadFile()) fail("load fail");
		InputData input = new InputData(Command.LIST);
		input.put(Keys.QUERY, ListQuery.ALL);
		OutputData o = e.execute(input);
		assertFalse("output object cant be null",
				o == null);
		assertEquals("the output status shud be success",
				ExecutionStatus.SUCCESS,
				o.getStatus());
		assertEquals("the output command should be list",
				Command.LIST,
				o.getCommand());
		@SuppressWarnings("unchecked")
		ArrayList<ItemData> s = (ArrayList<ItemData>) o.get(Keys.ITEMS);
		assertEquals("",
				2,
				s.size());
		System.out.println(s.get(0).toString());
		System.out.println(s.get(1).toString());
	}
	
	@Test
	public void testEngineExecuteExit() {
		Engine e = new Engine();
		e.loadFile();
		OutputData o = e.execute(new InputData(Command.EXIT));
		assertEquals("the output status shud be success",
				ExecutionStatus.SUCCESS,
				o.getStatus());
		assertEquals("the output command should be exit",
				Command.EXIT,
				o.getCommand());
	}
	
	@Test
	public void testEngineExecuteAddEventNotNull() {
		Engine e = new Engine();
		e.loadFile();
		InputData in = new InputData(Command.ADD_EVENT);
		assertFalse("",
				null == e.execute(in));
	}
	
	@Test
	public void testEngineExecuteSave() {
		Engine e = new Engine();
		boolean loadOK = e.loadFile();
		if (!loadOK) fail("load failed");
		InputData in = new InputData(Command.SAVE);
		OutputData out = e.execute(in);
		assertFalse("out should not be null",
				null == out);
		assertEquals("",
				ExecutionStatus.SUCCESS,
				out.getStatus());
	}
	
	@Test
	public void testEngineLoadFile() {
		assertTrue("the loading of the file should return true if successful",
				new Engine().loadFile());
	}
	
	@Test
	public void testCacheAddItemSizeIncrease() {
		Cache c = new Cache();
		int oldSize = c.size();
		ItemData i = new ItemData(ItemType.EVENT);
		i.put("title", "asd");
		ItemData ii = new ItemData(ItemType.EVENT);
		ii.put("title", "asdasd");
		assertTrue("add success",
				c.add(i));
		assertTrue("add success",
				c.add(ii));
		assertEquals("the new size should be larger than old size by 2",
				c.size(),
				oldSize + 2);
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
	
	/*@Test
	public void testFileManagerGetItemData() {
		FileManager fm = new FileManager();
		ItemData iExp = new ItemData(ItemType.EVENT);
		iExp.put("title", "meeting");
		iExp.put("date", "23/01/14");
		iExp.put("start time", "09.23am");
		iExp.put("end time", "10.30pm");
		iExp.put("tags", "meeting, boss, work");
		ItemData i = fm.getItemData("event|||meeting|||23/01/14|||09.23am|||10.30pm|||meeting, boss, work");
		assertTrue("same item",
				iExp.equals(i));
	}*/

	@Test
	public void testEngineLoadFileTrue() {
		Engine e = new Engine();
		assertTrue("the loading of the file should return true"
				+ "when successful", 
				e.loadFile());
	}
	
	@Test
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
		assertEquals("the fileoutput should look like that",
				"12345|||EVENT|||meeting|||23/1/14|||9:23|||23/1/14|||22:30|||meeting,boss,work"
				+ "12346|||EVENT|||bowling|||24/1/14|||13:30|||24/1/14|||15:45|||boss,play,fun,date",
				itemString);
	}

}
