package udo.util.parser;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public interface ParserList extends ParserCommand {
	
	@Override
	public InputData run(Command type, String input) {
		InputData data = list(type, input);
		return data;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}
	
	public InputData list(Command type, String details) {
		InputData data = new InputData(type);
		String lowerCaseDetails = details.toLowerCase();
		if (details.contains("#")) {
			listHashTag(type, details, data);
		
		} else if (details.contains("/") || lowerCaseDetails.contains("day")) {
			listDate(type, details, data);
		
		} else if (lowerCaseDetails.contains("all")){
			listAll(type, details, data);
		
		} else {
			data = new InputData(type);
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return data;
	}
	
	private void listAll(Command type, String details, InputData data) {
		data.put(Keys.QUERY_TYPE, ListQuery.ALL);
	}

	private void listDate(Command type, String details, InputData data) {
		ParserDate listDate = new ParserDate();
		Calendar date = listDate.getDate(details);
		data.put(Keys.QUERY_TYPE, ListQuery.DATE);
		data.put(Keys.DATE, date);
	}

	private void listHashTag(Command type, String details, InputData data) {
		ArrayList<String> tags = getTags(details);
		if (!tags.isEmpty()) {
			data.put(Keys.HASHTAG, tags.get(0));
			data.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

	// Returns an ArrayList of tags. Tags do not contain "#"
	// If no tags are found, retun an empty ArrayList
	private ArrayList<String> getTags(String input) {
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
