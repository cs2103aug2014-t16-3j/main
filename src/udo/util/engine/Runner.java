package udo.util.engine;

import udo.util.shared.InputData;
import udo.util.shared.OutputData;

/**
 * this class is the parent class of the classes that help the engine execute commands.
 * the run() method has to be implemented by the extending classes.
 * this class has to be extended.
 */
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
	
	/**
	 * This method executes a command and returns the output data.
	 * This method has to be implemented in an extending class.
	 * The actual execution is defined by the extending class.
	 * @return the output data after execution.
	 */
	public abstract OutputData run();
	
}
