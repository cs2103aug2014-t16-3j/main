//@author A0114847B
package udo.parser.util.add;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import udo.constants.Constants.Keys;
import udo.constants.Constants.LoggingStrings;
import udo.data.InputData;
import udo.enums.ParsingStatus;
import udo.language.LanguagePack;
import udo.parser.Parser;
import udo.parser.util.DateGetter;
import udo.parser.util.TimeGetter;

public class AddActionEvent implements AddActionType {
	
	/**
	 * This class handles the raw input for add event. It fills up the InputData
	 * passed to it.
	 */

	private Logger mLogger;
	
	public AddActionEvent() {
		mLogger = Logger.getLogger(Parser.class.getSimpleName());
		mLogger.setLevel(Level.ALL);
	}

	@Override
	public void fill(String details, InputData data) {
		String title = getTitle(details);
		ArrayList<String> tags = getTags(details);
		Calendar start = setFirstTimeAndDate(details);
		Calendar end = setSecondTimeAndDate(details);
		if (title != null && start != null && end != null) {
			data.put(Keys.TITLE, title);
			data.put(Keys.HASHTAGS, tags);
			data.put(Keys.START, start);
			data.put(Keys.END, end);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

	@Override
	public String getTitle(String input) {
		String title = input.replaceAll("#", "");
		String parts[] = title.split(" ");
		String newTitle = "";
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].equals(mLang.getFROM())) {
				break;
			}
			newTitle = newTitle + parts[i] + " "; 
		}
		if (newTitle.length() != 0) {
			newTitle = newTitle.trim();
			return newTitle;
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		String tag;
		boolean hashTagFilled;
		String words[] = input.split(" ");
		for (String word : words) {
			hashTagFilled = word.length() > 1;
			if (word.startsWith("#") && hashTagFilled) {
				tag = word.replaceFirst("#", "");
				tagArrayList.add(tag);
			}
		}
		return tagArrayList;
	}
	
	public Calendar getDate(String input) {
		DateGetter date = new DateGetter();
		return date.getDate(input);
	}
	
	public Calendar getTime(String input) {
		TimeGetter time = new TimeGetter();
		return time.getTime(input);
	}
	
	public Calendar setFirstTimeAndDate(String details) {
		Calendar start = getTime(details);
		Calendar date = getDate(details);
		if (start != null && date != null) {
			start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
			start.set(Calendar.MONTH, date.get(Calendar.MONTH));
			start.set(Calendar.YEAR, date.get(Calendar.YEAR));
			return start;
		}
		mLogger.log(Level.WARNING, 
					"Event's first time and date cannot be set. 1 of this field is missing");
		return null;
	}
	
	public Calendar setSecondTimeAndDate(String details) {
		int toStringIndex = details.lastIndexOf(mLang.getTO());
		String endingTimeDateString = details.substring(toStringIndex);
		Calendar end = getTime(endingTimeDateString);
		Calendar date = getDate(endingTimeDateString);
		if (end != null && date != null) {
			end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
			end.set(Calendar.MONTH, date.get(Calendar.MONTH));
			end.set(Calendar.YEAR, date.get(Calendar.YEAR));
			return end;
		} else if (end != null) {
			date = getDate(details);
			if (date != null) {
				end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
				end.set(Calendar.MONTH, date.get(Calendar.MONTH));
				end.set(Calendar.YEAR, date.get(Calendar.YEAR));
				return end;
			} else {
				mLogger.log(Level.WARNING, "Event's 2nd time field is missing");
				return null;
			}
		} else {
			return null;
		}
	}

}
