package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import udo.main.Parser;
import udo.util.shared.Command;
import udo.util.shared.InputData;

public class uDoParserUnitTest {

	Parser p = new Parser();
	String input = "add activity on 03/01/2014 from 9:24pm to 10:30pm";
	String inputTag = "add activity #2103 on 03/01/2014 from 9:24pm to 10:30pm";
	String deleteLine = "delete 2";
	//add <title> <hashTags, if any> on <date> from <start time> to <end time>
	
	@Test
	public void testDetermineCommandType() {
		Command type = p.determineCommandType(input);
		assertTrue(type.equals(Command.ADD_EVENT));
	}
	
	@Test
	public void testIsValidAdd() {
		assertTrue(p.isValidAdd(input));
	}
	
	@Test
	public void testGetTitle() {
		String title = p.getTitle(input);
		assertTrue(title.equals("activity"));
	}
	
	@Test 
	public void testGetDate() {
		
	}
	
	@Test
	public void testGetTags() {
		
	}
}
