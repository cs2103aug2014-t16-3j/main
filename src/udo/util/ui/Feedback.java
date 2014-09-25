package udo.util.ui;

import udo.util.shared.Command;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.shared.Constants.Keys;

public class Feedback {
	
	private String mCommand;
	private String mStatus;
	private String mStart;
	private String mEnd;
	public Feedback(OutputData output){
		if(output.getStatus().equals(ExecutionStatus.SUCCESS)) {
			switch(output.getCommand()){
			case ADD_EVENT: mCommand = "Added ";
			ItemData item = (ItemData) output.get(Keys.ITEM);
			item.get(Keys.START);
				break;
			case DELETE: mCommand = "Deleted ";
				break;
			case EXIT: mCommand = "Exit";
				break;
			case LIST: mCommand = "Listing ";
				break;
			case SAVE: mCommand = "Saved ";
				break;
			default:
				break;
			}
		}else{
			mCommand = "Failed to do specified task. Please try again";
		}
	}
	
	public String getCommand(){
		return mCommand;
	}
	
	public String getStatus(){
		return mStatus;
	}
}
