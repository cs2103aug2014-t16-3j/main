//@author A0114847B
package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserTime {
	
	/**
	 * This class takes returns a calendar object set to the first time it reads 
	 * from input. It takes in time of format hh:mma and hha
	 */

	private Calendar mTime;
	private String mTimeFormat[] = {"hh:mma", "hha"};

	public ParserTime() {
		
	}
	
	/**
	 * Method returns the first time it reads from input.
	 * Time must not have space in-between
	 * @param input
	 * @return Calendar time object
	 */
	public Calendar getTime(String input) {
		decipherText(input);
		return mTime;
	}
	
	private void decipherText(String input) {
		int timeFormat = getTimeFormat(input);
		if (timeFormat == 0 || timeFormat == 1) {
			String timeString = getTimeString(input, timeFormat);
			mTime = formatTimeSubstring(timeString, timeFormat);
		} else {
			mTime = null;
		}
	}

	private String getTimeString(String input, int timeFormat) {
		switch (timeFormat) {
			case 0:
				return extractTimeWithMinutes(input);
			case 1:
				return extractTimeWithoutMinutes(input);
			default:
				return null;
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

	private String extractTimeWithMinutes(String input) {
		String timeString = input.toUpperCase();
		String time[] = timeString.split(" ");
		for (int i = 0; i < time.length; i++) {
			if (isValidTime(time[i])) {
				return time[i];
			}
		}
		return null;
	}

	private String extractTimeWithoutMinutes(String input) {
		String time[] = input.split(" ");
		for (int i = 0; i < time.length; i++) {
			if (isValidTime(time[i])){
				return time[i];
			}
		}
		return null;
	}

	private boolean isValidTime(String time) {
		time = time.toUpperCase();
		if (time.length() == 3 || time.length() == 4) { // e.g. 3pm, 12am
			String amPmString = time.substring(time.length() - 1);
			String hour = time.substring(0, 1);
			if (isInteger(hour) && amPmString.equals("M")) {
				return true;
			} else {
				return false;
			}
		} else if (time.length() == 6 || time.length() == 7) { // e.g. 3:45am, 12:45pm
			String amPmString = time.substring(time.length() - 1);
			String hour = time.substring(0, 1);
			if (isInteger(hour) && amPmString.equals("M")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private Calendar formatTimeSubstring(String timeString, int timeFormat) {
		Calendar cal = null;
		SimpleDateFormat format = new SimpleDateFormat(mTimeFormat[timeFormat]);
		Date date;
		if (timeString != null) {
			try {
				date = format.parse(timeString);
				cal = Calendar.getInstance();
				cal.setTime(date);
			} catch (ParseException parserException) {
				cal = null;
				return cal;
			}
		}
		return cal;
	}

	private int getTimeFormat(String input) {
		String parts[] = input.split(" ");
		for (int i = 0; i < parts.length; i++) {
			if (isValidTime(parts[i])) {
				if (parts[i].length() == 3 || 	// e.g. 3pm 
					parts[i].length() == 4) {	// e.g. 12pm
					return 1;
				} else {
					return 0;
				}
			}
		}
		return -1;
	}
}
