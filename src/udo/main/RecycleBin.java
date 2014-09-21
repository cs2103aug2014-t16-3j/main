package udo.main;

import udo.util.Command;
import udo.util.ItemData;

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
