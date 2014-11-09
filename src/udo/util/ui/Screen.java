//@author A0114088H
package udo.util.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import udo.util.shared.Command;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.shared.Constants.UI;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JPanel mHeader = new JPanel();
	protected JScrollPane mScrollPane = new JScrollPane();
	protected View mEntryView;
	protected WrapLayout mLayout = new WrapLayout();
	
	protected int mHeight, mWidth;

	public Screen(int width, int height) {
		mWidth = width;
		mHeight = height;
		setOpaque(false);
		mHeader.setOpaque(false);
		setLayout(mLayout);
		mScrollPane.getViewport().setOpaque(false);
		mScrollPane.setOpaque(false);
		mScrollPane.setBorder(BorderFactory.createEmptyBorder());
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setBounds(0, 0, width, height);
	}

	protected void init(ArrayList<ItemData> data) {
		mEntryView = new ListView();
		mScrollPane.setPreferredSize(new Dimension(mWidth, 
									mHeight - mLayout.getVgap()*2 - (int) mHeader.getPreferredSize().getHeight()));
		mScrollPane.getViewport().add(mEntryView);
		mEntryView.populateView(data);
		if (mEntryView.getPreferredSize().width > UI.MAIN_WIDTH) {
			// logger.info("TODAY mEntryView's preferredSize: " +
			// mEntryView.getPreferredSize() +
			// "\nmEntryView's preferredSize is wider than mScrollPane");
		} else {
			// logger.fine("TODAY mEntryView's preferredSize is contained in mScrollPane");
		}

		add(mScrollPane);
	}

	protected void init(OutputData output, Command type) {
		mEntryView = new SingleView();
		mEntryView.init(output, type);
		add(mEntryView);
	}

	public JScrollPane getScrollPane() {
		return mScrollPane;
	}

	public void init(Date date, ArrayList<ItemData> mData) {
		// this is another stub to be overridden by subclass.

	}

	@Override
	public void removeAll() {
		super.removeAll();
	}
}
