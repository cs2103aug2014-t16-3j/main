package udo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import udo.main.Engine;
import udo.main.FileManager;
import udo.util.InputData;

public class uDoTest {

	@Test
	public void testEngineAddEventNotNull() {
		Engine e = new Engine();
		InputData id = null;
		assertFalse("the adding of an item should give a non-null input data object"
				+ "when successful", 
				e.addItem(id) == null);
	}
	
	@Test
	public void testFileManagerOpenFileTrue() {
		FileManager fm = new FileManager();
		assertTrue("should be true if the file can be opened.",
				fm.openFile());
	}

}
