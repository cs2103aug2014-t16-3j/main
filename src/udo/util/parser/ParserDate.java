package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserDate {

Calendar mDate;
	
	public ParserDate() {
		mDate = Calendar.getInstance();
	}
	
	/**
	 * Method returns the first date it reads from input.
	 * Detects date by "/" character
	 * @param input string
	 * @return Calendar date object
	 */
	public Calendar getDate() {
		return mDate;
	}
	
	public void decipherText(String input) {
		String dateString = getDateString(input);
		Calendar cal = formatDateSubstring(dateString);
		mDate = cal;
	}
	
	public String getDateString(String input) {
		int dayMonthSlashIndex;
		int monthYearSlashIndex;
		String dateSubstring = input;
		String ERROR_MESSAGE = "DateString does not exist or it was not detected";
		while (dateSubstring.contains("/")) {
			dayMonthSlashIndex = dateSubstring.indexOf("/");
			monthYearSlashIndex = dateSubstring.indexOf("/", dayMonthSlashIndex + 1);
			
			if (isValidDateString(dateSubstring, dayMonthSlashIndex, monthYearSlashIndex)) {
				dateSubstring = dateSubstring.substring(dayMonthSlashIndex - 2, monthYearSlashIndex + 3);
				return dateSubstring.trim();
			}
			dateSubstring = dateSubstring.substring(dayMonthSlashIndex + 1);
		}
		return ERROR_MESSAGE;
	}
	
	public boolean isValidDateString(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		if (isSingleDigitMonth(input, dayMonthSlashIndex, monthYearSlashIndex) ||
			isDoubleDigitMonth(input, dayMonthSlashIndex, monthYearSlashIndex)) {
			try {
				String day = input.substring(dayMonthSlashIndex - 2, dayMonthSlashIndex);
				String month = input.substring(dayMonthSlashIndex + 1, monthYearSlashIndex);
				String year = input.substring(monthYearSlashIndex + 1, monthYearSlashIndex + 3);
				
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
		if (input.indexOf("/") == dayMonthSlashIndex && 
			dayMonthSlashIndex + 2 == monthYearSlashIndex) { // for 1 digit month
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isDoubleDigitMonth(String input, int dayMonthSlashIndex, int monthYearSlashIndex) {
		if (input.indexOf("/") == dayMonthSlashIndex && 
			dayMonthSlashIndex + 3 == monthYearSlashIndex) { // for 2 digit month
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
