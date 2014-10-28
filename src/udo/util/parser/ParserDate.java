package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserDate {

	private static Calendar mDate;
	private String mDays[] = {"mon", "tue", "wed", "thu", "fri", "sat", "sun", 
							"today", "tomorrow"};
	
	public ParserDate() {
		
	}
	
	//Calendar cal = ParserDate.getInstance();
	public static Calendar getInstance() {
		if (mDate == null) {
			mDate = Calendar.getInstance(); 
		} 
		return mDate;
	}
	
	/**
	 * Method returns the first date it reads from input.
	 * Detects date by "/" character
	 * @param input string
	 * @return Calendar date object
	 */
	public Calendar getDate(String input) {
		decipherText(input);
		return mDate;
	}
	
	private void decipherText(String input) {
		if (input.contains("/")) {
			String dateString = getDateString(input);
		} else {
			// go by day of week
		}
	}

	private String getDateString(String input) {
		String dateSubstring = input;
		int dayMonthSlashIndex = dateSubstring.indexOf("/");
		int offset = dayMonthSlashIndex + 1; 
		int monthYearSlashIndex = dateSubstring.indexOf("/", offset); // gets the second "/" from "dd/mm/yy"
		int monthDigits = monthYearSlashIndex - dayMonthSlashIndex - 1;
		boolean dateStringContainsYear = (monthYearSlashIndex != -1 || monthDigits == 1 || monthDigits == 2);
		int dayIndex = dayMonthSlashIndex - 2;
		int monthIndex;
		int yearIndex;
		
		if (dateStringContainsYear) {
			yearIndex = monthYearSlashIndex + 5;
			dateSubstring = dateSubstring.substring(dayIndex, yearIndex);
			dateSubstring = removePossibleAlphabets(dateSubstring);
			
		} else {
			monthIndex = dayMonthSlashIndex + 3;
			dateSubstring = dateSubstring.substring(dayIndex, monthIndex);
			dateSubstring = removePossibleAlphabets(dateSubstring);
		}
		return dateSubstring;
	}

	private String removePossibleAlphabets(String dateSubstring) {
		String dateString = dateSubstring.replaceAll("\\s+","");
		String firstChar = dateString.substring(0, 1);
		String lastChar = dateString.substring(dateString.length() - 1);
		while (!dateString.isEmpty()) {
			if (!isInteger(firstChar)) {
				dateString = dateString.substring(1);
				firstChar = dateString.substring(0, 1);
			} else if (!isInteger(lastChar)) {
				dateString = dateString.substring(0, dateString.length() - 1);
				lastChar = dateString.substring(dateString.length() - 1);
			} else {
				break;
			}
		}
		return dateString;
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
