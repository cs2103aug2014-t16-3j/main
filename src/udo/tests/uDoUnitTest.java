package udo.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import udo.main.Engine;
import udo.main.FileManager;
import udo.util.Command;
import udo.util.InputData;

public class uDoUnitTest {

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
				"event|meeting|23/01/14|09.23am|10.30pmevent|bowling|24/01/14|12.30pm|3.45pm",
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
