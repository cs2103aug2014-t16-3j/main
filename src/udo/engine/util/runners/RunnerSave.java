//@author A0108358B
package udo.engine.util.runners;

import java.io.IOException;
import java.util.ArrayList;

import udo.data.ItemData;
import udo.data.OutputData;
import udo.engine.util.Cache;
import udo.engine.util.FileManager;
import udo.enums.Command;
import udo.enums.ExecutionStatus;
import udo.enums.ParsingStatus;
import udo.exceptions.CacheAccessException;
import udo.exceptions.WritingToStorageException;

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

	private void writeCacheToFile() throws 
							WritingToStorageException, 
							IOException, 
							CacheAccessException {
		ArrayList<ItemData> itemsToWrite = mCache.getAllItems(); 
		mFileManager.writeToFile(itemsToWrite);
	}

}
