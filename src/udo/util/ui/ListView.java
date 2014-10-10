package udo.util.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;

public class ListView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GridBagConstraints mConstraints = new GridBagConstraints();
	private JPanel mList = new JPanel();
	
	public ListView() {

		setOpaque(false);
		setBounds(20,20,UI.SUBVIEW_WIDTH,UI.SUBVIEW_HEIGHT);
		mList.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT));
		mList.setLayout(new GridBagLayout());
	}

	public void init(ArrayList<ItemData> data) {
		mConstraints.fill = GridBagConstraints.HORIZONTAL;
		mConstraints.weightx = 1;
		mConstraints.weighty = 0;
		mConstraints.anchor = GridBagConstraints.PAGE_START;
		for (int i = 0; i < data.size(); i++) {
			mConstraints.gridy = i;
			Entry entry = new Entry(data.get(i), UI.ENTRY_EVENT);
			//add(entry, mConstraints);
			add(entry);
		}
	}

}
