//@author A0114847B
package udo.util.parser.add;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.parser.DateGetter;
import udo.util.parser.TimeGetter;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class AddActionEvent implements AddActionType {
	
	/**
	 * This class handles the raw input for add event. It fills up the InputData
	 * passed to it.
	 */

	public AddActionEvent() {
		
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
				return null;
			}
		} else {
			return null;
		}
	}

}
