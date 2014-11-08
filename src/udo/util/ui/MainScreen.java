//@author A0114088H
package udo.util.ui;

import java.awt.Dimension;
import java.util.ArrayList;

import udo.util.shared.ItemData;

public class MainScreen extends Screen {

	private static final long serialVersionUID = 1L;

	public MainScreen(int width, int height) {
		super(width, height);
		mLayout.setVgap(0);
	}

	@Override
	public void init(ArrayList<ItemData> data) {
		mEntryView = new ListView();
		mScrollPane.setPreferredSize(new Dimension(mWidth, mHeight));
		mScrollPane.getViewport().add(mEntryView);
		mEntryView.populateView(data);
		if (mEntryView.getPreferredSize().width > mWidth) {
			// logger.info("TODAY mEntryView's preferredSize: " +
			// mEntryView.getPreferredSize() +
			// "\nmEntryView's preferredSize is wider than mScrollPane");
		} else {
			// logger.fine("TODAY mEntryView's preferredSize is contained in mScrollPane");
		}

		add(mScrollPane);
	}

}
