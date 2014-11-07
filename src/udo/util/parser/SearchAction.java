//@author A0114847B
package udo.util.parser;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.InputData;
import udo.util.shared.ParsingStatus;

public class SearchAction implements Action {

	public SearchAction() {
		
	}

	@Override
	public InputData run(Command type, String input) {
		InputData searchInputData = new InputData(type);
		if (isValidSearch(input)) {
			String searchString = getSearchQuery(input);
			searchInputData.put(Keys.SEARCH_QUERY, searchString);
			searchInputData.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			searchInputData.setParsingStatus(ParsingStatus.FAIL);
		}
		return searchInputData;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}
	
	private String getSearchQuery(String input) {
		String parts[] = input.split(" ");
		String searchString = "";
		for (int i = 1; i < parts.length; i++) {
			searchString = searchString + parts[i] + " ";
		}
		return searchString;
	}
 
	private boolean isValidSearch(String input) {
		String parts[] = input.split(" ");
		if (parts.length >= 2) {
			return true;
		} else {
			return false;
		}
	}

}
