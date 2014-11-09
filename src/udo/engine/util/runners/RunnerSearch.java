//@author A0108358B
package udo.engine.util.runners;

import java.util.ArrayList;

import udo.constants.Constants.Keys;
import udo.data.InputData;
import udo.data.ItemData;
import udo.data.OutputData;
import udo.engine.util.Cache;
import udo.engine.util.UndoBin;
import udo.enums.Command;
import udo.enums.ExecutionStatus;
import udo.enums.ParsingStatus;
import udo.exceptions.CacheAccessException;

public class RunnerSearch extends Runner {
	
	public RunnerSearch(InputData input, UndoBin undoBin, Cache cache) {
		super(input, null, undoBin, cache);
	}

	@Override
	public OutputData run() {
		
		OutputData output = new OutputData(Command.SEARCH, ParsingStatus.SUCCESS);
		
		String searchQuery = (String) mInput.get(Keys.SEARCH_QUERY);
		ArrayList<ItemData> list = new ArrayList<ItemData>();
		
		try {
			list = mCache.searchAllItems(searchQuery);
			
		} catch (CacheAccessException e) {
			e.printStackTrace();
			return new OutputData(Command.SEARCH,
					ParsingStatus.SUCCESS, 
					ExecutionStatus.FAIL);
		}
		
		output.setExecutionStatus(ExecutionStatus.SUCCESS);
		output.put(Keys.SEARCH_QUERY, searchQuery);
		output.put(Keys.ITEMS, list);
		
		return output;
	}

}
