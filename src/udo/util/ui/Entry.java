package udo.util.ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
*/
import udo.util.shared.Constants.Keys;
import udo.util.shared.ItemData;

public class Entry extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea mTextArea = new JTextArea();
	
	private static final Color ENTRY_COLOR = new Color(50,255,125);
	private static final Color ENTRY_BORDER = new Color(45,215,105);
	
	public Entry(ItemData item) {
        setBorder(BorderFactory.createLineBorder(ENTRY_BORDER));
        setBackground(ENTRY_COLOR);
        mTextArea.setLineWrap(true);
		mTextArea.setWrapStyleWord(true);
		mTextArea.setEditable(false);
		mTextArea.setOpaque(false);
		initText(item);
	    mTextArea.setSize(300, 1); // to make sure the width is fixed at 300
        add(mTextArea);
    }
	
	@SuppressWarnings("unchecked")
	/*private void initText(ItemData item) {
		appendToPane(textArea, String.valueOf(item.get(Keys.UID)), Color.RED);
		appendToPane(textArea, item.get(Keys.START) + " - ", Color.BLACK );
		appendToPane(textArea, item.get(Keys.END) + "\n", Color.black );
		appendToPane(textArea, item.get(Keys.TITLE) + "\n", Color.BLACK);
		ArrayList<String> hashtags = ((ArrayList<String>) item.get(Keys.HASHTAGS));
		for(int i=0; i<hashtags.size(); i++){
			appendToPane(textArea, "#" + hashtags.get(i) + " ", Color.blue);
		}
	}*/
	
	private void initText(ItemData item) {
		mTextArea.append("[" + item.get(Keys.UID) + "] ");
		mTextArea.append(calToString((Calendar)item.get(Keys.START), (Calendar)item.get(Keys.END)));
		mTextArea.append(item.get(Keys.TITLE) + "\n");
		ArrayList<String> hashtags = ((ArrayList<String>) item.get(Keys.HASHTAGS));
		for(int i=0; i<hashtags.size(); i++){
			mTextArea.append("#" + hashtags.get(i) + " ");
		}
	}
	
	private String calToString(Calendar startCal, Calendar endCal) {
		String calString = "";
		SimpleDateFormat sdf, sdf2; 
		sdf = new SimpleDateFormat("EEE dd/MM/yy kk:mm");
		sdf2 = new SimpleDateFormat("kk:mm");
		calString += sdf.format(startCal.getTime());
		calString += " - ";
		if(startCal.get(Calendar.DAY_OF_YEAR) == endCal.get(Calendar.DAY_OF_YEAR)) {
			calString += sdf2.format(endCal.getTime());
		}else{
			calString += sdf.format(endCal.getTime());
		}
		calString += "\n";
		return calString;
		
	}
	
	/*private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }*/

}
