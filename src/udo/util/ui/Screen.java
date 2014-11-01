package udo.util.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import udo.util.shared.ItemData;
import udo.util.shared.Constants.UI;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JPanel mHeader = new JPanel();
	protected JScrollPane mScrollPane = new JScrollPane();
	protected View mEntryView = new ListView();
	protected WrapLayout mLayout = new WrapLayout();
	
	public Screen() {
		setOpaque(false);
		mHeader.setOpaque(false);
		setLayout(mLayout);
		mScrollPane.getViewport().setOpaque(false);
		mScrollPane.setOpaque(false);
		mScrollPane.setBorder(BorderFactory.createEmptyBorder());
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	protected void init(ArrayList<ItemData> data) {
		mScrollPane.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH,
				UI.SUBVIEW_HEIGHT - mHeader.getPreferredSize().height - UI.TEXTFIELD_HEIGHT));
		mScrollPane.getViewport().add(mEntryView);
		mEntryView.populateView(data);
		if(mEntryView.getPreferredSize().width > UI.MAIN_WIDTH) {
//			logger.info("TODAY mEntryView's preferredSize: " + mEntryView.getPreferredSize() + "\nmEntryView's preferredSize is wider than mScrollPane");
		} else {
//			logger.fine("TODAY mEntryView's preferredSize is contained in mScrollPane");
		}
		
		add(mScrollPane);
	}
	
	public JScrollPane getScrollPane() {
		return mScrollPane;
	}

	public void init(Date date, ArrayList<ItemData> mData) {
		// TODO Auto-generated method stub
		// this is another stub to be overridden by subclass.
		
	}
}