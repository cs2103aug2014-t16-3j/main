//@author A0108358B
package udo.engine.util.runners;

import java.util.ArrayList;
import java.util.Calendar;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.data.ItemData;
import udo.data.OutputData;
import udo.engine.util.Cache;
import udo.engine.util.UndoBin;
import udo.enums.Command;
import udo.enums.ExecutionStatus;
import udo.enums.ListQuery;
import udo.enums.ParsingStatus;
import udo.exceptions.CacheAccessException;

public class RunnerList extends Runner {

	public RunnerList(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		
		ArrayList<ItemData> result;

		ListQuery queryType = (ListQuery) mInput.get(Keys.QUERY_TYPE);
		
		OutputData output = new OutputData(Command.LIST,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.SUCCESS);
		try {
			switch (queryType) {
				case ALL :
					result = mCache.getAllItems();
					break;

				case DONE :
					result = mCache.getAllDone();
					break;

				case SINGLE_HASHTAG :
					String hashtag = (String) mInput.get(Keys.QUERY_VALUE);
					result = mCache.getAllItemsWithHashtag(hashtag);
					output.put(Keys.QUERY_VALUE, hashtag);
					break;

				case DATE :
					Calendar dateCal = (Calendar) mInput.get(Keys.QUERY_VALUE);
					result = mCache.getAllItemsOn(dateCal);
					output.put(Keys.QUERY_VALUE, dateCal);
					break;
					
				case EVENT :
					result = mCache.getAllEvents();
					break;
					
				case TASK :
					result = mCache.getAllUndoneTasks();
					break;
					
				case PLAN :
					result = mCache.getAllUndonePlans();
					break;

				default :
					result = null;
					output.setExecutionStatus(ExecutionStatus.FAIL);
			}
			output.put(Keys.QUERY_TYPE, queryType);
			output.put(Keys.ITEMS, result);

		} catch (CacheAccessException e) {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		}

		return output;
	}

}
