package udo.util.parser;

import java.util.ArrayList;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;

public class ParserListHashtag implements ParserListCommand {

	public ParserListHashtag() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fill(Command type, String details, InputData data) {
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
