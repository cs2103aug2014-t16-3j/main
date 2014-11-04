package udo.util.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import udo.util.shared.ItemData;
import udo.util.shared.Constants.UI;

public class MainScreen extends Screen {

	private static final long serialVersionUID = 1L;

	public MainScreen() {
		super();
		mLayout.setVgap(0);
	}

	@Override
	public void init(ArrayList<ItemData> data) {
		mEntryView = new ListView();
		mScrollPane.setPreferredSize(new Dimension(UI.MAIN_WIDTH
				- UI.MAIN_PADDING, UI.MAIN_HEIGHT - UI.TEXTFIELD_HEIGHT));
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

}
