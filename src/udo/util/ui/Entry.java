package udo.util.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;










import javax.swing.border.EmptyBorder;


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

	private JTextArea mTextArea = new JTextArea();
	private JPanel mDetailPanel = new JPanel();
	// TODO maybe put a JTextPane here
	private JLabel mUid = new JLabel();
	private JPanel mTimePanel = new JPanel();
	private JPanel mSeparator = new JPanel();

	//private static final Color ENTRY_COLOR = new Color(50, 255, 125);

	public Entry(ItemData item, String type) {
		setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, UI.ENTRY_BORDERCOLOR));
		setBackground(UI.ENTRY_BGCOLOR);
		
		mUid.setFont(UI.UID_FONT);
		//mUid.setOpaque(true);
		//mUid.setBackground(UI.UID_COLOR);
		
		//mTimePanel.setBackground(UI.ENTRY_BGCOLOR);
		mTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		
		mTextArea.setLineWrap(true);
		mTextArea.setWrapStyleWord(true);
		mTextArea.setEditable(false);
		mTextArea.setOpaque(false);
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
		mTextArea.setSize(300, 1); // to make sure the width is fixed at 300
		add(mTextArea);
	}

	private void initPlan(ItemData item) {
		// TODO Auto-generated method stub
		
	}

	private void initTask(ItemData item) {
		// TODO Auto-generated method stub
		
	}

	private void initEvent(ItemData item) {
		// TODO Auto-generated method stub
		initUid( (Integer) item.get(Keys.UID));
		initTime((Calendar) item.get(Keys.START), (Calendar) item.get(Keys.END)); 
		add(mTimePanel, BorderLayout.WEST);
	}
	
	private void initUid(Integer uid) {
		mUid.setText(uid.toString());
		FontMetrics fm = mUid.getFontMetrics(mUid.getFont());
		int padding = 2;
		int height = fm.getHeight() + padding;
		int width = fm.stringWidth(mUid.getText()) + padding;
		mUid.setPreferredSize(new Dimension(width, height));
		mTimePanel.add(mUid);
	}
	
	private void initTime(Calendar start, Calendar end) {
		
	}

	@SuppressWarnings("unchecked")
	/*
	 * private void initText(ItemData item) { appendToPane(textArea,
	 * String.valueOf(item.get(Keys.UID)), Color.RED); appendToPane(textArea,
	 * item.get(Keys.START) + " - ", Color.BLACK ); appendToPane(textArea,
	 * item.get(Keys.END) + "\n", Color.black ); appendToPane(textArea,
	 * item.get(Keys.TITLE) + "\n", Color.BLACK); ArrayList<String> hashtags =
	 * ((ArrayList<String>) item.get(Keys.HASHTAGS)); for(int i=0;
	 * i<hashtags.size(); i++){ appendToPane(textArea, "#" + hashtags.get(i) +
	 * " ", Color.blue); } }
	 */
	private void initText(ItemData item) {
		mTextArea.append("[" + item.get(Keys.UID) + "] ");
		mTextArea.append(calToString((Calendar) item.get(Keys.START),
				(Calendar) item.get(Keys.END)));
		ArrayList<String> hashtags = ((ArrayList<String>) item
				.get(Keys.HASHTAGS));
		appendTitleAndTags((String) item.get(Keys.TITLE), hashtags );
	}
	
	@SuppressWarnings("unchecked")
	private void initTextNoDate(ItemData item) {
		mTextArea.append("[" + item.get(Keys.UID) + "] ");
		mTextArea.append(calToStringHoursOnly((Calendar) item.get(Keys.START),
				(Calendar) item.get(Keys.END)));
		ArrayList<String> hashtags = ((ArrayList<String>) item
				.get(Keys.HASHTAGS));
		appendTitleAndTags((String) item.get(Keys.TITLE), hashtags );
	}
	
	@SuppressWarnings("unchecked")
	private void initTextToDo(ItemData item) {
		mTextArea.append("[" + item.get(Keys.UID) + "] ");
		if(item.get(Keys.END)!= null ) {
			mTextArea.append(calToStringToDo((Calendar) item.get(Keys.END)));
		}
		ArrayList<String> hashtags = ((ArrayList<String>) item
				.get(Keys.HASHTAGS));
		appendTitleAndTags((String) item.get(Keys.TITLE), hashtags );
	}

	private String calToStringToDo(Calendar endCal) {
		String calString = "";
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("EEE dd/MM/yy kk:mm"); // will kk:mm be needed?
		calString += sdf.format(endCal.getTime());
		calString += "\n";
		return calString;
	}
	
	private String calToString(Calendar startCal, Calendar endCal) {
		String calString = "";
		SimpleDateFormat sdf, sdf2;
		sdf = new SimpleDateFormat("EEE dd/MM/yy kk:mm");
		sdf2 = new SimpleDateFormat("kk:mm");
		calString += sdf.format(startCal.getTime());
		calString += " - ";
		if (startCal.get(Calendar.DAY_OF_YEAR) == endCal
				.get(Calendar.DAY_OF_YEAR)) {
			calString += sdf2.format(endCal.getTime());
		} else {
			calString += sdf.format(endCal.getTime());
		}
		calString += "\n";
		return calString;
	}
	
	private String calToStringHoursOnly(Calendar startCal, Calendar endCal) {
		String calString = "";
		SimpleDateFormat sdf, sdf2;
		sdf = new SimpleDateFormat("EEE dd/MM/yy kk:mm");
		sdf2 = new SimpleDateFormat("kk:mm");
		calString += sdf2.format(startCal.getTime());
		calString += " - ";
		if (startCal.get(Calendar.DAY_OF_YEAR) == endCal
				.get(Calendar.DAY_OF_YEAR)) {
			calString += sdf2.format(endCal.getTime());
		} else {
			calString += sdf.format(endCal.getTime());
		}
		calString += "\n";
		return calString;
	}

	private void appendTitleAndTags(String title, ArrayList<String> hashtags ) {
		String titleAndTag = title + "\n";
		for (int i = 0; i < hashtags.size(); i++) {
			titleAndTag += "#" + hashtags.get(i) + " ";
		}
		mTextArea.append(titleAndTag);
	}
	
	/*
	 * private void appendToPane(JTextPane tp, String msg, Color c) {
	 * StyleContext sc = StyleContext.getDefaultStyleContext(); AttributeSet
	 * aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
	 * StyleConstants.Foreground, c);
	 * 
	 * aset = sc.addAttribute(aset, StyleConstants.FontFamily,
	 * "Lucida Console"); aset = sc.addAttribute(aset, StyleConstants.Alignment,
	 * StyleConstants.ALIGN_JUSTIFIED);
	 * 
	 * int len = tp.getDocument().getLength(); tp.setCaretPosition(len);
	 * tp.setCharacterAttributes(aset, false); tp.replaceSelection(msg); }
	 */

}
