package udo.util.ui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import udo.util.shared.ItemData;

public class ListView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListView() {

		super();
		setOpaque(false);
		setBounds(20,20,360,550);
		// setPreferredSize(new Dimension(360, 550));
	}

	public void init(ArrayList<ItemData> data) {
		for (int i = 0; i < data.size(); i++) {
			Entry entry = new Entry(data.get(i));
			add(entry);
		}
	}

}
