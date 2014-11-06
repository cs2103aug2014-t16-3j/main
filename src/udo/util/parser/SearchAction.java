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
		/*
		InputData searchInputData = new InputData(type);
		String parts[] = input.split(" ");
		if (isValidSearch(parts)) {
			//searchInputData.put(Keys.SEARCH_QUERY, );
			searchInputData.setParsingStatus(ParsingStatus.SUCCESS);
		} else {
			searchInputData.setParsingStatus(ParsingStatus.FAIL);
		}
		
		searchInputData.put(Keys.SEARCH_QUERY, input);
		searchInputData.setParsingStatus(ParsingStatus.SUCCESS);
		
		return searchInputData;
		*/
		return null;
	}

	@Override
	public InputData run(Command type) {
		return null;
	}

	private boolean isValidSearch(String[] parts) {
		// TODO Auto-generated method stub
		return false;
	}

}
