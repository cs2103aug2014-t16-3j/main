//@author A0114847B
package udo.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.EditField;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;
import udo.parser.Parser;

public class ParserUnitTest {

	Parser p = new Parser();
	Calendar cal = Calendar.getInstance();
	
	@Test
	public void testSearchAction() {
		String search = "search jo ba b";
		InputData data = p.getInputData(search);
		ParsingStatus status = data.getStatus();
		
		assertEquals(ParsingStatus.SUCCESS, status);
		
		search = "search s";
		data = p.getInputData(search);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.SUCCESS, status);
		
		search = "search ";
		data = p.getInputData(search);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}
	
	@Test
	public void testListPlan() {
		String listPlan = "list plan";
		InputData data = p.getInputData(listPlan);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.PLAN, type);
		
		listPlan = "listPlan";
		data = p.getInputData(listPlan);
		status = data.getStatus();
		type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.FAIL, status);
		assertEquals(Command.NULL, data.getCommand());
	}
	
	@Test
	public void testListTask() {
		String listTask = "list task";
		InputData data = p.getInputData(listTask);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.TASK, type);
		
		listTask = "listTask";
		data = p.getInputData(listTask);
		status = data.getStatus();
		type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.FAIL, status);
		assertEquals(Command.NULL, data.getCommand());
		
	}
	
	@Test
	public void testListEvent() {
		String listEvents = "list Events";
		InputData data = p.getInputData(listEvents);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.EVENT, type);
		
		String listEvents1 = "listevents";
		InputData data1 = p.getInputData(listEvents1);
		ParsingStatus status1 = data1.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status1);
		assertEquals(Command.NULL, data1.getCommand());
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
		
		listTag = "list #";
		data = p.getInputData(listTag);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}
	
	@Test
	public void testListDate() {
		String listDate = "list 20/1";
		InputData data = p.getInputData(listDate);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.DATE, type);
		
		// boundary case of negative date
		listDate = "list -20/1";
		data = p.getInputData(listDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of typo date
		listDate = "list 20/1/";
		data = p.getInputData(listDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of days
		listDate = "list today";
		data = p.getInputData(listDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.SUCCESS, status);
		
		//boundary case of no "day" behind
		listDate = "list tomorrow";
		data = p.getInputData(listDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.SUCCESS, status);
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
		
		listAll = "list";
		data = p.getInputData(listAll);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}
	
	@Test
	public void testListDone() {
		String listDone = "list done";
		InputData data = p.getInputData(listDone);
		ParsingStatus status = data.getStatus();
		ListQuery type = (ListQuery) data.get(Keys.QUERY_TYPE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.LIST, data.getCommand());
		assertEquals(ListQuery.DONE, type);
		
		listDone = "list DoNe";
		data = p.getInputData(listDone);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.SUCCESS, status);
		
		listDone = "list Do";
		data = p.getInputData(listDone);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}
	
	@Test
	public void testAddPlan() {
		String plan1 = "add finish #cs2103 stuff 9/2";
		InputData data = p.getInputData(plan1);
		ParsingStatus status = data.getStatus();
		Object title = data.get(Keys.TITLE);
		Object hashtags = data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.ADD_PLAN, data.getCommand());
		assertEquals("finish cs2103 stuff 9/2", title);
		assertEquals("[cs2103]", hashtags.toString());
		
		// boundary case of unfilled fields
		plan1 = "add ";
		data = p.getInputData(plan1);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of blank title
		plan1 = "add     ";
		data = p.getInputData(plan1);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}
	
	@Test
	public void testAddTask() {
		// boundary case of hashtag keyword
		String task1 = "add Meet jane after #school #by today 7:55pm";
		InputData data = p.getInputData(task1);
		ParsingStatus status = data.getStatus();
		Object title = data.get(Keys.TITLE);
		Object hashtags = data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.ADD_TASK, data.getCommand());
		assertEquals("Meet jane after school", title);
		assertEquals("[school, by]", hashtags.toString());
		
		Calendar due = (Calendar) data.get(Keys.DUE);
		int day = due.get(Calendar.DAY_OF_MONTH);
		int month = due.get(Calendar.MONTH);
		int year = due.get(Calendar.YEAR);
		int hour = due.get(Calendar.HOUR);
		int mins = due.get(Calendar.MINUTE);
		
		Calendar todayCal = Calendar.getInstance();
		assertEquals(todayCal.get(Calendar.DAY_OF_MONTH), day);
		assertEquals(todayCal.get(Calendar.MONTH), month);
		assertEquals(todayCal.get(Calendar.YEAR), year);
		assertEquals(7, hour);
		assertEquals(55, mins);
		
		// test for no time input
		task1 = "add Meet jane after #school #by tomorrow";
		data = p.getInputData(task1);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// test for no date input
		task1 = "add Meet jane after #school #by 7am";
		data = p.getInputData(task1);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// test with 2 days in input
		task1 = "add buy sunday times by sunday 12am";
		data = p.getInputData(task1);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.SUCCESS, status);
	}
	
	@Test
	public void testAddEvent() {
		// boundary case of different date and time formats
		String event = "add meet #boss from 13/2/2013 10:10am to 22/1 9pm #vacation";
		InputData data = p.getInputData(event);
		ParsingStatus status = data.getStatus();
		Object title = data.get(Keys.TITLE);
		Object hashtags = data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.ADD_EVENT, data.getCommand());
		assertEquals("meet boss", title);
		assertEquals("[boss, vacation]", hashtags.toString());
		
		Calendar startEvent = (Calendar) data.get(Keys.START);
		Calendar endEvent = (Calendar) data.get(Keys.END);
		
		int startDay = startEvent.get(Calendar.DAY_OF_MONTH);
		int startMonth = startEvent.get(Calendar.MONTH);
		int startYear = startEvent.get(Calendar.YEAR);
		int startHour = startEvent.get(Calendar.HOUR);
		int startMins = startEvent.get(Calendar.MINUTE);
		
		assertEquals(13, startDay);
		assertEquals(1, startMonth);
		assertEquals(2013, startYear);
		assertEquals(10, startHour);
		assertEquals(10, startMins);
		
		int endDay = endEvent.get(Calendar.DAY_OF_MONTH);
		int endMonth = endEvent.get(Calendar.MONTH);
		int endYear = endEvent.get(Calendar.YEAR);
		int endHour = endEvent.get(Calendar.HOUR);
		int endMins = endEvent.get(Calendar.MINUTE);
		
		assertEquals(22, endDay);
		assertEquals(0, endMonth);
		assertEquals(2014, endYear);
		assertEquals(9, endHour);
		assertEquals(0, endMins);
		
		// boundary case of empty title
		event = "add from 12/12 10am to 12pm";
		data = p.getInputData(event);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of 1 empty field
		event = "add from 10am to 12pm";
		data = p.getInputData(event);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		event = "add from 12/12 to 12pm";
		data = p.getInputData(event);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		event = "add from 12/12 10am to ";
		data = p.getInputData(event);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// case of days
		event = "add meet #boss from tomorrow 10am to 9:12am";
		data = p.getInputData(event);
		status = data.getStatus();
				
		assertEquals(ParsingStatus.SUCCESS, status);
		
		startEvent = (Calendar) data.get(Keys.START);
		endEvent = (Calendar) data.get(Keys.END);
		
		startDay = startEvent.get(Calendar.DAY_OF_MONTH);
		startMonth = startEvent.get(Calendar.MONTH);
		startYear = startEvent.get(Calendar.YEAR);
		startHour = startEvent.get(Calendar.HOUR);
		startMins = startEvent.get(Calendar.MINUTE);
		
		assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, startDay);
		assertEquals(cal.get(Calendar.MONTH), startMonth);
		assertEquals(cal.get(Calendar.YEAR), startYear);
		assertEquals(10, startHour);
		assertEquals(0, startMins);
		
		endDay = endEvent.get(Calendar.DAY_OF_MONTH);
		endMonth = endEvent.get(Calendar.MONTH);
		endYear = endEvent.get(Calendar.YEAR);
		endHour = endEvent.get(Calendar.HOUR);
		endMins = endEvent.get(Calendar.MINUTE);
		
		assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, endDay);
		assertEquals(cal.get(Calendar.MONTH), endMonth);
		assertEquals(cal.get(Calendar.YEAR), endYear);
		assertEquals(9, endHour);
		assertEquals(12, endMins);
		
		// case of 1 day event
		event = "add meet #boss from 12/12/12 10am to 9:12am";
		data = p.getInputData(event);
		status = data.getStatus();
		title = data.get(Keys.TITLE);
		hashtags = data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.ADD_EVENT, data.getCommand());
		assertEquals("meet boss", title);
		assertEquals("[boss]", hashtags.toString());
		
		startEvent = (Calendar) data.get(Keys.START);
		endEvent = (Calendar) data.get(Keys.END);
		
		startDay = startEvent.get(Calendar.DAY_OF_MONTH);
		startMonth = startEvent.get(Calendar.MONTH);
		startYear = startEvent.get(Calendar.YEAR);
		startHour = startEvent.get(Calendar.HOUR);
		startMins = startEvent.get(Calendar.MINUTE);
		
		assertEquals(12, startDay);
		assertEquals(11, startMonth);
		assertEquals(2012, startYear);
		assertEquals(10, startHour);
		assertEquals(0, startMins);
		
		endDay = endEvent.get(Calendar.DAY_OF_MONTH);
		endMonth = endEvent.get(Calendar.MONTH);
		endYear = endEvent.get(Calendar.YEAR);
		endHour = endEvent.get(Calendar.HOUR);
		endMins = endEvent.get(Calendar.MINUTE);
		
		assertEquals(12, endDay);
		assertEquals(11, endMonth);
		assertEquals(2012, endYear);
		assertEquals(9, endHour);
		assertEquals(12, endMins);
	}

	@Test
	public void testDelete() {
		
		String delete1 = "delete 12359";
		InputData data1 = p.getInputData(delete1);
		ParsingStatus status1 = data1.getStatus();
		Command type1 = data1.getCommand(); 
		int uid1 = (int) data1.get(Keys.UID);
		
		assertEquals(ParsingStatus.SUCCESS, status1);
		assertEquals(Command.DELETE, type1);
		assertEquals(12359, uid1);
		
		String delete3 = "delete -0000";
		InputData data3 = p.getInputData(delete3);
		ParsingStatus status3 = data3.getStatus();
		Command type3 = data3.getCommand(); 
		int uid3 = (int) data3.get(Keys.UID);
		
		assertEquals(ParsingStatus.SUCCESS, status3);
		assertEquals(Command.DELETE, type3);
		assertEquals(0, uid3);
		
		// Testing boundary for string
		String delete4 = "delete delet";
		InputData data4 = p.getInputData(delete4);
		ParsingStatus status4 = data4.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status4);
	}

	@Test
	public void testMarkDone() {
		// testing within acceptance region
		String done = "done 12359";
		InputData data = p.getInputData(done);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand(); 
		int uid = (int) data.get(Keys.UID);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.MARK_DONE, type);
		assertEquals(12359, uid);
		
		// Testing outside boundary case
		done = "done -0";
		data = p.getInputData(done);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.SUCCESS, status);
	
		// Testing boundary for string
		done = "done insta";
		data = p.getInputData(done);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		done = "done ";
		data = p.getInputData(done);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		done = "done";
		data = p.getInputData(done);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
	}

	@Test
	public void testToggleDone() {
		// testing within acceptance region
		String toggleDone = "toggle done 12359 ";
		InputData data = p.getInputData(toggleDone);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand(); 
		int uid = (int) data.get(Keys.UID);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.TOGGLE_DONE, type);
		assertEquals(12359, uid);
		
		// testing typo command
		toggleDone = "toggledone 12359 ";
		data = p.getInputData(toggleDone);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		toggleDone = "toggle done -00000";
		data = p.getInputData(toggleDone);
		status = data.getStatus();
		uid = (int) data.get(Keys.UID);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(00000, uid);
		
		toggleDone = "toggle done ";
		data = p.getInputData(toggleDone);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// Testing boundary for string
		toggleDone = "toggle done ios";
		data = p.getInputData(toggleDone);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}

	@Test
	public void testEditDueDate() {
		// testing date boundary of dd/mm
		String editDueDate = "edit 0 due date 1/12";
		InputData data = p.getInputData(editDueDate);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand();
		EditField field = (EditField) data.get(Keys.FIELD);
		Calendar date = (Calendar) data.get(Keys.VALUE);
		
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH);
		int year = date.get(Calendar.YEAR);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.DUE_DATE, field);
		
		assertEquals(1, day);
		assertEquals(11, month);
		assertEquals(2014, year);
		
		// testing date boundary of dd/mm/yy
		editDueDate = "edit 0430 due date 9/9/11";
		data = p.getInputData(editDueDate);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		date = (Calendar) data.get(Keys.VALUE);
		
		day = date.get(Calendar.DAY_OF_MONTH);
		month = date.get(Calendar.MONTH);
		year = date.get(Calendar.YEAR);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.DUE_DATE, field);
		
		assertEquals(9, day);
		assertEquals(8, month);
		assertEquals(2011, year);
		
		// testing date boundary of dd/mm/yyyy
		editDueDate = "edit 20430 due date 28/2/1111";
		data = p.getInputData(editDueDate);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		date = (Calendar) data.get(Keys.VALUE);
		
		day = date.get(Calendar.DAY_OF_MONTH);
		month = date.get(Calendar.MONTH);
		year = date.get(Calendar.YEAR);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.DUE_DATE, field);
		
		assertEquals(28, day);
		assertEquals(1, month);
		assertEquals(1111, year);
		
		// testing typo command
		editDueDate = "edit 0 due";
		data = p.getInputData(editDueDate);
		status = data.getStatus();
		assertEquals(ParsingStatus.FAIL, status);
		
	}

	@Test
	public void testEditDueTime() {
		// testing within boundary of time hh:mma
		String editDueTime = "edit 30953 due time 12:10am";
		InputData data = p.getInputData(editDueTime);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand();
		EditField field = (EditField) data.get(Keys.FIELD);
		Calendar date = (Calendar) data.get(Keys.VALUE);
		
		int hour = date.get(Calendar.HOUR);
		int mins = date.get(Calendar.MINUTE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.DUE_TIME, field);
	
		assertEquals(0, hour);
		assertEquals(10, mins);
		
		// testing within boundary of time hh:mma
		editDueTime = "edit 30953 due time 12am";
		data = p.getInputData(editDueTime);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		date = (Calendar) data.get(Keys.VALUE);
		
		hour = date.get(Calendar.HOUR);
		mins = date.get(Calendar.MINUTE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.DUE_TIME, field);
	
		assertEquals(0, hour);
		assertEquals(0, mins);
		
		// testing typo command
		editDueTime = "edit 30953 due 12am";
		data = p.getInputData(editDueTime);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// testing negative time
		editDueTime = "edit 30953 duetime -10am";
		data = p.getInputData(editDueTime);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}

	@Test
	public void testEditEndDate() {
		// testing date boundary of dd/mm
		String editEndDate = "edit 23859 end date 1/1";
		InputData data = p.getInputData(editEndDate);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand();
		EditField field = (EditField) data.get(Keys.FIELD);
		Calendar date = (Calendar) data.get(Keys.VALUE);
		
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH);
		int year = date.get(Calendar.YEAR);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.END_DATE, field);
		
		assertEquals(1, day);
		assertEquals(0, month);
		assertEquals(2014, year);
		
		// testing date boundary of dd/mm/yy
		editEndDate = "edit 23859 end date 1/1/34";
		data = p.getInputData(editEndDate);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		date = (Calendar) data.get(Keys.VALUE);
		
		day = date.get(Calendar.DAY_OF_MONTH);
		month = date.get(Calendar.MONTH);
		year = date.get(Calendar.YEAR);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.END_DATE, field);
		
		assertEquals(1, day);
		assertEquals(0, month);
		assertEquals(2034, year);
		
		// testing date boundary of dd/mm/yy
		editEndDate = "edit 23859 end date 1/1/3423";
		data = p.getInputData(editEndDate);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		date = (Calendar) data.get(Keys.VALUE);
		
		day = date.get(Calendar.DAY_OF_MONTH);
		month = date.get(Calendar.MONTH);
		year = date.get(Calendar.YEAR);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.END_DATE, field);
		
		assertEquals(1, day);
		assertEquals(0, month);
		assertEquals(3423, year);
		
		// test typo
		editEndDate = "edit 23859 enddate 1/1/3423";
		data = p.getInputData(editEndDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}

	@Test
	public void testEditEndTime() {
		// testing date boundary of time for hh:mma
		String editEndTime = "edit 23535 end time 10:12pm";
		InputData data = p.getInputData(editEndTime);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand();
		EditField field = (EditField) data.get(Keys.FIELD);
		Calendar date = (Calendar) data.get(Keys.VALUE);
		
		int hour = date.get(Calendar.HOUR);
		int mins = date.get(Calendar.MINUTE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.END_TIME, field);
	
		assertEquals(10, hour);
		assertEquals(12, mins);
		
		// testing date boundary of time for hha
		editEndTime = "edit 23535 end time 10pm";
		data = p.getInputData(editEndTime);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		date = (Calendar) data.get(Keys.VALUE);
		
		hour = date.get(Calendar.HOUR);
		mins = date.get(Calendar.MINUTE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.END_TIME, field);
	
		assertEquals(10, hour);
		assertEquals(0, mins);
		
		// test typo
		editEndTime = "edit 23535 endtime 10pm";
		data = p.getInputData(editEndTime);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	
	}

	@Test
	public void testEditStartTime() {
		String editStartTime = "edit 23834 start time 9:00am";
		InputData data = p.getInputData(editStartTime);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand();
		EditField field = (EditField) data.get(Keys.FIELD);
		Calendar date = (Calendar) data.get(Keys.VALUE);
		
		int hour = date.get(Calendar.HOUR);
		int mins = date.get(Calendar.MINUTE);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.START_TIME, field);
	
		assertEquals(9, hour);
		assertEquals(0, mins);
		
		// boundary case of negative time
		editStartTime = "edit 23834 start time -2pm";
		data = p.getInputData(editStartTime);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of wrongly filled edit
		editStartTime = "edit 23834 start time 2opm";
		data = p.getInputData(editStartTime);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of unfilled edit
		editStartTime = "edit 23834 start time ";
		data = p.getInputData(editStartTime);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}

	@Test
	public void testEditStartDate() {
		// test case within boundary
		String editStartDate = "edit 21345 start date 9/12/13";
		InputData data = p.getInputData(editStartDate);
		ParsingStatus status = data.getStatus();
		Command type = data.getCommand();
		EditField field = (EditField) data.get(Keys.FIELD);
		Calendar date = (Calendar) data.get(Keys.VALUE);
		
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH);
		int year = date.get(Calendar.YEAR);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.START_DATE, field);
		
		assertEquals(9, day);
		assertEquals(11, month);
		assertEquals(2013, year);
		
		// boundary case of wrongly filled edit
		editStartDate = "edit 21345 start date 9/ ";
		data = p.getInputData(editStartDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of unfilled edit
		editStartDate = "edit 21345 start date ";
		data = p.getInputData(editStartDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
		
		// boundary case of negative date
		editStartDate = "edit 21345 start date -19/2";
		data = p.getInputData(editStartDate);
		status = data.getStatus();
		
		assertEquals(ParsingStatus.FAIL, status);
	}

	@Test
	public void testEditTitle() {
		//within test boundary
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
		
		//boundary case of unfilled hashtag
		editTitle = "edit 72384 title hello #";
		data = p.getInputData(editTitle);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		title = data.get(Keys.VALUE);
		tags = (ArrayList<String>) data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.TITLE, field);
		assertEquals("hello", title);
		assertEquals("[]", tags.toString());
		
		editTitle = "edit 72384 titles hello #everybody";
		data = p.getInputData(editTitle);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		title = data.get(Keys.VALUE);
		tags = (ArrayList<String>) data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.SUCCESS, status);
		assertEquals(Command.EDIT, type);
		assertEquals(EditField.TITLE, field);
		assertEquals("hello everybody", title);
		assertEquals("[everybody]", tags.toString());
		
		//boundary case of unfilled edit
		editTitle = "edit 72384 title ";
		data = p.getInputData(editTitle);
		status = data.getStatus();
		type = data.getCommand();
		field = (EditField) data.get(Keys.FIELD);
		title = data.get(Keys.VALUE);
		tags = (ArrayList<String>) data.get(Keys.HASHTAGS);
		
		assertEquals(ParsingStatus.FAIL, status);
		
	}
	
}
