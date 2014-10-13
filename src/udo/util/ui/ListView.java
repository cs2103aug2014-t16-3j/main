package udo.util.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	
	public ListView() {

		setOpaque(false);
		setBounds(20,20,UI.SUBVIEW_WIDTH,UI.SUBVIEW_HEIGHT);
		setLayout(new FlowLayout(FlowLayout.LEADING, 0 ,0));
	}

	public void init(ArrayList<ItemData> data) {
		for (int i = 0; i < data.size(); i++) {
			Entry entry = new Entry(data.get(i), UI.ENTRY_EVENT);
			add(entry);
		}
	}

}
