package udo.util.parser;

import java.util.ArrayList;

import udo.util.shared.Command;
import udo.util.shared.InputData;

public class ParserAddEvent implements ParserAddCommand {

	public ParserAddEvent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		// TODO Auto-generated method stub
		
	}
	
	public InputData addEvent(Command type, String details) {
		InputData eventInputData = new InputData(type);
		String title = getEventTitle(details);
		ArrayList<String> tags = getTags(details);
		Calendar start = setFirstTimeAndDate(details);
		Calendar end = setSecondTimeAndDate(details);
		if (title.isEmpty()) {
			eventInputData.setParsingStatus(ParsingStatus.FAIL);
		} else {
			assert(start != null);
			assert(end != null);
			eventInputData.put(Keys.TITLE, title);
			eventInputData.put(Keys.HASHTAGS, tags);
			eventInputData.put(Keys.START, start);
			eventInputData.put(Keys.END, end);
			eventInputData.setParsingStatus(ParsingStatus.SUCCESS);
		}
		return eventInputData;
	}

	@Override
	public String getTitle(String input) {
		// TODO Auto-generated method stub
		return null;
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

}
