package udo.util.engine.runners;

import java.util.ArrayList;

import udo.util.engine.Cache;
import udo.util.engine.FileManager;
import udo.util.engine.UndoBin;
import udo.util.exceptions.WritingToStorageFailedException;
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
		} catch (WritingToStorageFailedException e) {
			output.setExecutionStatus(ExecutionStatus.FAIL);
		}
		
		return output;
	}

	private boolean writeCacheToFile() throws WritingToStorageFailedException {
		ArrayList<ItemData> itemsToWrite = mCache.getAllItems(); 
		mFileManager.writeToFile(itemsToWrite);
		return true;
	}

}
