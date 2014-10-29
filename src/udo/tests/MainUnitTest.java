package udo.tests;

//@Author A0114088H

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
		OutputData output = udo.testParseAndExecute("add #new Event from 28/10/14 3pm to 29/10/14 4pm");
		ItemData item = (ItemData) output.get(Keys.ITEM);
		Calendar time = Calendar.getInstance();
		time.set(2014, Calendar.OCTOBER, 28, 15, 0);
		ArrayList<String> hashtags = new ArrayList<String>();
		hashtags.add("new");
		// The following will check the attributes of the added event
		assertEquals("item name should be new Event",
						"new Event",
						item.get(Keys.TITLE));
		assertEquals("start date should be 29/10/14 3pm",
						time,
						item.get(Keys.START));
		time.set(2014, Calendar.OCTOBER, 29, 16, 0);
		assertEquals("end date should be 29/10/14 4pm",
						time,
						item.get(Keys.END));
		assertEquals("hashtags will contain one object, 'new'",
						hashtags.size(),
						((ArrayList<String>) item.get(Keys.HASHTAGS)).size());
		assertEquals("the object is 'new'",
						"new",
						((ArrayList<String>) item.get(Keys.HASHTAGS)).get(0));
	}
}
