package udo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import udo.main.Parser;
import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class uDoParserUnitTest {

	Parser p = new Parser();
	String input = "add activity on 03/01/2014 from 9:24pm to 10:30pm";
	String inputTag = "add activity #2103 on 03/01/2014 from 9:24pm to 10:30pm";
	String deleteLine = "delete 2";

	// add <title> <hashTags, if any> on <date> from <start time> to <end time>

	@Test
	public void testDetermineCommandType() {
		Command type = p.determineCommandType(input);
		assertTrue(type.equals(Command.ADD_EVENT));
	}

	@Test
	public void testIsValidAdd() {
		assertTrue(p.isValidAdd(input));
	}

	String getDateTest1 = "add activity on 1/9/33 from 9:24pm to 10:30pm";
	String getDateTest2 = "add activity on 13/12/33 from 9:24pm to 10:30pm";

	@Test
	public void testGetDate() {
		Calendar cal1 = p.getDate(getDateTest1);
		Calendar cal2 = p.getDate(getDateTest2);
		
		assertEquals(cal1.get(Calendar.DAY_OF_MONTH), 1);
		assertEquals(cal1.get(Calendar.MONTH), 8);
		assertEquals(cal1.get(Calendar.YEAR), 2033);
		
		assertEquals(cal2.get(Calendar.DAY_OF_MONTH), 13);
		assertEquals(cal2.get(Calendar.MONTH), 11);
		assertEquals(cal2.get(Calendar.YEAR), 2033);
	}
	
	String dateTest1 = "add activity on 3/1/15 from 9:24pm to 10:30pm";
	String dateTest2 = "add activity on 13/10/14 from 9:24pm to 10:30pm";
	String dateTest3 = "add activity on 3/10/14 from 9:24pm to 10:30pm";
	String dateTest4 = "add activity on 13/10/34 from 9:24pm to 10:30pm";
	String dateTest5 = "add activity on 13/ 1/ 14 from 9:24pm to 10:30pm";

	@Test
	public void testGetDateString() {
		String dateString1 = p.getDateString(dateTest1);
		String dateString2 = p.getDateString(dateTest2);
		String dateString3 = p.getDateString(dateTest3);
		String dateString4 = p.getDateString(dateTest4);
		//corner case:
		String dateString5 = p.getDateString(dateTest5);
		
		assertEquals(dateString1, "3/1/15");
		assertEquals(dateString2, "13/10/14");
		assertEquals(dateString3, "3/10/14");
		assertEquals(dateString4, "13/10/34");
		//assertEquals(dateString5, "13/1/14");
	}
	
	String formatDateTest1 = "3/1/14";
	String formatDateTest2 = "13/5/34";
	String formatDateTest3 = "3/11/14";
	
	@Test
	public void testfFormatDateSubstring() {
		Calendar cal1 = p.formatDateSubstring(formatDateTest1);
		Calendar cal2 = p.formatDateSubstring(formatDateTest2);
		Calendar cal3 = p.formatDateSubstring(formatDateTest3);
		
		assertEquals(cal1.get(Calendar.DAY_OF_MONTH), 3);
		assertEquals(cal1.get(Calendar.MONTH), 0);
		assertEquals(cal1.get(Calendar.YEAR), 2014);
		
		assertEquals(cal2.get(Calendar.DAY_OF_MONTH), 13);
		assertEquals(cal2.get(Calendar.MONTH), 4);
		assertEquals(cal2.get(Calendar.YEAR), 2034);
		
		assertEquals(cal3.get(Calendar.DAY_OF_MONTH), 3);
		assertEquals(cal3.get(Calendar.MONTH), 10);
		assertEquals(cal3.get(Calendar.YEAR), 2014);
	}
	
	String timeTest1 = "add activity on 3/1/15 from 9:24am to 10:30pm";
	String timeTest2 = "add activity on 13/10/14 from 10:00pm to 10:30pm";
	String timeTest3 = "add activity on 3/10/14 from 12:59am to 10:30pm";
	String timeTest4 = "add activity on 13/10/34 from 2:00am to 10:30pm";
	
	@Test
	public void testGetTimeString() {
		String timeString1 = p.getTimeString(timeTest1);
		String timeString2 = p.getTimeString(timeTest2);
		String timeString3 = p.getTimeString(timeTest3);
		String timeString4 = p.getTimeString(timeTest4);
		
		assertEquals(timeString1, "9:24AM");
		assertEquals(timeString2, "10:00PM");
		assertEquals(timeString3, "12:59AM");
		assertEquals(timeString4, "2:00AM");
	}
	
	String titleTest1 = "add finish cs2103 class diagram on 3/1/15 from 9:24am to 10:30pm";
	String titleTest2 = "add date with leo #date on 30/9/14 from 12:30pm to 6:45pm";
	String titleTest3 = "add complete ps4 by 30/9/14 from 9:24am to 10:30pm";
	
	@Test
	public void testGetTitle() {
		String title1 = p.getTitle(titleTest1);
		String title2 = p.getTitle(titleTest2);
		String title3 = p.getTitle(titleTest3);
		
		assertEquals(title1, "finish cs2103 class diagram ");
		assertEquals(title2, "date with leo ");
		assertEquals(title3, "complete ps4 ");
	}
	
	String addEventTest1 = "add date with leo #date on 30/9/14 from 12:30pm to 6:45pm";
	String addEventTest2 = "add complete ps4 by 30/9/14 from 9:24am to 10:30pm";

	@Test
	public void testAddEvent() {
		Command type = Command.ADD_EVENT;
		InputData inputData1 = p.addEvent(type, addEventTest1);
		InputData inputData2 = p.addEvent(type, addEventTest1);
		
		assertEquals(inputData1.getStatus(), ParsingStatus.SUCCESS);
		assertEquals(inputData2.getStatus(), ParsingStatus.SUCCESS);
	}
}
