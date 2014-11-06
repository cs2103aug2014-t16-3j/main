//@author A0114847B
package udo.util.parser.add;

import java.util.ArrayList;

import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class AddActionPlan implements AddActionType {

	/**
	 * This class handles all Plans. It fills in the InputData passed to it.
	 */
	
	public AddActionPlan() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		String title = getTitle(details);
		ArrayList<String> tags = getTags(details);
		if (title != null) {
			data.put(Keys.TITLE, title);
			data.put(Keys.HASHTAGS, tags);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

	@Override
	public String getTitle(String input) {
		String title = input.replaceAll("#", "");
		String parts[] = title.split(" ");
		String newTitle = "";
		for (int i = 1; i < parts.length; i++) {
			newTitle = newTitle + parts[i] + " "; 
		}
		if (newTitle.length() != 0) {
			newTitle = newTitle.trim();
			return newTitle;
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		String tag;
		String words[] = input.split(" ");
		for (String word : words) {
			if (word.startsWith("#")) {
				tag = word.replaceFirst("#", "");
				assert(!tag.isEmpty());
				tagArrayList.add(tag);
			}
		}
		return tagArrayList;
	}

}
