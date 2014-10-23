package udo.util.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
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
		mMainToDoView.setBounds(0,0,UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT);
		mMainToDoView.setLayout(new WrapLayout());
	}

	public JPanel initTodayView(ArrayList<ItemData> data) {
		mMainTodayView.removeAll();
		mMainTodayView.init(data);
		mMainTodayView.revalidate();
		return mMainTodayView;
	}

	public JPanel initToDoView(ArrayList<ItemData> data) {
		mMainToDoView.removeAll();
		mMainToDoView.init(data);
		mMainToDoView.revalidate();
		return mMainToDoView;
	}

	public void process(OutputData output) {
		if (output.getParsingStatus().equals(ParsingStatus.SUCCESS)) {
			if (output.getExecutionStatus().equals(ExecutionStatus.SUCCESS)) {
				switch (output.getCommand()) {
					case ADD_EVENT:
					case ADD_TASK:
					case ADD_PLAN:
						add_entry(output, output.getCommand());
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
						edit_entry(output);
						break; 
					case MARK_DONE:
						mark_done(output);
						break;
					case TOGGLE_DONE:
						toggle_done(output);
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

	private void toggle_done(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mSingleView.removeAll();
		mSingleView.init(output, Command.TOGGLE_DONE);
		mFinalView = mSingleView;
		mCommand = "Toggled completion status of " + item.get(Keys.TITLE);
	}

	private void mark_done(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mSingleView.removeAll();
		mSingleView.init(output, Command.MARK_DONE);
		mFinalView = mSingleView;
		mCommand = "Marked " + item.get(Keys.TITLE) + " as done";
	}

	private void add_entry(OutputData output, Command type) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mSingleView.removeAll();
		mSingleView.init(output, type);
		mFinalView = mSingleView;
		mCommand = "Added " + item.get(Keys.TITLE);
	}

	private void delete_entry(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mSingleView.removeAll();
		mSingleView.init(output, Command.DELETE);
		mFinalView = mSingleView;
		mCommand = "Deleted " + item.get(Keys.TITLE);
	}
	
	private void edit_entry(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mSingleView.removeAll();
		mSingleView.init(output, Command.EDIT);
		mFinalView = mSingleView;
		mCommand = "Edited " + item.get(Keys.UID);
		
	}
	
	@SuppressWarnings("unchecked")
	private void list_entry(OutputData output) {
		// TODO check if query is specified to 1 day, is a todo, or general list
		// view
		mData = output.get(Keys.ITEMS);
		if (((ArrayList<ItemData>) mData).size() == 0) {
			mCommand = "No items found";
		} else {
			// TODO make a method that can take in string/ calendar etc to build
			// the popup string
			ListQuery queryType = (ListQuery) output.get(Keys.QUERY_TYPE);
			String query = "";
			switch (queryType) {
				case ALL:
					query = "all items";
					setToListView();
					break;
				case SINGLE_HASHTAG:
					query = "#" + (String) output.get(Keys.QUERY);
					setToListView();
					break;
				case DONE:
					query = "items that have been marked as done";
					setToListView();
					break;
				case DATE:
					Date date = ((Calendar) output.get(Keys.QUERY)).getTime();
					query = "items on " + UI.DD_MMMM_YYYY.format(date);
					setToDayVIew(date);
					break;
				default:
					break;

			}
			mCommand = "Listing " + query;
		}
		
	}
	
	private void setToListView() {
		mListView.populateView((ArrayList<ItemData>) mData);
		mFinalView = mListView;
	}
	
	private void setToDayVIew(Date date) {
		mDayView.init(date, (ArrayList<ItemData>) mData);
		mFinalView = mDayView;
	}
	
	private void setToToDoView() {
		mToDoView.init((ArrayList<ItemData>) mData);
		mFinalView = mToDoView;
	}

	public String getCommand() {
		return mCommand;
	}

	public JPanel getFinalView() {
		return mFinalView;
	}
}
