//@author A0114847B
package udo.util.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserTime {

	private Calendar mTime;
	private String mTimeFormat[] = {"hh:mma", "hha"};

	public ParserTime() {
		
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
		int colonIndex = input.indexOf(":");
		int startIndex = colonIndex - 2;
		int endIndex = colonIndex + 5;
		if (endIndex < input.length() && startIndex > -1) {
			String timeString = input.substring(startIndex, endIndex);
			String firstDigit = timeString.substring(0, 1);
			String amPmMarker = timeString.substring(timeString.length() - 1);
			amPmMarker = amPmMarker.toUpperCase();
			
			if (!isInteger(firstDigit)) {
				timeString = timeString.substring(1); //remove non-digit hour in timeString
			}
			
			if (!amPmMarker.equals("M")) {
				return null;
			} else {
				return timeString;
			}
		}
		return null;
	}

	private String extractTimeWithoutMinutes(String input) {
		String parts[] = input.split(" ");
		//
		return null;
	}

	private Calendar formatTimeSubstring(String timeString, int timeFormat) {
		// TODO Auto-generated method stub
		return null;
	}

	private int getTimeFormat(String input) {
		String timeString = input.toUpperCase();
		if (timeString.contains(":") && isValidDate(input)) {
			return 0;
		} else if ((timeString.contains("AM") || timeString.contains("PM")) 
					&& isValidDate(input)) {
			return 1;
		} else {
			return -1;
		}
	}

	// checks if ":" and "AM" or "PM" is from the same time string
	private boolean isValidDate(String input) {
		// TODO Auto-generated method stub
		return false;
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
