package udo.util.engine.runners;

import java.util.ArrayList;

import udo.util.engine.Cache;
import udo.util.engine.UndoBin;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

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
		ArrayList<ItemData> result;

		ListQuery queryType = (ListQuery) mInput.get(Keys.QUERY_TYPE);
		
		OutputData output = new OutputData(Command.LIST,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.SUCCESS);

		switch (queryType) {
			case ALL :
				result = mCache.getAllItems();
				break;

			case DONE :
				result = mCache.getAllDone();
				break;

			case SINGLE_HASHTAG :
				String hashtag = (String) mInput.get(Keys.HASHTAG);
				result = mCache.getAllItemsWithHashtag(hashtag);
				output.put(Keys.QUERY_VALUE, hashtag);
				break;

			default :
				// unrecognised list query type.
				return new OutputData(Command.LIST,
						ParsingStatus.SUCCESS,
						ExecutionStatus.FAIL);
		}
		output.put(Keys.QUERY_TYPE, queryType);
		output.put(Keys.ITEMS, result);

		return output;
	}

}
