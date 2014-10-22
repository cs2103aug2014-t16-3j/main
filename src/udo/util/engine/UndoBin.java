package udo.util.engine;

import udo.util.shared.InputData;

/**
 * only for engine to use.
 */
public class UndoBin {
	private InputData mInputData;

	public UndoBin() {
		mInputData = null;
	}

	public void putInputData(InputData input) {
		mInputData = input;
	}

	public InputData getUndoInputData() {
		return mInputData;
	}

	public void clear() {
		mInputData = null;
	}
}
