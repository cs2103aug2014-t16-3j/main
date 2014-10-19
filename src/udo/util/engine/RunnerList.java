package udo.util.engine;

import java.util.ArrayList;
import java.util.Collections;

import udo.util.shared.Command;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

public class RunnerList extends Runner {

	public RunnerList(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		/*
		 * 1. build a list of all items 2. select only the items we want. 3.
		 * sort the items according to date/time 4. put the items in the
		 * outputdata. 5. return output
		 */
		ArrayList<ItemData> listOfAllItems = mCache.getAllItems();
		ArrayList<ItemData> result;

		ListQuery query = (ListQuery) mInput.get(Keys.QUERY_TYPE);
		String queryString = "";
		switch (query) {
		case ALL:
			result = listOfAllItems;
			break;
		case SINGLE_HASHTAG:
			queryString = (String) mInput.get(Keys.HASHTAG);
			result = trimList(listOfAllItems, queryString);
			break;
		default:
			return null;
		}

		OutputData output = new OutputData(Command.LIST,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.SUCCESS);
		output.put(Keys.QUERY_TYPE, query);
		output.put(Keys.QUERY, queryString);
		output.put(Keys.ITEMS, result);

		return output;
	}
	
	private ArrayList<ItemData> trimList(ArrayList<ItemData> list, String tag) {
		ArrayList<ItemData> result = new ArrayList<ItemData>();
		for (ItemData item : list) {
			@SuppressWarnings("unchecked")
			ArrayList<String> tags = (ArrayList<String>) item.get(Keys.HASHTAGS);
			if (tags.contains(tag)) {
				result.add(item);
			}
		}
		Collections.sort(result);
		return result;
	}

}
