//@author A0114847B
package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import udo.util.shared.Command;

public class ParserDate {

	private static Calendar mDate;
	private String mDays[] = {"TODAY", "SUNDAY", "MONDAY", 
							"TUESDAY", "WEDNESDAY", "THURSDAY", 
							"FRIDAY", "SATURDAY", "TOMORROW"};
	private String mDateFormat[] = {"dd/MM", "dd/MM/yy", "dd/MM/yyyy", ""};
	
	public ParserDate() {
		
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
	
	// facilitates how all instructions are run
	private void decipherText(String input) {
		int dateFormat = getDateFormat(input);
		if (dateFormat == -1) {
			int day = getDay(input);
			mDate = formatDaySubstring(day);
		} else if (dateFormat != 3) {
			String dateString = getDateString(input, dateFormat);
			mDate = formatDateSubstring(dateString, dateFormat);
		} else {
			mDate = null;
		}
	}

	private int getDateFormat(String input) {
		if (input.contains("/")) {
			int dayMonthSlashIndex = input.indexOf("/");
			int offset = dayMonthSlashIndex + 1; 
			int monthYearSlashIndex = input.indexOf("/", offset); // gets the second "/" from "dd/mm/yy" 
	
			if (monthYearSlashIndex == -1 ||
				!containsYear(dayMonthSlashIndex, monthYearSlashIndex)) { // dd/MM format
				return 0;
			} else if (containsYear(dayMonthSlashIndex, monthYearSlashIndex) &&
						!hasFourYearDigits(monthYearSlashIndex, input)) {
				return 1;
			} else if (containsYear(dayMonthSlashIndex, monthYearSlashIndex) &&
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
	private boolean containsYear(int dayMonthSlashIndex, int monthYearSlashIndex) {
		int difference = monthYearSlashIndex - dayMonthSlashIndex;
		if (difference == 2 || difference == 3) { // xx/x/xx or xx/xx/xx (one month or 2 months)
			return true;
		} else {
			return false;
		}
	}

	private boolean hasFourYearDigits(int monthYearSlashIndex, String input) {
		String year = input;
		int yearStartIndex = monthYearSlashIndex + 1;
		int yearEndIndex = monthYearSlashIndex + 5;
		if (yearEndIndex > year.length()) {
			yearEndIndex = year.length();
		}
		int count = 0;
		String yearDigit;
		for (int i = yearStartIndex; i < yearEndIndex; i++) {
			yearDigit = year.substring(i, i + 1);
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
		String dateString = input.toUpperCase();
		String dateStringArr[] = dateString.split(" ");
		boolean isOfValidLength;
		String dayFirstDigit;
		String monthLastDigit;
		for (int i = 0; i < dateStringArr.length; i++) {
			isOfValidLength = (dateStringArr[i].length() >= 3 && dateStringArr[i].length() <= 5); // d/m to dd/mm
			dayFirstDigit = dateStringArr[i].substring(0, 1);
			monthLastDigit = dateStringArr[i].substring(dateStringArr[i].length() - 1);
			if (dateStringArr[i].contains("/") &&
				isOfValidLength &&
				isInteger(dayFirstDigit) &&
				isInteger(monthLastDigit)) { 
				return dateStringArr[i];
			}
		}
		return null;
	}

	private String extractDateWithTwoYears(String input) {
		String dateString = input.toUpperCase();
		String dateStringArr[] = dateString.split(" ");
		boolean isOfValidLength;
		String dayFirstDigit;
		String yearLastDigit;
		for (int i = 0; i < dateStringArr.length; i++) {
			isOfValidLength = (dateStringArr[i].length() >= 6 && dateStringArr[i].length() <=8);
			dayFirstDigit = dateStringArr[i].substring(0, 1);
			yearLastDigit = dateStringArr[i].substring(dateStringArr[i].length() - 1);
			if (dateStringArr[i].contains("/") &&
				isOfValidLength &&
				isInteger(dayFirstDigit) &&
				isInteger(yearLastDigit)) { 
				return dateStringArr[i];
			}
		}
		return null;
	}

	private String extractDateWithFourYears(String input) {
		String dateString = input.toUpperCase();
		String dateStringArr[] = dateString.split(" ");
		boolean isOfValidLength;
		String dayFirstDigit;
		String yearLastDigit;
		for (int i = 0; i < dateStringArr.length; i++) {
			isOfValidLength = (dateStringArr[i].length() >= 8 && dateStringArr[i].length() <=10);
			dayFirstDigit = dateStringArr[i].substring(0, 1);
			yearLastDigit = dateStringArr[i].substring(dateStringArr[i].length() - 1);
			if (dateStringArr[i].contains("/") &&
				isOfValidLength &&
				isInteger(dayFirstDigit) &&
				isInteger(yearLastDigit)) { 
				return dateStringArr[i];
			}
		}
		return null;
	}

	private Calendar formatDateSubstring(String input, int dateFormat) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		SimpleDateFormat format = new SimpleDateFormat(mDateFormat[dateFormat]);
		Date date;
		if (input != null) {
			try {
				date = format.parse(input);
				cal.setTime(date);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				
				if (dateFormat == 0) {
					cal.set(Calendar.YEAR, year);
				}
			} catch (ParseException parserException) {
				cal = null;
				return cal;
			}
			return cal;
		} else {
			cal = null;
			return cal;
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

	private int getDay(String input) {
		String dayString = input.toUpperCase();
		for (int i = 0; i < mDays.length; i++) {
			if (dayString.contains(mDays[i])) {
				return i;
			}
		}
		return -1;
	}

	private Calendar formatDaySubstring(int day) {
		Calendar cal = Calendar.getInstance();
		int difference = getDayNextWeek(day, cal);
		if (day == 0) {
			return cal;
		} else if (day == 8) {
			cal.add(Calendar.DATE, 1);
			return cal;
		} else if (day > 0 && day < 8) {
			cal.add(Calendar.DAY_OF_MONTH, difference);
			return cal;
		} else {
			cal = null;
			return cal;
		}
	}

	private int getDayNextWeek(int day, Calendar cal) {
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int difference = day - dayOfWeek;
		if (difference < 0) {
			difference = 7 + difference;
		} else if (difference == 0) {
			difference = 7;
		}
		return difference;
	}

}
