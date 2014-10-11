package udo.util.ui;

import java.awt.BorderLayout;
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

public class Entry extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel mDetailPanel = new JPanel();
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

	public Entry(ItemData item, String type) {
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
		
		mHashtags.setFont(UI.FONT_16);
		
		switch(type) {
			case UI.ENTRY_EVENT:
				initEvent(item);
				break;
			case UI.ENTRY_TASK:
				initTask(item);
				break;
			case UI.ENTRY_PLAN:
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
		mTimePanel.add(initDate((Calendar) item.get(Keys.END)), BorderLayout.CENTER);
		add(mTimePanel);
		add(initSeparator());
		add(initDetails((String) item.get(Keys.TITLE), (ArrayList<String>) item.get(Keys.HASHTAGS)));
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
	
	private JPanel initDetails(String title, ArrayList<String> hashtags) {
		mHorizontalRemainder = UI.SUBVIEW_WIDTH - (int) getPreferredSize().getWidth();
		mDescription.setWrapStyleWord(true);
		mDescription.setLineWrap(true);
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
		mDash.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
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
		mSecondaryTime.setFont(UI.FONT_16);
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
