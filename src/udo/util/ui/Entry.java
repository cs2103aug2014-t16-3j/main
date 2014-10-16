package udo.util.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
	// TODO maybe put a JTextPane here
	private JLabel mUid = new JLabel();
	private JLabel mPrimaryTime; // can be hour or date
	private JLabel mSecondaryTime = new JLabel(); // can be minute or month
	private JLabel mDash = new JLabel("-");
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
		
		mExtraDesc.setFont(UI.FONT_14);
		mExtraDesc.setEditable(false);
		mExtraDesc.setWrapStyleWord(true);
		mExtraDesc.setLineWrap(true);
		
		mDescription.setFont(UI.FONT_20);
		mDescription.setWrapStyleWord(true);
		mDescription.setLineWrap(true);
		mDescription.setEditable(false);
		
		mHashtags.setFont(UI.FONT_16);
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
		mTimePanel.add(initUid( (Integer) item.get(Keys.UID)), BorderLayout.CENTER);
		add(mTimePanel);
		add(initSeparator());
		add(initDetails((String) item.get(Keys.TITLE), (ArrayList<String>) item.get(Keys.HASHTAGS)));
		
	}

	@SuppressWarnings("unchecked")
	private void initTask(ItemData item) {
		mTimePanel.add(initUid( (Integer) item.get(Keys.UID)), BorderLayout.NORTH);
		mTimePanel.add(initDate((Calendar) item.get(Keys.DUE)), BorderLayout.CENTER);
		add(mTimePanel);
		add(initSeparator());
		add(initDetails((Calendar) item.get(Keys.DUE), (String) item.get(Keys.TITLE), (ArrayList<String>) item.get(Keys.HASHTAGS)));
	}

	@SuppressWarnings("unchecked")
	private void initEvent(ItemData item) {
		mTimePanel.add(initTime((Calendar) item.get(Keys.START)), BorderLayout.WEST);
		mTimePanel.add(initDash(), BorderLayout.CENTER);
		mTimePanel.add(initTime((Calendar) item.get(Keys.END)), BorderLayout.EAST);
		mTimePanel.add(initUid( (Integer) item.get(Keys.UID)), BorderLayout.NORTH);
		add(mTimePanel);
		add(initSeparator());
		add(initDetails((String) item.get(Keys.TITLE), (ArrayList<String>) item.get(Keys.HASHTAGS)));
	}
	
	private JPanel initDetails(Calendar dueTime, String title, ArrayList<String> hashtags) {
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
		mExtraDesc.append(sdf.format(dueTime.getTime()));
		mDetailPanel.add(mExtraDesc);
		return initDetails(title, hashtags);
	}
	
	private JPanel initDetails(String title, ArrayList<String> hashtags) {
		mHorizontalRemainder = UI.SUBVIEW_WIDTH - (int) getPreferredSize().getWidth();
		mDescription.setSize(mHorizontalRemainder ,1);
		mDescription.append(title);
		for(int i = 0; i< hashtags.size(); i++) {
			mHashtags.append("#" + hashtags.get(i) + " ");
		}
		mDetailPanel.add(mDescription);
		mDetailPanel.add(mHashtags);
		return mDetailPanel;
	}

	private JLabel initUid(Integer uid) {
		mUid.setText(uid.toString());
		mUid.setHorizontalAlignment(SwingConstants.CENTER);
		return mUid;
	}
	
	private JLabel initDash() {
		mDash.setFont(UI.FONT_16);
		mDash.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
		return mDash;
	}
	
	private JPanel initTime(Calendar cal) {
		JPanel time = new JPanel();
		time.setOpaque(false);
		time.setLayout(new BoxLayout(time, BoxLayout.PAGE_AXIS));
		SimpleDateFormat dfHour = new SimpleDateFormat("kk");
		mPrimaryTime = new JLabel(dfHour.format(cal.getTime()));
		SimpleDateFormat dfMin = new SimpleDateFormat("mm");
		mSecondaryTime = new JLabel(dfMin.format(cal.getTime()));
		mPrimaryTime.setFont(UI.FONT_18);
		mSecondaryTime.setFont(UI.FONT_14);
		time.add(mPrimaryTime);
		time.add(mSecondaryTime);
		return time;
	}
	
	private JPanel initDate(Calendar cal) {
		JPanel time = new JPanel();
		time.setOpaque(false);
		time.setLayout(new BoxLayout(time, BoxLayout.PAGE_AXIS));
		SimpleDateFormat dfDate = new SimpleDateFormat("dd");
		mPrimaryTime = new JLabel(dfDate.format(cal.getTime()));
		SimpleDateFormat dfMonth = new SimpleDateFormat("MMM");
		mSecondaryTime = new JLabel(dfMonth.format(cal.getTime()));
		mPrimaryTime.setFont(UI.FONT_20);
		mSecondaryTime.setFont(UI.FONT_16);
		time.add(mPrimaryTime);
		time.add(mSecondaryTime);
		return time;
	}
	
	private JPanel initSeparator() {
		mSeparator.setBackground(UI.SEPARATOR_COLOR);
		mSeparator.setBorder(BorderFactory.createEmptyBorder(UI.ENTRY_SEPARATOR_HEIGHT, UI.ENTRY_SEPARATOR_WIDTH, 0, 0));
		return mSeparator;
	}

}
