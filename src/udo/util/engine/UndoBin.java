package udo.util.engine;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.ItemData;

/**
 * only for engine to use.
 */
public class UndoBin {
	InputData mInputData;

	public UndoBin() {
		mInputData = null;
	}

	public void putInputData(InputData input) {
		mInputData = input;
	}

	public InputData getInputData() {
		return mInputData;
	}
}
