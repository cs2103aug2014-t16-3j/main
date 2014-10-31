//@author A0114088H
package udo.util.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;








/*
 import javax.swing.JTextPane;
 import javax.swing.text.AttributeSet;
 import javax.swing.text.SimpleAttributeSet;
 import javax.swing.text.StyleConstants;
 import javax.swing.text.StyleContext;
 */
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

public class Entry extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel mDetailPanel = new JPanel();
	private JTextArea mExtraDesc = new JTextArea();
	private JTextArea mDescription = new JTextArea();
	private JTextArea mHashtags = new JTextArea();
	private JLabel mUid = new JLabel();
	private JLabel mDate;
	private JLabel mMonth = new JLabel();
	private JPanel mTimePanel = new JPanel();
	private JPanel mSeparator = new JPanel();
	private int mHorizontalRemainder;

	public Entry(ItemData item, ItemType type) {
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UI.ENTRY_BORDERCOLOR));
		setBackground(UI.ENTRY_BGCOLOR);
		
		mUid.setFont(UI.FONT_12);
		mUid.setOpaque(true);
		mUid.setBackground(UI.UID_COLOR);
		
		mTimePanel.setOpaque(false);
		mTimePanel.setLayout(new BorderLayout());
		
		mDetailPanel.setLayout(new BoxLayout(mDetailPanel, BoxLayout.PAGE_AXIS));
		mDetailPanel.setOpaque(false);
		
		mDescription.setFont(UI.FONT_20);
		mDescription.setForeground(UI.ENTRY_TITLE_COLOR);
		mDescription.setOpaque(false);
		mDescription.setEditable(false);
		mDescription.setWrapStyleWord(true);
		mDescription.setLineWrap(true);
		
		mExtraDesc.setFont(UI.FONT_14);
		mExtraDesc.setForeground(UI.ENTRY_DATE_COLOR);
		mExtraDesc.setOpaque(false);
		mExtraDesc.setEditable(false);
		mExtraDesc.setWrapStyleWord(true);
		mExtraDesc.setLineWrap(true);
		
		mHashtags.setFont(UI.FONT_14);
		mHashtags.setForeground(UI.ENTRY_HASHTAGS_COLOR);
		mHashtags.setOpaque(false);
		mHashtags.setEditable(false);
		mHashtags.setWrapStyleWord(true);
		mHashtags.setLineWrap(true);
		
		switch(type) {
			case EVENT:
				initEvent(item);
				break;
			case TASK:
				initTask(item);
				break;
			case PLAN:
				initPlan(item);
				break;
			default:
				break;
		}
	}

	@SuppressWarnings("unchecked")
	private void initPlan(ItemData item) {
		mTimePanel.add(initUid( (Integer) item.get(Keys.UID)), BorderLayout.NORTH);
		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);
		emptyPanel.setPreferredSize(UI.EMPTY_DATE);
		mTimePanel.add(emptyPanel);
		add(mTimePanel);
		add(initSeparator(item.getItemType()));
		add(initDetails((String) item.get(Keys.TITLE), (ArrayList<String>) item.get(Keys.HASHTAGS)));
		
	}

	@SuppressWarnings("unchecked")
	private void initTask(ItemData item) {
		mTimePanel.add(initUid( (Integer) item.get(Keys.UID)), BorderLayout.NORTH);
		mTimePanel.add(initDate((Calendar) item.get(Keys.DUE)), BorderLayout.CENTER);
//		mTimePanel.setBorder(BorderFactory.createEmptyBorder(0, (UI.ENTRY_TIMEPANEL_WIDTH - mTimePanel.getPreferredSize().width)/2,
//															0, (UI.ENTRY_TIMEPANEL_WIDTH - mTimePanel.getPreferredSize().width)/2));
		add(mTimePanel);
		add(initSeparator(item.getItemType()));
		add(initDetails((Calendar) item.get(Keys.DUE), (String) item.get(Keys.TITLE), (ArrayList<String>) item.get(Keys.HASHTAGS)));
	}

	@SuppressWarnings("unchecked")
	private void initEvent(ItemData item) {
		mTimePanel.add(initUid( (Integer) item.get(Keys.UID)), BorderLayout.NORTH);
		mTimePanel.add(initDate((Calendar) item.get(Keys.START)), BorderLayout.CENTER);
		add(mTimePanel);
		add(initSeparator(item.getItemType()));
		add(initDetails((Calendar) item.get(Keys.START),
						(Calendar) item.get(Keys.END),
						(String) item.get(Keys.TITLE), 
						(ArrayList<String>) item.get(Keys.HASHTAGS)));
	}
	
	private JPanel initDetails(Calendar dueTime, String title, ArrayList<String> hashtags) {
		mExtraDesc.append(initTime(dueTime));
		mExtraDesc.setForeground(UI.TASK_COLOR);
		return initDetails(title,hashtags);
	}
	
	private JPanel initDetails(Calendar startTime, Calendar endTime, String title, ArrayList<String> hashtags) {
		mExtraDesc.append(initTime(startTime, endTime));
		mExtraDesc.setForeground(UI.EVENT_COLOR);
		return initDetails(title,hashtags);
	}
	
	private JPanel initDetails(String title, ArrayList<String> hashtags) {
		mHorizontalRemainder = UI.SUBVIEW_WIDTH - (int) getPreferredSize().getWidth();
		mDescription.setSize(mHorizontalRemainder ,1);
		mDescription.append(title);
		for(int i = 0; i< hashtags.size(); i++) {
			mHashtags.append("#" + hashtags.get(i) + " ");
		}
		mDetailPanel.add(mDescription);
		mDetailPanel.add(mExtraDesc);
		mDetailPanel.add(mHashtags);
		return mDetailPanel;
	}

	private JLabel initUid(Integer uid) {
		mUid.setText(uid.toString());
		mUid.setHorizontalAlignment(SwingConstants.CENTER);
		return mUid;
	}
	
	private String initTime(Calendar cal) {
		String time = "by ";
		time += getDay(cal);
		time += UI.HOUR_12.format(cal.getTime());
		return time;
	}
	
	private String initTime(Calendar startCal, Calendar endCal) {
		String time = getDay(startCal);
		time += UI.HOUR_12.format(startCal.getTime()) + " - ";
		if(getDayDiff(startCal, endCal) != 0) {
			time += getDay(endCal);
			time += UI.DD_MMM.format(endCal.getTime());
		}
		time += UI.HOUR_12.format(endCal.getTime());
		return time;
	}
	
	private String getDay(Calendar cal) {
		String day = "";
		Calendar today = Calendar.getInstance();
		int dayDiff = getDayDiff(today, cal);
		switch(dayDiff) {
			case -1:
				day = "yesterday ";
				break;
			case 0:
				if(cal.get(Calendar.HOUR_OF_DAY) > 17) {
					day = "tonight ";
				}else{
					day = "today ";
				}
				break;
			case 1:
				day = "tomorrow ";
				break;
			case -6:
			case -5:
			case -4:
			case -3:
			case -2:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				day = UI.DAY_NAME.format(cal.getTime()) + " ";
			default:
				break;
		}
		return day;
	}
	
	private int getDayDiff(Calendar start, Calendar end) {
		int tempYear = end.get(Calendar.YEAR);
		int endDays = end.get(Calendar.DAY_OF_YEAR);
		
		while(tempYear > start.get(Calendar.YEAR)) {
			tempYear--;
			Calendar offsetYear = new GregorianCalendar(tempYear, Calendar.DECEMBER, 31);
			endDays += offsetYear.get(Calendar.DAY_OF_YEAR);
		}
		return endDays - start.get(Calendar.DAY_OF_YEAR);
	}
	
	private JPanel initDate(Calendar cal) {
		JPanel time = new JPanel();
		time.setOpaque(false);
		time.setLayout(new BoxLayout(time, BoxLayout.PAGE_AXIS));
		mDate = new JLabel(UI.DD.format(cal.getTime()));
		mMonth = new JLabel(UI.MMM.format(cal.getTime()));
		mDate.setFont(UI.FONT_20_BOLD);
		mMonth.setFont(UI.FONT_16);
		time.add(mDate);
		time.add(mMonth);
		System.out.println(time.getPreferredSize());
		return time;
	}
	
	private JPanel initSeparator(ItemType type) {
		if(type.equals(ItemType.EVENT)) {
			mSeparator.setBackground(UI.EVENT_COLOR);
		} else if(type.equals(ItemType.TASK)) {
			mSeparator.setBackground(UI.TASK_COLOR);
		}else if(type.equals(ItemType.PLAN)){
			mSeparator.setBackground(UI.PLAN_COLOR);
		}
		mSeparator.setBorder(BorderFactory.createEmptyBorder(UI.ENTRY_SEPARATOR_HEIGHT, UI.ENTRY_SEPARATOR_WIDTH, 0, 0));
		return mSeparator;
	}

}
