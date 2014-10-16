package udo.util.parser;

import java.util.ArrayList;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ListQuery;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserList {

	public ParserList() {
		// TODO Auto-generated constructor stub
	}
	
	// Returns an ArrayList of tags. Tags do not contain "#"
	// If no tags are found, retun an empty ArrayList
	public ArrayList<String> getTags(String input) {
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
	
	public InputData list(Command type, String details) {
		InputData listInputData = new InputData(type);
		if (details.contains("#")) {
			ArrayList<String> tags = getTags(details);
			if (tags.size() == empty) {
				listInputData.setParsingStatus(ParsingStatus.FAIL);
				return listInputData;
			} else {
				assert(tags.size() > empty);
				assert(!tags.get(0).isEmpty());
				
				listInputData.put(Keys.HASHTAG, tags.get(0));
				listInputData.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
				listInputData.setParsingStatus(ParsingStatus.SUCCESS);
				return listInputData;
			}
		} else {
			listInputData.put(Keys.QUERY_TYPE, ListQuery.ALL);
			listInputData.setParsingStatus(ParsingStatus.SUCCESS);
			return listInputData;
		}
	}
}
