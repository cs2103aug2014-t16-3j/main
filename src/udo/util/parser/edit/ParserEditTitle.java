package udo.util.parser.edit;

import java.util.ArrayList;

import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditTitle implements ParserEditCommand {

	public ParserEditTitle() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		String title = getTitle(details);
		ArrayList<String> tags = getTags(details);
		if (title != null && !title.isEmpty()) {
			data.put(Keys.FIELD, EditField.TITLE);
			data.put(Keys.VALUE, title);
			data.put(Keys.HASHTAGS, tags);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}
	
	// need to take in tags as well
	// returns the new title if it exists
	// otherwise returns null
	private String getTitle(String details) {
		int startingIndex = 17; // new info starts after "edit 12345 title "
		if (details.length() > startingIndex) {
			String title = details.substring(startingIndex);
			title = title.replaceAll("#", "");
			return title;
		} else {
			return null;
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
