//@author A0114088H
package udo.ui.util;

import java.awt.Dimension;
import java.util.ArrayList;

import udo.data.ItemData;

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

		add(mScrollPane);
	}

}
