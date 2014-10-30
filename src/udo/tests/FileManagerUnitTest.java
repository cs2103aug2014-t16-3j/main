//@author A0108358B
package udo.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import udo.util.engine.FileManager;
import udo.util.exceptions.ReadingFromStorageException;
import udo.util.exceptions.WritingToStorageException;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.Constants.Keys;

public class FileManagerUnitTest {

	private static final String TEST_READ_FILENAME = "data/test_read_data.txt";
	private static final String TEST_WRITE_FILENAME = "data/test_write_data.txt";

	@Test
	public void testFMReadFile() {
		FileManager fm = new FileManager(TEST_READ_FILENAME);
		ArrayList<ItemData> list = null;
		
		try {
			list = fm.getFromFile();
		} catch (ReadingFromStorageException e) {
			fail("reading storage");
		} catch (IOException e) {
			fail("io");
		}
		
		assertNotEquals("list should not be null",
				null,
				list);
	}
	
	@SuppressWarnings("null")
	@Test
	public void testFMWriteFile() {
		// make two plans, 
		// then place them in the file
		// then get back and check the file got things or not.
		FileManager fm = new FileManager(TEST_WRITE_FILENAME);
		ArrayList<ItemData> list = new ArrayList<ItemData>();
		
		ItemData testPlan = new ItemData(ItemType.PLAN);
		testPlan.put(Keys.TITLE, "test plan 1");
		testPlan.put(Keys.UID, 12347);
		testPlan.put(Keys.HASHTAGS, new ArrayList<String>());
		ItemData testPlan2 = new ItemData(ItemType.PLAN);
		testPlan2.put(Keys.TITLE, "test plan 2");
		testPlan2.put(Keys.UID, 12348);
		testPlan2.put(Keys.HASHTAGS, new ArrayList<String>());
		
		list.add(testPlan);
		list.add(testPlan2);
		
		try {
			fm.writeToFile(list);
		} catch (IOException e) {
			fail("io");
		} catch (WritingToStorageException e) {
			fail("writing storage");
		}
		
		// writing passed.
		// now to get back.
		ArrayList<ItemData> getBack = null;
		try {
			 getBack = fm.getFromFile();
		} catch (ReadingFromStorageException e) {
			fail("reading storage");
		} catch (IOException e) {
			fail("io");
		}
		
		assertNotEquals("list should not be null",
				null,
				getBack);
		
		assertEquals("list should have the 2 things",
				getBack.size(),
				2);
		
	}

}
