//@author A0114847B
package udo.util.parser.add;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import udo.main.Parser;
import udo.util.parser.DateGetter;
import udo.util.parser.TimeGetter;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.LoggingStrings;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class AddActionTask implements AddActionType {

	/**
	 * This class handles all Tasks. It fills in the InputData passed to it.
	 */
	
	private Logger mLogger;
	
	public AddActionTask() {
		mLogger = Logger.getLogger(Parser.class.getSimpleName());
		mLogger.setLevel(Level.ALL);
	}

	@Override
	public void fill(String details, InputData data) {
		String title = getTitle(details);
		ArrayList<String> tags = getTags(details);
		Calendar end = setFirstTimeAndDate(details);
		if (title != null && end != null) {
			data.put(Keys.TITLE, title);
			data.put(Keys.HASHTAGS, tags);
			data.put(Keys.DUE, end);
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
			if (parts[i].equals(mLang.getBY())) {
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
		} else {
			mLogger.log(Level.WARNING, 
						"Task's due time and due date not set. 1 of this field is missing");
			return null;
		}
	}
}
