package udo.util.engine;

import udo.util.shared.Command;
import udo.util.shared.ItemData;

/**
 * only for engine to use.
 */
public class RecycleBin {
	ItemData mItem;
	Command mCommand;
	
	public RecycleBin() {
		mItem = null;
		mCommand = null;
	}
	
	public ItemData putItem(ItemData item) {
		mItem = item;
		return mItem;
	}
	
	public Command putCommand(Command cmd) {
		mCommand = cmd;
		return mCommand;
	}
	
	public ItemData getItem() {
		return mItem;
	}
	
	public Command getCommand() {
		return mCommand;
	}
}
