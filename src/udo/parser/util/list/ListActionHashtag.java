//@author A0114847B
package udo.parser.util.list;

import java.util.ArrayList;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.Command;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;

public class ListActionHashtag implements ListActionCommand {

	/**
	 * This class handles the hashtag field
	 */
	
	public ListActionHashtag() {
		
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		ArrayList<String> tags = getTags(details);
		if (!tags.isEmpty()) {
			data.put(Keys.QUERY_VALUE, tags.get(0));
			data.put(Keys.QUERY_TYPE, ListQuery.SINGLE_HASHTAG);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}
	
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
