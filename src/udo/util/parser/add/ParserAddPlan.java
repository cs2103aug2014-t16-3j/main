//@author A0114847B
package udo.util.parser.add;

import java.util.ArrayList;

import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class ParserAddPlan implements ParserAddCommand {

	public ParserAddPlan() {
		
	}

	@Override
	public void fill(String details, InputData data) {
		int startingIndex = 4;
		String title = details.substring(startingIndex);
		title = title.replaceAll("#", "");
		ArrayList<String> tags = getTags(details);
		if (!title.isEmpty()) {
			data.put(Keys.TITLE, title);
			data.put(Keys.HASHTAGS, tags);
			data.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			data.setParsingStatus(ParsingStatus.FAIL);
		}
	}

	@Override
	public String getTitle(String input) {
		return null;
	}

	// Returns an ArrayList of tags. Tags do not contain "#"
	// If no tags are found, retun an empty ArrayList
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
