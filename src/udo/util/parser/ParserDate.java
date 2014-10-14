package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserDate {

	private Calendar mDate;
	
	public ParserDate() {
		mDate = Calendar.getInstance();
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
	
	/**
	 * Method checks whether input contains a date string of format "dd/mm/yy"
	 * @param input
	 * @return true if date string exist, false otherwise
	 */
	public boolean containsDate(String input) {
		String dateString = getDateString(input);
		if (dateString != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void decipherText(String input) {
		String dateString = getDateString(input);
		if (dateString != null) {
			Calendar cal = formatDateSubstring(dateString);
			mDate = cal;
		}
	}

	// checks for dateString with the format "dd/mm/yy"
	// returns the date string with all white spaces trimed if a date string exist
	// returns the first date string it reads if a date string exist
	// otherwise return null
	public String getDateString(String input) {
		int dayMonthSlashIndex;
		int monthYearSlashIndex;
		int dateStringIndexOffset;
		int dateStringStartingindex;
		int dateStringEndingIndex;
		String dateSubstring = input;
		
		while (dateSubstring.contains("/")) {
			dayMonthSlashIndex = dateSubstring.indexOf("/");
			dateStringIndexOffset = dayMonthSlashIndex + 1; // gets the second "/" from "dd/mm/yy"
			monthYearSlashIndex = dateSubstring.indexOf("/", dateStringIndexOffset);
			
			if (isValidDateString(dateSubstring, dayMonthSlashIndex, monthYearSlashIndex)) {
				dateStringStartingindex = dayMonthSlashIndex - 2;
				dateStringEndingIndex = monthYearSlashIndex + 3;
				dateSubstring = dateSubstring.substring(dateStringStartingindex, dateStringEndingIndex);
				return dateSubstring.trim();
			}
			dateSubstring = dateSubstring.substring(dateStringIndexOffset);
		}
		return null;
	}
	
	public boolean isValidDateString(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		if (isSingleDigitMonth(input, dayMonthSlashIndex, monthYearSlashIndex) ||
			isDoubleDigitMonth(input, dayMonthSlashIndex, monthYearSlashIndex)) {
			
			try {
				int dayStartingIndex = dayMonthSlashIndex - 2;
				int monthStartingIndex = dayMonthSlashIndex + 1;
				int yearStartingIndex = monthYearSlashIndex + 1;
				int yearEndingIndex = monthYearSlashIndex + 3;
				
				String day = input.substring(dayStartingIndex, dayMonthSlashIndex);
				String month = input.substring(monthStartingIndex, monthYearSlashIndex);
				String year = input.substring(yearStartingIndex, yearEndingIndex);
				
				int dayInt = Integer.parseInt(day.trim());
				int monthInt = Integer.parseInt(month);
				int yearInt = Integer.parseInt(year);
			
			} catch (IndexOutOfBoundsException indexOutOfBoundsException) {
				return false;
			} catch (NumberFormatException numberFormatException) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean isSingleDigitMonth(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		int oneDigitMonth = 2;
		if (input.indexOf("/") == dayMonthSlashIndex && 
			dayMonthSlashIndex + oneDigitMonth == monthYearSlashIndex) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isDoubleDigitMonth(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		int twoDigitMonth = 3;
		if (input.indexOf("/") == dayMonthSlashIndex && 
			dayMonthSlashIndex + twoDigitMonth == monthYearSlashIndex) {
			return true;
		} else {
			return false;
		}
	}
	
	public Calendar formatDateSubstring(String input) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date date;

		try {
			date = format.parse(input);
			cal.setTime(date);
		} catch (ParseException parserException) {
			// inputData status fail
			return cal;
		}
		return cal;
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
