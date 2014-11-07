package udo.util.engine.runners;

import java.util.ArrayList;

import udo.util.engine.Cache;
import udo.util.engine.UndoBin;
import udo.util.exceptions.CacheAccessException;
import udo.util.shared.Command;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;
import udo.util.shared.Constants.Keys;

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
