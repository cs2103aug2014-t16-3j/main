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

	private JPanel mFinalView = new JPanel();

	public Feedback() {
		mListView = new ListView();
		mListView.setOpaque(false);
		mDayView = new DayView();
	}

	public void process(OutputData output) {
		if (output.getStatus().equals(ExecutionStatus.SUCCESS)) {
			switch (output.getCommand()) {
			case ADD_EVENT:
				add_entry(output);
				break;
			case DELETE:
				mCommand = "Deleted ";
				break;
			case EXIT:
				mCommand = "Exit";
				break;
			case LIST:
				list_entry(output);
				break;
			case SAVE:
				mCommand = "Saved ";
				break;
			default:
				break;
			}
		} else {
			mCommand = "Failed to do specified task. Please try again";
		}
	}

	
	public void add_entry(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mCommand = "Added " + item.get(Keys.TITLE);
	}
	
	@SuppressWarnings("unchecked")
	public void list_entry(OutputData output) {
		mCommand = "Listing ";
		mData = output.get(Keys.ITEMS);
		mListView.removeAll();
		mListView.init((ArrayList<ItemData>) mData);
		// testing dayview
		mDayView.removeAllButTicker();
		mDayView.init((ArrayList<ItemData>) mData);
		if(mFinalView.equals(mDayView)) {
			mFinalView = mListView;
		} else {
			mFinalView = mDayView;
		}
	}

	public String getCommand() {
		return mCommand;
	}

	public String getStatus() {
		return mStatus;
	}

	public JPanel getFinalView() {
		return mFinalView;
	}
}
