package udo.util.ui;

import java.util.ArrayList;

import javax.swing.JPanel;

import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;

import udo.util.ui.ListView;

public class Feedback {
	
	private String mCommand;
	private String mStatus;
	private Object mData;

	private DayView mDayView;
	private ListView mListView;
	
	private JPanel mFinalView;
	
	public Feedback(){
		mListView = new ListView();
		mListView.setOpaque(false);
		mDayView = new DayView();
	}
	
	@SuppressWarnings("unchecked")
	public void process(OutputData output){
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
			case LIST: 	mCommand = "Listing ";
						mData = output.get(Keys.ITEMS);
						mListView.removeAll();
						mListView.init((ArrayList<ItemData>) mData);
						mListView.setBounds(20,20,350,500);
						mListView.validate();
						mListView.repaint();
						mFinalView = mListView;
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

	public JPanel getFinalView() {
		return mFinalView;
	}
}
