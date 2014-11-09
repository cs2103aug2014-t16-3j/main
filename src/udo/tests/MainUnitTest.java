//@author a0114088H
package udo.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import udo.main.uDo;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.shared.Constants.Keys;

public class MainUnitTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testAddEvent() {
		uDo udo = new uDo();
		// checking hours and minutes from the start and end dates
		OutputData output = udo.testParseAndExecute("add #new Event from 28/10/14 3:00pm to 29/10/14 4:23pm");
		ItemData item = (ItemData) output.get(Keys.ITEM);
		Calendar time = Calendar.getInstance();
		time.set(2014, Calendar.OCTOBER, 28, 15, 0);
		ArrayList<String> hashtags = new ArrayList<String>();
		hashtags.add("new");
		// The following will check the attributes of the added event
		assertEquals("item name should be new Event",
						"new Event",
						item.get(Keys.TITLE));
		
		assertEquals("start hour should be 3", // do the same for minutes, day, month.. so on
						time.get(Calendar.HOUR),
						((Calendar)item.get(Keys.START)).get(Calendar.HOUR));
		
		time.set(2014, Calendar.OCTOBER, 29, 16, 23);
		assertEquals("end hour 4",
						time.get(Calendar.HOUR),
						((Calendar)item.get(Keys.END)).get(Calendar.HOUR));
		
		assertEquals("hashtags will contain one object, 'new'",
						hashtags.size(),
						((ArrayList<String>) item.get(Keys.HASHTAGS)).size());
		
		assertEquals("the object is 'new'",
						"new",
						((ArrayList<String>) item.get(Keys.HASHTAGS)).get(0));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddTask() {
		uDo udo = new uDo();
		// checking minutes from due date as well
		OutputData output = udo.testParseAndExecute("add #new Task by 30/10/14 11:15am");
		ItemData item = (ItemData) output.get(Keys.ITEM);
		Calendar time = Calendar.getInstance();
		time.set(2014, Calendar.OCTOBER, 30, 11, 15);
		ArrayList<String> hashtags = new ArrayList<String>();
		hashtags.add("new");
		// The following will check the attributes of the added plan
		assertEquals("item name should be new task",
						"new Task",
						item.get(Keys.TITLE));
		assertEquals("due hour 4",
				time.get(Calendar.HOUR),
				((Calendar)item.get(Keys.DUE)).get(Calendar.HOUR));
		assertEquals("hashtags will contain one object, 'new'",
						hashtags.size(),
						((ArrayList<String>) item.get(Keys.HASHTAGS)).size());
		assertEquals("the object is 'new'",
						"new",
						((ArrayList<String>) item.get(Keys.HASHTAGS)).get(0));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddPlan() {
		uDo udo = new uDo();
		OutputData output = udo.testParseAndExecute("add #new Plan");
		ItemData item = (ItemData) output.get(Keys.ITEM);
		ArrayList<String> hashtags = new ArrayList<String>();
		hashtags.add("new");
		// The following will check the attributes of the added plan
		assertEquals("item name should be new Event",
						"new Plan",
						item.get(Keys.TITLE));
		assertEquals("hashtags will contain one object, 'new'",
						hashtags.size(),
						((ArrayList<String>) item.get(Keys.HASHTAGS)).size());
		assertEquals("the object is 'new'",
						"new",
						((ArrayList<String>) item.get(Keys.HASHTAGS)).get(0));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testListEvent(){
		uDo udo = new uDo();
		OutputData output = udo.testParseAndExecute("list event");
		ArrayList<ItemData> items = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		// check the size of the resulting arraylist of itemdata
		assertEquals("the number of itemData should be zero (no data in database)",
						0,
						items.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testListAll(){
		uDo udo = new uDo();
		OutputData output = udo.testParseAndExecute("list all");
		ArrayList<ItemData> items = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		// check the size of the resulting arraylist of itemdata
		assertEquals("the number of itemData should be zero (3 in database)",
						3,
						items.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testListPlan(){
		uDo udo = new uDo();
		OutputData output = udo.testParseAndExecute("list plan");
		ArrayList<ItemData> items = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		// check the size of the resulting arraylist of itemdata
		assertEquals("the number of itemData should be zero (3 in database)",
						3,
						items.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testListTask(){
		uDo udo = new uDo();
		OutputData output = udo.testParseAndExecute("list task");
		ArrayList<ItemData> items = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		// check the size of the resulting arraylist of itemdata
		assertEquals("the number of itemData should be zero (no data in database)",
						0,
						items.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testListHashtag(){
		uDo udo = new uDo();
		OutputData output = udo.testParseAndExecute("list #new");
		ArrayList<ItemData> items = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		// check the size of the resulting arraylist of itemdata
		assertEquals("the number of itemData should be zero (no data in database)",
						0,
						items.size());
		OutputData added = udo.testParseAndExecute("add #new stuff"); //proved to be working in testAddPlan
		output = udo.testParseAndExecute("list #new");
		// check the size again after adding new plan with hashtag
		items = (ArrayList<ItemData>) output.get(Keys.ITEMS);
		assertEquals("the number of itemData should be one (the newly added)",
				1,
				items.size());
	}
}
