//@author A0114847B
package udo.parser.util.edit;

import java.util.ArrayList;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.enums.EditField;
import udo.enums.ParsingStatus;

public class EditActionTitle implements EditActionField {

	/**
	 * This class handles the title field
	 */
	
	public EditActionTitle() {
		
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
