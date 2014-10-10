package udo.util.ui;

import java.util.ArrayList;

import javax.swing.JPanel;

import udo.util.shared.Constants.Keys;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.ItemData;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;
import udo.util.ui.ListView;

public class Feedback {

	private String mCommand;
	private String mStatus;
	private Object mData;

	private DayView mDayView;
	private ListView mListView;
	private ToDoView mToDoView;
	private SingleView mSingleView;
	private DayView mMainTodayView;
	private ToDoView mMainToDoView;

	private JPanel mFinalView = new JPanel();

	public Feedback() {
		mListView = new ListView();
		mDayView = new DayView();
		mToDoView = new ToDoView();
		mSingleView = new SingleView();
		mMainTodayView = new DayView();
		mMainToDoView = new ToDoView();
	}

	public JPanel initTodayView(ArrayList<ItemData> data) {
		mMainTodayView.removeAll();
		mMainTodayView.init(data);
		return mMainTodayView;
	}
	
	public JPanel initToDoView(ArrayList<ItemData> data) {
		mMainToDoView.removeAll();
		mMainToDoView.init(data);
		return mMainToDoView;
	}
	
	public void process(OutputData output) {
		if (output.getParsingStatus().equals(ParsingStatus.SUCCESS)) {
			if (output.getExecutionStatus().equals(ExecutionStatus.SUCCESS)) {
				switch (output.getCommand()) {
				case ADD_EVENT:
					add_entry(output, "addEvent");
					break;
				case ADD_TASK:
					break;
				case ADD_PLAN:
					break;
				case DELETE:
					delete_entry(output);
					break;
				case EXIT:
					mCommand = "Exit";
					break;
				case LIST:
					list_entry(output);
					break;
				case UNDO:
					break;
				case EDIT:
					break;
				case SAVE:
					mCommand = "Saved ";
					break;
				default:
					break;
				}
			} else {
				mCommand = "Command cannot be executed. Please try again";
			}
		} else {
			mCommand = "Command not recognised. Please try again";
		}
	}

	
	public void add_entry(OutputData output, String type) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mSingleView.removeAll();
		mSingleView.init(item, type);
		mFinalView = mSingleView;
		mCommand = "Added " + item.get(Keys.TITLE);
	}
	
	public void delete_entry(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mSingleView.removeAll();
		mSingleView.init(item, "delete");
		mFinalView = mSingleView;
		mCommand = "Deleted " + item.get(Keys.TITLE);
	}
	
	@SuppressWarnings("unchecked")
	public void list_entry(OutputData output) {
		// TODO check if query is specified to 1 day, is a todo, or general list view
		mData = output.get(Keys.ITEMS);
		if(((ArrayList<ItemData>) mData).size() == 0) {
			mCommand = "No items found";
		} else {
			// TODO make a method that can take in string/ calendar etc to build the popup string
			ListQuery queryType = (ListQuery) output.get(Keys.QUERY_TYPE);
			String query = "";
			switch(queryType) {
			case ALL :
				query = "All";
				break;
			case SINGLE_HASHTAG:
				query = "#" + (String) output.get(Keys.QUERY);
				break;
			default:
				break;
				
			}
			mCommand = "Listing " + query;
		}
		mListView.removeAll();
		mListView.init( (ArrayList<ItemData>) mData);
		/* testing dayview
		mDayView.removeAll();
		mDayView.init((ArrayList<ItemData>) mData);*/
//		if(mFinalView.equals(mDayView)) { //if listquery is a date
//			mFinalView = mListView;
//		} else {
//			mFinalView = mDayView;
//		}
		mFinalView = mListView;
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
