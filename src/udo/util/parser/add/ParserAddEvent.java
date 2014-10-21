package udo.util.parser.add;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.parser.ParserDate;
import udo.util.parser.ParserTime;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserAddEvent implements ParserAddCommand {

	public ParserAddEvent() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		String title = getTitle(details);
		ArrayList<String> tags = getTags(details);
		Calendar start = setFirstTimeAndDate(details);
		Calendar end = setSecondTimeAndDate(details);
		if (!title.isEmpty() && start != null && end != null) {
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
		int startingIndex = 4; 							// start after "add "
		int endingIndex = title.indexOf("from") - 2; // trim ending white space
		title = title.substring(startingIndex, endingIndex);
		return title;
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
		ParserDate date = new ParserDate();
		return date.getDate(input);
	}
	
	public Calendar getTime(String input) {
		ParserTime time = new ParserTime();
		return time.getTime(input);
	}
	
	public Calendar setFirstTimeAndDate(String details) {
		Calendar start = getTime(details);
		Calendar date = getDate(details);
		if (start != null && date != null) {
			start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
			start.set(Calendar.MONTH, date.get(Calendar.MONTH));
			start.set(Calendar.YEAR, date.get(Calendar.YEAR));
		}
		return start;
	}
	
	public Calendar setSecondTimeAndDate(String details) {
		int toStringIndex = details.indexOf("to");
		assert(toStringIndex != -1);
		String endingTimeDateString = details.substring(toStringIndex);
		Calendar end = getTime(endingTimeDateString);
		Calendar date = getDate(endingTimeDateString);
		assert(end != null);
		assert(date != null);
		end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		end.set(Calendar.MONTH, date.get(Calendar.MONTH));
		end.set(Calendar.YEAR, date.get(Calendar.YEAR));
		return end;
	}

}
