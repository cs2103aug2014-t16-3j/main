//@author A0114088H
package udo.util.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import udo.language.LanguagePack;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.ExecutionStatus;
import udo.util.shared.ItemData;
import udo.util.shared.ListQuery;
import udo.util.shared.OutputData;
import udo.util.shared.ParsingStatus;

public class Feedback {

	private String mCommand;
	private Object mData;

	private Screen mDayView;
	private Screen mToDoView;
	private Screen mCenterView;
	private Screen mMainTodayView;
	private Screen mMainToDoView;

	private JPanel mFinalView = new JPanel();

	private JScrollPane mMainScrollPane;
	
	private LanguagePack mLang = LanguagePack.getInstance();

	public Feedback() {
		mCenterView = new MainScreen(UI.MAIN_WIDTH - UI.MAIN_PADDING, 
									UI.MAIN_HEIGHT - UI.TEXTFIELD_HEIGHT);
		mDayView = new DayScreen(UI.MAIN_WIDTH - UI.MAIN_PADDING, 
									UI.MAIN_HEIGHT - UI.TEXTFIELD_HEIGHT);
		mToDoView = new ToDoScreen(UI.MAIN_WIDTH - UI.MAIN_PADDING, 
									UI.MAIN_HEIGHT - UI.TEXTFIELD_HEIGHT);
		mMainTodayView = new DayScreen(UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT);
		mMainToDoView = new ToDoScreen(UI.SUBVIEW_WIDTH,UI.SUBVIEW_HEIGHT);
	}

	public JPanel getTodayView(ArrayList<ItemData> data) {
		mMainTodayView.removeAll();
		mMainTodayView.init(data);
		mMainTodayView.revalidate();
		return mMainTodayView;
	}

	public JPanel getToDoView(ArrayList<ItemData> data) {
		mMainToDoView.removeAll();
		mMainToDoView.init(data);
		mMainToDoView.revalidate();
		return mMainToDoView;
	}

	public void process(OutputData output) {
		if (output.getParsingStatus().equals(ParsingStatus.SUCCESS)) {
			if (output.getExecutionStatus().equals(ExecutionStatus.SUCCESS)) {
				mCenterView.removeAll();
				switch (output.getCommand()) {
					case ADD_EVENT :
					case ADD_TASK :
					case ADD_PLAN :
						add_entry(output, output.getCommand());
						break;
						
					case DELETE :
						delete_entry(output);
						break;
						
					case LIST :
						list_entry(output);
						break;
						
					case UNDO :
						break;
						
					case EDIT :
						edit_entry(output);
						break;
						
					case MARK_DONE :
						mark_done(output);
						break;
						
					case TOGGLE_DONE :
						toggle_done(output);
						break;
						
					case SAVE :
						mCommand = mLang.getPOPUP_SAVED();
						break;
						
					case SEARCH :
						mCommand = mLang.getPOPUP_SEARCH();
						search_entry(output);
						break;
						
					case EXIT :
						mCommand = "";
						break;
						
					default :
						break;
				}
			} else {
				mCommand = mLang.getPOPUP_EXEC_FAIL();
			}
		} else {
			mCommand = mLang.getPOPUP_PARSING_FAIL();
		}
	}

	private void toggle_done(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mCenterView.init(output, Command.TOGGLE_DONE);
		mFinalView = mCenterView;
		mCommand = mLang.getPOPUP_TOGGLE_DONE() + item.get(Keys.TITLE);
	}

	private void mark_done(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mCenterView.init(output, Command.MARK_DONE);
		mFinalView = mCenterView;
		mCommand = mLang.getPOPUP_MARK_AS_DONE() + item.get(Keys.TITLE);
	}

	private void add_entry(OutputData output, Command type) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mCenterView.init(output, type);
		mFinalView = mCenterView;
		mCommand = mLang.getPOPUP_ADDED() + item.get(Keys.TITLE);
	}

	private void delete_entry(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mCenterView.init(output, Command.DELETE);
		mFinalView = mCenterView;
		mCommand = mLang.getPOPUP_DELETED() + item.get(Keys.TITLE);
	}

	private void edit_entry(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		mCenterView.init(output, Command.EDIT);

		mFinalView = mCenterView;
		mCommand = mLang.getPOPUP_EDITED() + item.get(Keys.UID);

	}

	@SuppressWarnings("unchecked")
	private void search_entry(OutputData output) {
		mData = output.get(Keys.ITEMS);
		if (((ArrayList<ItemData>) mData).size() == 0) {
			mCommand = mLang.getPOPUP_NO_ITEMS_FOUND();
		} else {
			String searchQuery = (String) output.get(Keys.SEARCH_QUERY);
			mCommand = mLang.getPOPUP_SEARCH() + "\"" + searchQuery + "\"";
			setToListView();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void list_entry(OutputData output) {
		mData = output.get(Keys.ITEMS);
		if (((ArrayList<ItemData>) mData).size() == 0) {
			mCommand = mLang.getPOPUP_NO_ITEMS_FOUND();
		} else {
			ListQuery queryType = (ListQuery) output.get(Keys.QUERY_TYPE);
			String query = "";
			switch (queryType) {
				case ALL :
					query = mLang.getPOPUP_QUERY_ALL();
					setToListView();
					break;
					
				case SINGLE_HASHTAG :
					query = "#" + (String) output.get(Keys.QUERY_VALUE);
					setToListView();
					break;
					
				case DONE :
					query = mLang.getPOPUP_QUERY_DONE();
					setToListView();
					break;
					
				case DATE :
					Date date = ((Calendar) output.get(Keys.QUERY_VALUE))
							.getTime();
					query = mLang.getPOPUP_QUERY_DATE() + UI.DD_MMMM_YYYY.format(date);
					setToDayVIew(date);
					break;
					
				case EVENT :
					query = mLang.getPOPUP_QUERY_EVENT();
					setToListView();
					break;
					
				case PLAN :
					query = mLang.getPOPUP_QUERY_PLAN();
					setToListView();
					break;
					
				case TASK :
					query = mLang.getPOPUP_QUERY_TASK();
					setToToDoView();
					break;
					
				default :
					break;

			}
			mCommand = mLang.getPOPUP_LIST() + query;
		}

	}

	@SuppressWarnings("unchecked")
	private void setToListView() {
		mCenterView.init((ArrayList<ItemData>) mData);
		mMainScrollPane = mCenterView.getScrollPane();
		mFinalView = mCenterView;
	}

	@SuppressWarnings("unchecked")
	private void setToDayVIew(Date date) {
		mDayView.init(date, (ArrayList<ItemData>) mData);
		mMainScrollPane = mDayView.getScrollPane();
		mFinalView = mDayView;
	}

	@SuppressWarnings("unchecked")
	private void setToToDoView() {
		mToDoView.init((ArrayList<ItemData>) mData);
		mMainScrollPane = mToDoView.getScrollPane();
		mFinalView = mToDoView;
	}

	public String getCommand() {
		return mCommand;
	}

	public JPanel getFinalView() {
		return mFinalView;
	}

	public JScrollPane getLeftScrollPane() {
		return mMainToDoView.getScrollPane();
	}

	public JScrollPane getMainScrollPane() {
		return mMainScrollPane;
	}

	public JScrollPane getRightScrollPane() {
		return mMainTodayView.getScrollPane();
	}
}
