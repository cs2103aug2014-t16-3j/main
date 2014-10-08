package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import udo.main.Parser;
import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class uDoParserUnitTest {

	Parser p = new Parser();
	String test1 = "add #date with #jiawei from 30/9/14 12:30pm to 30/9/14 6:45pm";
	String test2 = "add #cs2010 #homework finish it! from 12/12/13 11:30am to 11/3/14 5:45am";
	
	@Test
	public void testGetTitle() {
		String title1 = p.getTitle(test1);
		String title2 = p.getTitle(test2);
		assertEquals("date with jiawei", title1);
		assertEquals("cs2010 homework finish it!", title2);
	}
	
	@Test 
	public void testSetEventStartTime() {
		Calendar start = p.setEventStartTime(test1);
		int day = start.get(Calendar.DAY_OF_MONTH);
		int month = start.get(Calendar.MONTH);
		int year = start.get(Calendar.YEAR);
		int hour = start.get(Calendar.HOUR);
		int mins = start.get(Calendar.MINUTE);
		
		assertEquals(day, 30);
		assertEquals(month, 8);
		assertEquals(year, 2014);
		assertEquals(hour, 0); // noon and mid-night are represented by 0
		assertEquals(mins, 30);
	}
	
	@Test
	public void testSetEventEndTime() {
		Calendar start = p.setEventEndTime(test2);
		int day = start.get(Calendar.DAY_OF_MONTH);
		int month = start.get(Calendar.MONTH);
		int year = start.get(Calendar.YEAR);
		int hour = start.get(Calendar.HOUR);
		int mins = start.get(Calendar.MINUTE);
		
		assertEquals(day, 11);
		assertEquals(month, 2);
		assertEquals(year, 2014);
		assertEquals(hour, 5);
		assertEquals(mins, 45);
	}
	
	String tagTest1 = "list #duo "; // requires a space behind tag
	
	@Test
	public void testGetTags() {
		ArrayList<String> t = p.getTags(tagTest1);
		
		assertEquals("duo", t.get(0));
	}
	
	//InputData list(Command type, String details)
	
	@Test
	public void testList() {
		InputData data = p.list(Command.LIST, tagTest1);
		Object tag = data.get(Keys.HASHTAG);
		assertEquals("duo", tag.toString());
	}
}
