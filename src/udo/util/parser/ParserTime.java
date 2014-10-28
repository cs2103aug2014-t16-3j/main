//@author A0114847B
package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserTime {

	private Calendar mTime;

	public ParserTime() {
		mTime = Calendar.getInstance();
	}
	
	/**
	 * Method returns the first time it reads from input.
	 * Detects time by ":" character
	 * @param input
	 * @return Calendar time object
	 */
	public Calendar getTime(String input) {
		decipherText(input);
		return mTime;
	}
	
	/**
	 * Method checks whether input contains a time string of format "hh:mma"
	 * @param input
	 * @return true if time string exist, false otherwise
	 */
	public boolean containsTime(String input) {
		if (getTimeString(input) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void decipherText(String input) {
		String timeString = getTimeString(input);
		if (timeString != null) {
			Calendar cal = formatTimeSubstring(timeString);
			mTime = cal;
		}
	}

	// checks for dateString with the format "hh:mma"
	// returns the date string with all white spaces trimed if a time string exist
	// returns the first date string it reads if a time string exist		
	// otherwise return null
	public String getTimeString(String input) {
		int timeSessionIndex;
		int colonIndex;
		int timeSubstringStartingIndex;
		int timeSubstringEndingIndex;
		int getTimeStringOffset;
		String timeSubstring = input.toUpperCase();
		
		while (timeSubstring.contains(":")) {
			colonIndex = timeSubstring.indexOf(":");
			timeSessionIndex = timeSubstring.indexOf("M");
			
			if (isVerifiedTimeString(timeSubstring, colonIndex, timeSessionIndex)) {
				timeSubstringStartingIndex = colonIndex - 2;
				timeSubstringEndingIndex = timeSessionIndex + 1;
				timeSubstring = timeSubstring.substring(timeSubstringStartingIndex, timeSubstringEndingIndex);
				return timeSubstring.trim();
			}
			getTimeStringOffset = timeSessionIndex + 1;
			timeSubstring = timeSubstring.substring(getTimeStringOffset);
		}
		return null;
	}

	public boolean isVerifiedTimeString(String timeSubstring, int colonIndex, int timeSessionIndex) {
		int twoDigitMins = 2;
		int timeSession = 2;
		if (colonIndex + twoDigitMins + timeSession == timeSessionIndex &&
			containsTimeSession(timeSubstring, colonIndex, timeSessionIndex)) {
			try {
				//check for integers
				int hourStringStartingIndex = colonIndex - 2;
				int minStringStratingIndex = colonIndex + 1;
				int minStringEndingIndex = colonIndex + 3;
				
				String hourString = timeSubstring.substring(hourStringStartingIndex, colonIndex);
				String minString = timeSubstring.substring(minStringStratingIndex, minStringEndingIndex);
				
				int hours = Integer.parseInt(hourString.trim());
				int mins = Integer.parseInt(minString);	
				
			} catch (IndexOutOfBoundsException indexOutOfBoundsException) {
				return false;
			} catch (NumberFormatException numberFormatException) {
				return false;
			}
			return true;
		}
		return false;
	}

	// checks if it contains "AM" and "PM" strings
	public boolean containsTimeSession(String timeSubstring, int colonIndex, int timeSessionIndex) {
		String sessionString = timeSubstring.substring(timeSessionIndex - 1, timeSessionIndex);
		if (sessionString.equalsIgnoreCase("A") ||
			sessionString.equalsIgnoreCase("P")) {
			return true;
		} else {
			return false;
		}
	}

	public Calendar formatTimeSubstring(String input) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("hh:mma");
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

}
