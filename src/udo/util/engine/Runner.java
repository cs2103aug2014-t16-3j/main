package udo.util.engine;

import udo.util.shared.InputData;
import udo.util.shared.OutputData;

public abstract class Runner {

	protected InputData mInput;
	protected FileManager mFileManager;
	protected Cache mCache;
	protected UndoBin mUndoBin;
	
	protected Runner(InputData i, FileManager f, UndoBin u, Cache c) {
		mInput = i;
		mFileManager = f;
		mUndoBin = u;
		mCache = c;
	}
	
	public abstract OutputData run();
	
}
