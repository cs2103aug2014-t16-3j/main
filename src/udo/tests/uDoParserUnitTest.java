package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import udo.main.Parser;
import udo.util.parser.ParserAdd;
import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class uDoParserUnitTest {

	Parser p = new Parser();
	ParserAdd addActivity = new ParserAdd();
	
	String testEvent1 = "add #date with #jiawei from 30/9/14 11:09pm to 3/8/25 6:45pm";
	String testEvent2 = "add #cs2010 #homework finish it! from 12/12/13 11:30am to 11/3/14 5:45am";
	
	String testTask1 = "add make #friendship #bands by 12/12/13 9:31am";
	String testTask2 = "add finish homework by 11:30am";
	
	String testPlan1 = "add vending #machines no money";
	String testPlan2 = "add reflections by Saturday";
	
	
	@Test
	public void testSetFirstTimeAndDate() {
		Calendar start = addActivity.setFirstTimeAndDate(testTask1);
		int day = start.get(Calendar.DAY_OF_MONTH);
		int month = start.get(Calendar.MONTH);
		int year = start.get(Calendar.YEAR);
		int hour = start.get(Calendar.HOUR);
		int mins = start.get(Calendar.MINUTE);
		
		assertEquals(12, day);
		assertEquals(11, month);
		assertEquals(2013, year);
		assertEquals(9, hour); // noon and mid-night are represented by 0
		assertEquals(31, mins);
	}
	
	@Test
	public void testSetSecondTimeAndDate() {
		Calendar start = addActivity.setSecondTimeAndDate(testEvent1);
		int day = start.get(Calendar.DAY_OF_MONTH);
		int month = start.get(Calendar.MONTH);
		int year = start.get(Calendar.YEAR);
		int hour = start.get(Calendar.HOUR);
		int mins = start.get(Calendar.MINUTE);
		
		assertEquals(3, day);
		assertEquals(7, month);
		assertEquals(2025, year);
		assertEquals(6, hour); // noon and mid-night are represented by 0
		assertEquals(45, mins);
	}

	@Test
	public void testAdd() {
		InputData activity1 = addActivity.add(Command.ADD, testEvent1);
		ParsingStatus status1 = activity1.getStatus();
		Object title = activity1.get(Keys.TITLE);
		Object hashtags = activity1.get(Keys.HASHTAGS);
		Calendar startEvent = (Calendar) activity1.get(Keys.START);
		Calendar endEvent = (Calendar) activity1.get(Keys.END);
		
		int day = startEvent.get(Calendar.DAY_OF_MONTH);
		int month = startEvent.get(Calendar.MONTH);
		int year = startEvent.get(Calendar.YEAR);
		int hour = startEvent.get(Calendar.HOUR);
		int mins = startEvent.get(Calendar.MINUTE);
		
		assertEquals(ParsingStatus.SUCCESS, status1);
		assertEquals(Command.ADD_EVENT, activity1.getCommand());
		assertEquals("date with jiawei", title);
		assertEquals("[date, jiawei]", hashtags.toString());
		
		assertEquals(30, day);
		assertEquals(8, month);
		assertEquals(2014, year);
		assertEquals(11, hour);
		assertEquals(9, mins);
		
		day = endEvent.get(Calendar.DAY_OF_MONTH);
		month = endEvent.get(Calendar.MONTH);
		year = endEvent.get(Calendar.YEAR);
		hour = endEvent.get(Calendar.HOUR);
		mins = endEvent.get(Calendar.MINUTE);
		
		assertEquals(3, day);
		assertEquals(7, month);
		assertEquals(2025, year);
		assertEquals(6, hour);
		assertEquals(45, mins);
		
		InputData activity2 = addActivity.add(Command.ADD, testTask2);
		ParsingStatus status2 = activity2.getStatus();
		title = activity2.get(Keys.TITLE);
		hashtags = activity2.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status2);
		assertEquals(Command.ADD_TASK, activity2.getCommand());
		assertEquals("finish homework", title);
		assertEquals("[]", hashtags.toString());
		
		InputData activity3 = addActivity.add(Command.ADD, testPlan1);
		ParsingStatus status3 = activity3.getStatus();
		title = activity3.get(Keys.TITLE);
		hashtags = activity3.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status3);
		assertEquals(Command.ADD_PLAN, activity3.getCommand());
		assertEquals("vending machines no money", title);
		assertEquals("[machines]", hashtags.toString());
	}

}
