package udo.util.ui;

public class CommandHistory {

	private String[] mCmdList;
	private int mIndex;
	
	public CommandHistory(int maxSize) {
		mCmdList = new String[maxSize];
		mIndex = -1;
	}
	
	public int getIndex() {
		return mIndex;
	}
	
	public void initIndex() {
		mIndex = -1;
	}
	
	public String getCmd() {
		if(mIndex == -1) {
			return "";
		}
		if(mCmdList[mIndex] != null) {
			return mCmdList[mIndex];	
		} else {
			mIndex--;
			return getCmd();
		}
	}
	
	public String cycle(int step) {
		assert step == 1 || step == -1;
		mIndex += step;
		if(mIndex > 4) {
			mIndex = 4;
		}
		if(mIndex < 0) {
			mIndex = 0;
		}
		return getCmd();
	}
	
	public void add(String newCmd) {
		for(int i = mCmdList.length-1; i>0; i--) {
			mCmdList[i] = mCmdList[i-1];
		}
		mCmdList[0] = newCmd;
	}
}
