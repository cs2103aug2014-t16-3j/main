//@author A0114847B
package udo.util.parser.edit;

import java.util.ArrayList;

import udo.util.shared.Constants.Keys;
import udo.util.shared.EditField;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class ParserEditTitle implements ParserEditCommand {

	/**
	 * This class handles the title field
	 */
	
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
	
	private String getTitle(String details) {
		String title = details.replaceAll("#", "");
		String parts[] = title.split(" ");
		String newTitle = "";
		for (int i=3; i< parts.length; i++) { 		// "edit 12345 title <new title>"
			newTitle = newTitle + parts[i] + " ";	// new title starts from index 3
		}
		newTitle = newTitle.trim();
		if (newTitle.length() != 0) {
			return newTitle;
		} else {
			return null;
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
