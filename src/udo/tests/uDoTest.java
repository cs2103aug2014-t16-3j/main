package udo.tests;

import static org.junit.Assert.*;

import java.io.IOException;

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
				"testline 1testline 2",
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
