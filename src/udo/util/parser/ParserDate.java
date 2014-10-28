//@author A0114847B
package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserDate {

	private static Calendar mDate;
	private String mDays[] = {"mon", "tue", "wed", "thu", "fri", "sat", "sun", 
							"today", "tomorrow", ""};
	private String mDateFormat[] = {"dd/MM", "dd/MM/yy", "dd/MM/yyyy", ""};
	
	public ParserDate() {
		
	}
	
	//Calendar cal = ParserDate.getInstance();
	/*
	public static Calendar getInstance() {
		if (mDate == null) {
			mDate = Calendar.getInstance(); 
		} 
		return mDate;
	}
	*/
	
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
	
	// facilitates how all instructions are run
	private void decipherText(String input) {
		int dateFormat = getDateFormat(input);
		if (dateFormat == -1) {
			//day
		} else if (dateFormat != 3) {
			String dateString = getDateString(input, dateFormat);
			mDate = formatDateSubstring(dateString, dateFormat);
		} else {
			mDate = null;
		}
	}

	private Calendar formatDateSubstring(String input, int dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(mDateFormat[dateFormat]);
		Date date;

		try {
			date = format.parse(input);
			cal.setTime(date);
		} catch (ParseException parserException) {
			cal = null;
			return cal;
		}
		return cal;
	}

	private String getDateString(String input, int dateFormat) {
		switch (dateFormat) {
			case 0:
				return extractDateWithNoYears(input);
			case 1:
				return extractDateWithTwoYears(input);
			case 2:
				return extractDateWithFourYears(input);
			default:
				return null;
		}
	}

	private String extractDateWithNoYears(String input) {
		int dayMonthSlashIndex = input.indexOf("/");
		int startIndex = dayMonthSlashIndex - 2;
		int endIndex = dayMonthSlashIndex + 3;
		
		String dateString = input.substring(startIndex, endIndex);
		dateString = dateString.replaceAll("\\s+","");
		return dateString;
	}

	private String extractDateWithTwoYears(String input) {
		int dayMonthSlashIndex = input.indexOf("/");
		int offset = dayMonthSlashIndex + 1; 
		int monthYearSlashIndex = input.indexOf("/", offset);
		int startIndex = dayMonthSlashIndex - 2;
		int endIndex = monthYearSlashIndex + 3;
		
		String dateString = input.substring(startIndex, endIndex);
		dateString = dateString.replaceAll("\\s+","");
		return dateString;
	}

	private String extractDateWithFourYears(String input) {
		int dayMonthSlashIndex = input.indexOf("/");
		int offset = dayMonthSlashIndex + 1; 
		int monthYearSlashIndex = input.indexOf("/", offset);
		int startIndex = dayMonthSlashIndex - 2;
		int endIndex = monthYearSlashIndex + 5;
		
		String dateString = input.substring(startIndex, endIndex);
		dateString = dateString.replaceAll("\\s+","");
		return dateString;
	}

	private int getDateFormat(String input) {
		if (input.contains("/")) {
			int dayMonthSlashIndex = input.indexOf("/");
			int offset = dayMonthSlashIndex + 1; 
			int monthYearSlashIndex = input.indexOf("/", offset); // gets the second "/" from "dd/mm/yy" 

			if (monthYearSlashIndex == -1) { // dd/MM format
				return 0;
			} else if (isValidDate(dayMonthSlashIndex, monthYearSlashIndex) &&
						!hasFourYearDigits(monthYearSlashIndex, input)) {
				return 1;
			} else if (isValidDate(dayMonthSlashIndex, monthYearSlashIndex) &&
					hasFourYearDigits(monthYearSlashIndex, input)) {
				return 2;
			} else {
				return 3;
			}
		} else {
			return -1;
		}
	}
	
	// checks if the first "/" and second "/" are of the same date string
	private boolean isValidDate(int dayMonthSlashIndex, int monthYearSlashIndex) {
		int difference = monthYearSlashIndex - dayMonthSlashIndex;
		if (difference == 2 || difference == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean hasFourYearDigits(int monthYearSlashIndex, String input) {
		int yearStartIndex = monthYearSlashIndex + 1;
		int yearEndIndex = monthYearSlashIndex + 5;
		int count = 0;
		String yearDigit;
		for (int i = yearStartIndex; i < yearEndIndex; i++) {
			yearDigit = input.substring(yearStartIndex, yearStartIndex + 1);
			if (isInteger(yearDigit)) {
				count++;
			}
		}
		
		if (count == 4) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
