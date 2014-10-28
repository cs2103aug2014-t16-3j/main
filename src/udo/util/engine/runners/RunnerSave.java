package udo.util.engine.runners;

import java.io.IOException;
import java.util.ArrayList;

import udo.util.engine.Cache;
import udo.util.engine.FileManager;
import udo.util.exceptions.CacheAccessException;
import udo.util.exceptions.WritingToStorageException;
import udo.util.shared.Command;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class RunnerSave extends Runner {

	public RunnerSave(FileManager f, Cache c) {
		super(null, f, null, c);
		
	}

	@Override
	public OutputData run() {
		OutputData output = new OutputData(Command.SAVE,
				ParsingStatus.SUCCESS, 
				ExecutionStatus.SUCCESS);
		try {
			writeCacheToFile();
			
		} catch (WritingToStorageException e) {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		} catch (IOException e) {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		} catch (CacheAccessException e) {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		}
		
		return output;
	}

	private void writeCacheToFile() throws WritingToStorageException, IOException, CacheAccessException {
		ArrayList<ItemData> itemsToWrite = mCache.getAllItems(); 
		mFileManager.writeToFile(itemsToWrite);
	}

}
