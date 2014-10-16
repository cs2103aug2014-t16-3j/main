package udo.util.parser;

import java.util.ArrayList;
import java.util.Calendar;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserList {

	public ParserList() {
		
	}
	
	public InputData list(Command type, String details) {
		InputData data;
		if (details.contains("#")) {
			data = listHashTag(type, details);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else if (details.contains("/") ||
				details.contains("day")) {
			data = listDate(type, details);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else if (details.contains("all")){
			data = listAll(type, details);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data = new InputData(type);
			data.setParsingStatus(ParsingStatus.FAIL);
		}
		return data;
	}
	
	private InputData listAll(Command type, String details) {
		InputData data = new InputData(type);
		data.put(Keys.QUERY_TYPE, ListQuery.ALL);
		return data;
	}

	private InputData listDate(Command type, String details) {
		InputData data = new InputData(type);
		ParserDate parserDate = new ParserDate();
		Calendar date = parserDate.getDate(details);
		data.put(Keys.QUERY_TYPE, ListQuery.DATE);
		data.put(Keys.DATE, date);
		return data;
	}

	private InputData listHashTag(Command type, String details) {
		InputData data = new InputData(type);
		ArrayList<String> tags = getTags(details);
		data.put(Keys.HASHTAG, tags.get(0));
		data.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
		return data;
	}

	// Returns an ArrayList of tags. Tags do not contain "#"
	// If no tags are found, retun an empty ArrayList
	private ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		String tag;
		String words[] = input.split(" ");
		for (String word : words) {
			if (word.startsWith("#")) {
				tag = word.replaceFirst("#", "");
				tagArrayList.add(tag);
			}
		}
		return tagArrayList;
	}
}
