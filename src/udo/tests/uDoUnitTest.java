package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import udo.main.Cache;
import udo.main.Engine;
import udo.main.FileManager;
import udo.util.Command;
import udo.util.InputData;
import udo.util.ItemData;
import udo.util.OutputData;
import udo.util.Status;

public class uDoUnitTest {
	
	@Test
	public void testEngineExecuteAddEventNotNull() {
		Engine e = new Engine();
		InputData in = new InputData(Command.ADD_EVENT);
		assertFalse("",
				null == e.execute(in));
	}
	
	@Test
	public void testEngineExecuteSave() {
		Engine e = new Engine();
		InputData in = new InputData(Command.SAVE);
		OutputData out = e.execute(in);
		assertEquals("",
				Status.SUCCESS,
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
		ItemData i = new ItemData();
		i.put("type", "event");
		i.put("title", "asd");
		ItemData ii = new ItemData();
		ii.put("type", "event");
		ii.put("title", "asdasd");
		c.addItem(i);
		c.addItem(ii);
		assertEquals("the new size should be larger than old size by 1",
				c.size(),
				oldSize + 2);
	}
	
	@Test
	public void testItemDataMatch() {
		ItemData i1 = new ItemData();
		i1.put("a", "a");
		i1.put("b", "ajshdgf");
		ItemData i2 = new ItemData();
		i2.put("a", "a");
		i2.put("b", "ajshdgf");
		assertTrue(i1.equals(i2));
	}
	
	@Test
	public void testFileManagerGetItemData() {
		FileManager fm = new FileManager();
		ItemData iExp = new ItemData();
		iExp.put("type", "event");
		iExp.put("title", "meeting");
		iExp.put("date", "23/01/14");
		iExp.put("start time", "09.23am");
		iExp.put("end time", "10.30pm");
		iExp.put("tags", "meeting, boss, work");
		ItemData i = fm.getItemData("event|||meeting|||23/01/14|||09.23am|||10.30pm|||meeting, boss, work");
		assertTrue("same item",
				iExp.equals(i));
	}

	@Test
	public void testEngineLoadFileTrue() {
		Engine e = new Engine();
		InputData id = new InputData(Command.ADD_EVENT);
		assertTrue("the adding of an item should give a non-null input data object"
				+ "when successful", 
				e.loadFile());
	}
	
	@Test
	public void testFileManagerReadFileOutput() {
		FileManager fm = new FileManager();
		fm.openFile();
		String fileoutput = new String();
		while (fm.hasNext()) {
			try {
				fileoutput = fileoutput.concat(fm.getNextLine());
			} catch (IOException e) {
				break;
			}
		}
		assertEquals("the fileoutput should look like that",
				"event|||meeting|||23/01/14|||09.23am|||10.30pm|||meeting, boss, work"
				+ "event|||bowling|||24/01/14|||12.30pm|||3.45pm|||play, fun, date",
				fileoutput);
	}
	
	@Test
	public void testFileManagerOpenFileTrue() {
		FileManager fm = new FileManager();
		assertTrue("should be true if the file can be opened, "
				+ "or created first and then opened",
				fm.openFile());
	}

}
