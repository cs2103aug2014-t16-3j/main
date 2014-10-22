package udo.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import udo.main.Parser;
import udo.util.parser.ParserAdd;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

public class uDoParserUnitTest {

	Parser p = new Parser();
	ParserAdd pa = new ParserAdd();
	String plan1 = "add finish #cs2103 stuff";
	String task1 = "add Meet jane after #school #by #22/10/2014 7:30am";
	String event1 = "add meet #boss #from 22/10/2014 10:00am to 22/10/2014 9:00pm";
	
	@Test
	public void testEditTitle() {
		String editTitle = "edit 72384 title hello #everybody";
		InputData data = p.getInputData(editTitle);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand();
		EditField field = (EditField) data.get(Keys.FIELD);
		Object title = data.get(Keys.VALUE);
		ArrayList<String> tags = (ArrayList<String>) data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.TITLE, field);
		assertEquals("hello everybody", title);
		assertEquals("[everybody]", tags.toString());
		
	}
	
	@Test
	public void testListHashtag() {
		String listTag = "list #lala";
		InputData data = p.getInputData(listTag);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.SINGLE_HASHTAG, type);
		
	}
	
	@Test
	public void testListDate() {
		String listDate = "list 20/1/2014";
		InputData data = p.getInputData(listDate);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.DATE, type);
	}
	
	@Test
	public void testListAll() {
		String listAll = "list ALL";
		InputData data = p.getInputData(listAll);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.ALL, type);
	}
	
	@Test
	public void testAddPlan() {
		InputData data = p.getInputData(plan1);
		ParsingStatus status = data.getStatus();
		Object title = data.get(Keys.TITLE);
		Object hashtags = data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.ADD_PLAN, data.getCommand());
		assertEquals("finish cs2103 stuff", title);
		assertEquals("[cs2103]", hashtags.toString());
	}
	
	@Test
	public void testAddTask() {
		InputData data = p.getInputData(task1);
		ParsingStatus status = data.getStatus();
		Object title = data.get(Keys.TITLE);
		Object hashtags = data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.ADD_TASK, data.getCommand());
		assertEquals("Meet jane after school", title);
		assertEquals("[school, by, 22/10/2014]", hashtags.toString());
		
		Calendar due = (Calendar) data.get(Keys.DUE);
		int day = due.get(Calendar.DAY_OF_MONTH);
		int month = due.get(Calendar.MONTH);
		int year = due.get(Calendar.YEAR);
		int hour = due.get(Calendar.HOUR);
		int mins = due.get(Calendar.MINUTE);
		
		assertEquals(22, day);
		assertEquals(9, month);
		//assertEquals(2014, year);
		assertEquals(7, hour);
		assertEquals(30, mins);
	}
	
	@Test
	public void testAddEvent() {
		InputData data = p.getInputData(event1);
		ParsingStatus status = data.getStatus();
		Object title = data.get(Keys.TITLE);
		Object hashtags = data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.ADD_EVENT, data.getCommand());
		assertEquals("meet boss", title);
		assertEquals("[boss, from]", hashtags.toString());
		
		Calendar startEvent = (Calendar) data.get(Keys.START);
		Calendar endEvent = (Calendar) data.get(Keys.END);
		
		int day = endEvent.get(Calendar.DAY_OF_MONTH);
		int month = endEvent.get(Calendar.MONTH);
		int year = endEvent.get(Calendar.YEAR);
		int hour = endEvent.get(Calendar.HOUR);
		int mins = endEvent.get(Calendar.MINUTE);
		
		assertEquals(22, day);
		assertEquals(9, month);
		//assertEquals(2014, year);
		assertEquals(9, hour);
		assertEquals(0, mins);
	}
	
}
